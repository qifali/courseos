package com.tonyjoy.courseos.web.rest;

import com.tonyjoy.courseos.CourseosApp;

import com.tonyjoy.courseos.domain.StuCourse;
import com.tonyjoy.courseos.domain.Student;
import com.tonyjoy.courseos.domain.TeachCourse;
import com.tonyjoy.courseos.repository.StuCourseRepository;
import com.tonyjoy.courseos.service.StuCourseService;
import com.tonyjoy.courseos.web.rest.errors.ExceptionTranslator;
import com.tonyjoy.courseos.service.dto.StuCourseCriteria;
import com.tonyjoy.courseos.service.StuCourseQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.tonyjoy.courseos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StuCourseResource REST controller.
 *
 * @see StuCourseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CourseosApp.class)
public class StuCourseResourceIntTest {

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_PHASE = "AAAAAAAAAA";
    private static final String UPDATED_PHASE = "BBBBBBBBBB";

    @Autowired
    private StuCourseRepository stuCourseRepository;

    @Autowired
    private StuCourseService stuCourseService;

    @Autowired
    private StuCourseQueryService stuCourseQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStuCourseMockMvc;

    private StuCourse stuCourse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StuCourseResource stuCourseResource = new StuCourseResource(stuCourseService, stuCourseQueryService);
        this.restStuCourseMockMvc = MockMvcBuilders.standaloneSetup(stuCourseResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StuCourse createEntity(EntityManager em) {
        StuCourse stuCourse = new StuCourse()
            .desc(DEFAULT_DESC)
            .phase(DEFAULT_PHASE);
        return stuCourse;
    }

    @Before
    public void initTest() {
        stuCourse = createEntity(em);
    }

    @Test
    @Transactional
    public void createStuCourse() throws Exception {
        int databaseSizeBeforeCreate = stuCourseRepository.findAll().size();

        // Create the StuCourse
        restStuCourseMockMvc.perform(post("/api/stu-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stuCourse)))
            .andExpect(status().isCreated());

        // Validate the StuCourse in the database
        List<StuCourse> stuCourseList = stuCourseRepository.findAll();
        assertThat(stuCourseList).hasSize(databaseSizeBeforeCreate + 1);
        StuCourse testStuCourse = stuCourseList.get(stuCourseList.size() - 1);
        assertThat(testStuCourse.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testStuCourse.getPhase()).isEqualTo(DEFAULT_PHASE);
    }

    @Test
    @Transactional
    public void createStuCourseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stuCourseRepository.findAll().size();

        // Create the StuCourse with an existing ID
        stuCourse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStuCourseMockMvc.perform(post("/api/stu-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stuCourse)))
            .andExpect(status().isBadRequest());

        // Validate the StuCourse in the database
        List<StuCourse> stuCourseList = stuCourseRepository.findAll();
        assertThat(stuCourseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStuCourses() throws Exception {
        // Initialize the database
        stuCourseRepository.saveAndFlush(stuCourse);

        // Get all the stuCourseList
        restStuCourseMockMvc.perform(get("/api/stu-courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stuCourse.getId().intValue())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].phase").value(hasItem(DEFAULT_PHASE.toString())));
    }

    @Test
    @Transactional
    public void getStuCourse() throws Exception {
        // Initialize the database
        stuCourseRepository.saveAndFlush(stuCourse);

        // Get the stuCourse
        restStuCourseMockMvc.perform(get("/api/stu-courses/{id}", stuCourse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stuCourse.getId().intValue()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.phase").value(DEFAULT_PHASE.toString()));
    }

    @Test
    @Transactional
    public void getAllStuCoursesByDescIsEqualToSomething() throws Exception {
        // Initialize the database
        stuCourseRepository.saveAndFlush(stuCourse);

        // Get all the stuCourseList where desc equals to DEFAULT_DESC
        defaultStuCourseShouldBeFound("desc.equals=" + DEFAULT_DESC);

        // Get all the stuCourseList where desc equals to UPDATED_DESC
        defaultStuCourseShouldNotBeFound("desc.equals=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllStuCoursesByDescIsInShouldWork() throws Exception {
        // Initialize the database
        stuCourseRepository.saveAndFlush(stuCourse);

        // Get all the stuCourseList where desc in DEFAULT_DESC or UPDATED_DESC
        defaultStuCourseShouldBeFound("desc.in=" + DEFAULT_DESC + "," + UPDATED_DESC);

        // Get all the stuCourseList where desc equals to UPDATED_DESC
        defaultStuCourseShouldNotBeFound("desc.in=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllStuCoursesByDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        stuCourseRepository.saveAndFlush(stuCourse);

        // Get all the stuCourseList where desc is not null
        defaultStuCourseShouldBeFound("desc.specified=true");

        // Get all the stuCourseList where desc is null
        defaultStuCourseShouldNotBeFound("desc.specified=false");
    }

    @Test
    @Transactional
    public void getAllStuCoursesByPhaseIsEqualToSomething() throws Exception {
        // Initialize the database
        stuCourseRepository.saveAndFlush(stuCourse);

        // Get all the stuCourseList where phase equals to DEFAULT_PHASE
        defaultStuCourseShouldBeFound("phase.equals=" + DEFAULT_PHASE);

        // Get all the stuCourseList where phase equals to UPDATED_PHASE
        defaultStuCourseShouldNotBeFound("phase.equals=" + UPDATED_PHASE);
    }

    @Test
    @Transactional
    public void getAllStuCoursesByPhaseIsInShouldWork() throws Exception {
        // Initialize the database
        stuCourseRepository.saveAndFlush(stuCourse);

        // Get all the stuCourseList where phase in DEFAULT_PHASE or UPDATED_PHASE
        defaultStuCourseShouldBeFound("phase.in=" + DEFAULT_PHASE + "," + UPDATED_PHASE);

        // Get all the stuCourseList where phase equals to UPDATED_PHASE
        defaultStuCourseShouldNotBeFound("phase.in=" + UPDATED_PHASE);
    }

    @Test
    @Transactional
    public void getAllStuCoursesByPhaseIsNullOrNotNull() throws Exception {
        // Initialize the database
        stuCourseRepository.saveAndFlush(stuCourse);

        // Get all the stuCourseList where phase is not null
        defaultStuCourseShouldBeFound("phase.specified=true");

        // Get all the stuCourseList where phase is null
        defaultStuCourseShouldNotBeFound("phase.specified=false");
    }

    @Test
    @Transactional
    public void getAllStuCoursesByStudentIsEqualToSomething() throws Exception {
        // Initialize the database
        Student student = StudentResourceIntTest.createEntity(em);
        em.persist(student);
        em.flush();
        stuCourse.setStudent(student);
        stuCourseRepository.saveAndFlush(stuCourse);
        Long studentId = student.getId();

        // Get all the stuCourseList where student equals to studentId
        defaultStuCourseShouldBeFound("studentId.equals=" + studentId);

        // Get all the stuCourseList where student equals to studentId + 1
        defaultStuCourseShouldNotBeFound("studentId.equals=" + (studentId + 1));
    }


    @Test
    @Transactional
    public void getAllStuCoursesByTeachCourseIsEqualToSomething() throws Exception {
        // Initialize the database
        TeachCourse teachCourse = TeachCourseResourceIntTest.createEntity(em);
        em.persist(teachCourse);
        em.flush();
        stuCourse.setTeachCourse(teachCourse);
        stuCourseRepository.saveAndFlush(stuCourse);
        Long teachCourseId = teachCourse.getId();

        // Get all the stuCourseList where teachCourse equals to teachCourseId
        defaultStuCourseShouldBeFound("teachCourseId.equals=" + teachCourseId);

        // Get all the stuCourseList where teachCourse equals to teachCourseId + 1
        defaultStuCourseShouldNotBeFound("teachCourseId.equals=" + (teachCourseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultStuCourseShouldBeFound(String filter) throws Exception {
        restStuCourseMockMvc.perform(get("/api/stu-courses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stuCourse.getId().intValue())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].phase").value(hasItem(DEFAULT_PHASE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultStuCourseShouldNotBeFound(String filter) throws Exception {
        restStuCourseMockMvc.perform(get("/api/stu-courses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingStuCourse() throws Exception {
        // Get the stuCourse
        restStuCourseMockMvc.perform(get("/api/stu-courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStuCourse() throws Exception {
        // Initialize the database
        stuCourseService.save(stuCourse);

        int databaseSizeBeforeUpdate = stuCourseRepository.findAll().size();

        // Update the stuCourse
        StuCourse updatedStuCourse = stuCourseRepository.findOne(stuCourse.getId());
        // Disconnect from session so that the updates on updatedStuCourse are not directly saved in db
        em.detach(updatedStuCourse);
        updatedStuCourse
            .desc(UPDATED_DESC)
            .phase(UPDATED_PHASE);

        restStuCourseMockMvc.perform(put("/api/stu-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStuCourse)))
            .andExpect(status().isOk());

        // Validate the StuCourse in the database
        List<StuCourse> stuCourseList = stuCourseRepository.findAll();
        assertThat(stuCourseList).hasSize(databaseSizeBeforeUpdate);
        StuCourse testStuCourse = stuCourseList.get(stuCourseList.size() - 1);
        assertThat(testStuCourse.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testStuCourse.getPhase()).isEqualTo(UPDATED_PHASE);
    }

    @Test
    @Transactional
    public void updateNonExistingStuCourse() throws Exception {
        int databaseSizeBeforeUpdate = stuCourseRepository.findAll().size();

        // Create the StuCourse

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStuCourseMockMvc.perform(put("/api/stu-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stuCourse)))
            .andExpect(status().isCreated());

        // Validate the StuCourse in the database
        List<StuCourse> stuCourseList = stuCourseRepository.findAll();
        assertThat(stuCourseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStuCourse() throws Exception {
        // Initialize the database
        stuCourseService.save(stuCourse);

        int databaseSizeBeforeDelete = stuCourseRepository.findAll().size();

        // Get the stuCourse
        restStuCourseMockMvc.perform(delete("/api/stu-courses/{id}", stuCourse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StuCourse> stuCourseList = stuCourseRepository.findAll();
        assertThat(stuCourseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StuCourse.class);
        StuCourse stuCourse1 = new StuCourse();
        stuCourse1.setId(1L);
        StuCourse stuCourse2 = new StuCourse();
        stuCourse2.setId(stuCourse1.getId());
        assertThat(stuCourse1).isEqualTo(stuCourse2);
        stuCourse2.setId(2L);
        assertThat(stuCourse1).isNotEqualTo(stuCourse2);
        stuCourse1.setId(null);
        assertThat(stuCourse1).isNotEqualTo(stuCourse2);
    }
}
