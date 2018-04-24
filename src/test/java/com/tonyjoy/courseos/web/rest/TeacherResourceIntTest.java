package com.tonyjoy.courseos.web.rest;

import com.tonyjoy.courseos.CourseosApp;

import com.tonyjoy.courseos.domain.Teacher;
import com.tonyjoy.courseos.repository.TeacherRepository;
import com.tonyjoy.courseos.service.TeacherService;
import com.tonyjoy.courseos.web.rest.errors.ExceptionTranslator;
import com.tonyjoy.courseos.service.dto.TeacherCriteria;
import com.tonyjoy.courseos.service.TeacherQueryService;

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

import com.tonyjoy.courseos.domain.enumeration.IsOnJobStatus;
/**
 * Test class for the TeacherResource REST controller.
 *
 * @see TeacherResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CourseosApp.class)
public class TeacherResourceIntTest {

    private static final String DEFAULT_TEACHER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TEACHER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_COLLEGE = "AAAAAAAAAA";
    private static final String UPDATED_COLLEGE = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final IsOnJobStatus DEFAULT_IS_ON_JOB = IsOnJobStatus.YES;
    private static final IsOnJobStatus UPDATED_IS_ON_JOB = IsOnJobStatus.NO;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherQueryService teacherQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTeacherMockMvc;

    private Teacher teacher;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeacherResource teacherResource = new TeacherResource(teacherService, teacherQueryService);
        this.restTeacherMockMvc = MockMvcBuilders.standaloneSetup(teacherResource)
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
    public static Teacher createEntity(EntityManager em) {
        Teacher teacher = new Teacher()
            .teacherCode(DEFAULT_TEACHER_CODE)
            .name(DEFAULT_NAME)
            .subject(DEFAULT_SUBJECT)
            .college(DEFAULT_COLLEGE)
            .desc(DEFAULT_DESC)
            .isOnJob(DEFAULT_IS_ON_JOB);
        return teacher;
    }

    @Before
    public void initTest() {
        teacher = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeacher() throws Exception {
        int databaseSizeBeforeCreate = teacherRepository.findAll().size();

        // Create the Teacher
        restTeacherMockMvc.perform(post("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacher)))
            .andExpect(status().isCreated());

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeCreate + 1);
        Teacher testTeacher = teacherList.get(teacherList.size() - 1);
//        assertThat(testTeacher.getTeacherCode()).isEqualTo(DEFAULT_TEACHER_CODE);
        assertThat(testTeacher.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTeacher.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testTeacher.getCollege()).isEqualTo(DEFAULT_COLLEGE);
        assertThat(testTeacher.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testTeacher.getIsOnJob()).isEqualTo(DEFAULT_IS_ON_JOB);
    }

    @Test
    @Transactional
    public void createTeacherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teacherRepository.findAll().size();

        // Create the Teacher with an existing ID
        teacher.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeacherMockMvc.perform(post("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacher)))
            .andExpect(status().isBadRequest());

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTeachers() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList
        restTeacherMockMvc.perform(get("/api/teachers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teacher.getId().intValue())))
            .andExpect(jsonPath("$.[*].teacherCode").value(hasItem(DEFAULT_TEACHER_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].college").value(hasItem(DEFAULT_COLLEGE.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].isOnJob").value(hasItem(DEFAULT_IS_ON_JOB.toString())));
    }

    @Test
    @Transactional
    public void getTeacher() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get the teacher
        restTeacherMockMvc.perform(get("/api/teachers/{id}", teacher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teacher.getId().intValue()))
            .andExpect(jsonPath("$.teacherCode").value(DEFAULT_TEACHER_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.college").value(DEFAULT_COLLEGE.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.isOnJob").value(DEFAULT_IS_ON_JOB.toString()));
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCode equals to DEFAULT_TEACHER_CODE
        defaultTeacherShouldBeFound("teacherCode.equals=" + DEFAULT_TEACHER_CODE);

        // Get all the teacherList where teacherCode equals to UPDATED_TEACHER_CODE
        defaultTeacherShouldNotBeFound("teacherCode.equals=" + UPDATED_TEACHER_CODE);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherCodeIsInShouldWork() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCode in DEFAULT_TEACHER_CODE or UPDATED_TEACHER_CODE
        defaultTeacherShouldBeFound("teacherCode.in=" + DEFAULT_TEACHER_CODE + "," + UPDATED_TEACHER_CODE);

        // Get all the teacherList where teacherCode equals to UPDATED_TEACHER_CODE
        defaultTeacherShouldNotBeFound("teacherCode.in=" + UPDATED_TEACHER_CODE);
    }

    @Test
    @Transactional
    public void getAllTeachersByTeacherCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where teacherCode is not null
        defaultTeacherShouldBeFound("teacherCode.specified=true");

        // Get all the teacherList where teacherCode is null
        defaultTeacherShouldNotBeFound("teacherCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeachersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where name equals to DEFAULT_NAME
        defaultTeacherShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the teacherList where name equals to UPDATED_NAME
        defaultTeacherShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTeachersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTeacherShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the teacherList where name equals to UPDATED_NAME
        defaultTeacherShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTeachersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where name is not null
        defaultTeacherShouldBeFound("name.specified=true");

        // Get all the teacherList where name is null
        defaultTeacherShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeachersBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where subject equals to DEFAULT_SUBJECT
        defaultTeacherShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the teacherList where subject equals to UPDATED_SUBJECT
        defaultTeacherShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllTeachersBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultTeacherShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the teacherList where subject equals to UPDATED_SUBJECT
        defaultTeacherShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllTeachersBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where subject is not null
        defaultTeacherShouldBeFound("subject.specified=true");

        // Get all the teacherList where subject is null
        defaultTeacherShouldNotBeFound("subject.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeachersByCollegeIsEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where college equals to DEFAULT_COLLEGE
        defaultTeacherShouldBeFound("college.equals=" + DEFAULT_COLLEGE);

        // Get all the teacherList where college equals to UPDATED_COLLEGE
        defaultTeacherShouldNotBeFound("college.equals=" + UPDATED_COLLEGE);
    }

    @Test
    @Transactional
    public void getAllTeachersByCollegeIsInShouldWork() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where college in DEFAULT_COLLEGE or UPDATED_COLLEGE
        defaultTeacherShouldBeFound("college.in=" + DEFAULT_COLLEGE + "," + UPDATED_COLLEGE);

        // Get all the teacherList where college equals to UPDATED_COLLEGE
        defaultTeacherShouldNotBeFound("college.in=" + UPDATED_COLLEGE);
    }

    @Test
    @Transactional
    public void getAllTeachersByCollegeIsNullOrNotNull() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where college is not null
        defaultTeacherShouldBeFound("college.specified=true");

        // Get all the teacherList where college is null
        defaultTeacherShouldNotBeFound("college.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeachersByDescIsEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where desc equals to DEFAULT_DESC
        defaultTeacherShouldBeFound("desc.equals=" + DEFAULT_DESC);

        // Get all the teacherList where desc equals to UPDATED_DESC
        defaultTeacherShouldNotBeFound("desc.equals=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllTeachersByDescIsInShouldWork() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where desc in DEFAULT_DESC or UPDATED_DESC
        defaultTeacherShouldBeFound("desc.in=" + DEFAULT_DESC + "," + UPDATED_DESC);

        // Get all the teacherList where desc equals to UPDATED_DESC
        defaultTeacherShouldNotBeFound("desc.in=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllTeachersByDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where desc is not null
        defaultTeacherShouldBeFound("desc.specified=true");

        // Get all the teacherList where desc is null
        defaultTeacherShouldNotBeFound("desc.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeachersByIsOnJobIsEqualToSomething() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where isOnJob equals to DEFAULT_IS_ON_JOB
        defaultTeacherShouldBeFound("isOnJob.equals=" + DEFAULT_IS_ON_JOB);

        // Get all the teacherList where isOnJob equals to UPDATED_IS_ON_JOB
        defaultTeacherShouldNotBeFound("isOnJob.equals=" + UPDATED_IS_ON_JOB);
    }

    @Test
    @Transactional
    public void getAllTeachersByIsOnJobIsInShouldWork() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where isOnJob in DEFAULT_IS_ON_JOB or UPDATED_IS_ON_JOB
        defaultTeacherShouldBeFound("isOnJob.in=" + DEFAULT_IS_ON_JOB + "," + UPDATED_IS_ON_JOB);

        // Get all the teacherList where isOnJob equals to UPDATED_IS_ON_JOB
        defaultTeacherShouldNotBeFound("isOnJob.in=" + UPDATED_IS_ON_JOB);
    }

    @Test
    @Transactional
    public void getAllTeachersByIsOnJobIsNullOrNotNull() throws Exception {
        // Initialize the database
        teacherRepository.saveAndFlush(teacher);

        // Get all the teacherList where isOnJob is not null
        defaultTeacherShouldBeFound("isOnJob.specified=true");

        // Get all the teacherList where isOnJob is null
        defaultTeacherShouldNotBeFound("isOnJob.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTeacherShouldBeFound(String filter) throws Exception {
        restTeacherMockMvc.perform(get("/api/teachers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teacher.getId().intValue())))
            .andExpect(jsonPath("$.[*].teacherCode").value(hasItem(DEFAULT_TEACHER_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].college").value(hasItem(DEFAULT_COLLEGE.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].isOnJob").value(hasItem(DEFAULT_IS_ON_JOB.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTeacherShouldNotBeFound(String filter) throws Exception {
        restTeacherMockMvc.perform(get("/api/teachers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTeacher() throws Exception {
        // Get the teacher
        restTeacherMockMvc.perform(get("/api/teachers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeacher() throws Exception {
        // Initialize the database
        teacherService.save(teacher);

        int databaseSizeBeforeUpdate = teacherRepository.findAll().size();

        // Update the teacher
        Teacher updatedTeacher = teacherRepository.findOne(teacher.getId());
        // Disconnect from session so that the updates on updatedTeacher are not directly saved in db
        em.detach(updatedTeacher);
        updatedTeacher
            .teacherCode(UPDATED_TEACHER_CODE)
            .name(UPDATED_NAME)
            .subject(UPDATED_SUBJECT)
            .college(UPDATED_COLLEGE)
            .desc(UPDATED_DESC)
            .isOnJob(UPDATED_IS_ON_JOB);

        restTeacherMockMvc.perform(put("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeacher)))
            .andExpect(status().isOk());

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate);
        Teacher testTeacher = teacherList.get(teacherList.size() - 1);
//        assertThat(testTeacher.getTeacherCode()).isEqualTo(UPDATED_TEACHER_CODE);
        assertThat(testTeacher.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTeacher.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testTeacher.getCollege()).isEqualTo(UPDATED_COLLEGE);
        assertThat(testTeacher.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testTeacher.getIsOnJob()).isEqualTo(UPDATED_IS_ON_JOB);
    }

    @Test
    @Transactional
    public void updateNonExistingTeacher() throws Exception {
        int databaseSizeBeforeUpdate = teacherRepository.findAll().size();

        // Create the Teacher

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTeacherMockMvc.perform(put("/api/teachers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teacher)))
            .andExpect(status().isCreated());

        // Validate the Teacher in the database
        List<Teacher> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTeacher() throws Exception {
        // Initialize the database
        teacherService.save(teacher);

        int databaseSizeBeforeDelete = teacherRepository.findAll().size();

        // Get the teacher
        restTeacherMockMvc.perform(delete("/api/teachers/{id}", teacher.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Teacher> teacherList = teacherRepository.findAll();
        assertThat(teacherList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Teacher.class);
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        Teacher teacher2 = new Teacher();
        teacher2.setId(teacher1.getId());
        assertThat(teacher1).isEqualTo(teacher2);
        teacher2.setId(2L);
        assertThat(teacher1).isNotEqualTo(teacher2);
        teacher1.setId(null);
        assertThat(teacher1).isNotEqualTo(teacher2);
    }
}
