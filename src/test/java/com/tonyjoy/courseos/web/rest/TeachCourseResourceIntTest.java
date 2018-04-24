package com.tonyjoy.courseos.web.rest;

import com.tonyjoy.courseos.CourseosApp;

import com.tonyjoy.courseos.domain.TeachCourse;
import com.tonyjoy.courseos.domain.Teacher;
import com.tonyjoy.courseos.domain.Course;
import com.tonyjoy.courseos.repository.TeachCourseRepository;
import com.tonyjoy.courseos.service.TeachCourseService;
import com.tonyjoy.courseos.web.rest.errors.ExceptionTranslator;
import com.tonyjoy.courseos.service.dto.TeachCourseCriteria;
import com.tonyjoy.courseos.service.TeachCourseQueryService;

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

import com.tonyjoy.courseos.domain.enumeration.TeachCourseType;
/**
 * Test class for the TeachCourseResource REST controller.
 *
 * @see TeachCourseResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CourseosApp.class)
public class TeachCourseResourceIntTest {

    private static final String DEFAULT_TEACH_COURSE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_TEACH_COURSE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DAY = "AAAAAAAAAA";
    private static final String UPDATED_DAY = "BBBBBBBBBB";

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    private static final TeachCourseType DEFAULT_TEACH_COURSE_TYPE = TeachCourseType.ONE2MANY;
    private static final TeachCourseType UPDATED_TEACH_COURSE_TYPE = TeachCourseType.ONE2ONE;

    private static final String DEFAULT_FEE = "AAAAAAAAAA";
    private static final String UPDATED_FEE = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_COUNT = "AAAAAAAAAA";
    private static final String UPDATED_COUNT = "BBBBBBBBBB";

    private static final String DEFAULT_COURSE_DUE = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_DUE = "BBBBBBBBBB";

    @Autowired
    private TeachCourseRepository teachCourseRepository;

    @Autowired
    private TeachCourseService teachCourseService;

    @Autowired
    private TeachCourseQueryService teachCourseQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTeachCourseMockMvc;

    private TeachCourse teachCourse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TeachCourseResource teachCourseResource = new TeachCourseResource(teachCourseService, teachCourseQueryService);
        this.restTeachCourseMockMvc = MockMvcBuilders.standaloneSetup(teachCourseResource)
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
    public static TeachCourse createEntity(EntityManager em) {
        TeachCourse teachCourse = new TeachCourse()
            .teachCourseCode(DEFAULT_TEACH_COURSE_CODE)
            .day(DEFAULT_DAY)
            .time(DEFAULT_TIME)
            .teachCourseType(DEFAULT_TEACH_COURSE_TYPE)
            .fee(DEFAULT_FEE)
            .desc(DEFAULT_DESC)
            .count(DEFAULT_COUNT)
            .courseDue(DEFAULT_COURSE_DUE);
        return teachCourse;
    }

    @Before
    public void initTest() {
        teachCourse = createEntity(em);
    }

    @Test
    @Transactional
    public void createTeachCourse() throws Exception {
        int databaseSizeBeforeCreate = teachCourseRepository.findAll().size();

        // Create the TeachCourse
        restTeachCourseMockMvc.perform(post("/api/teach-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachCourse)))
            .andExpect(status().isCreated());

        // Validate the TeachCourse in the database
        List<TeachCourse> teachCourseList = teachCourseRepository.findAll();
        assertThat(teachCourseList).hasSize(databaseSizeBeforeCreate + 1);
        TeachCourse testTeachCourse = teachCourseList.get(teachCourseList.size() - 1);
//        assertThat(testTeachCourse.getTeachCourseCode()).isEqualTo(DEFAULT_TEACH_COURSE_CODE);
        assertThat(testTeachCourse.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testTeachCourse.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testTeachCourse.getTeachCourseType()).isEqualTo(DEFAULT_TEACH_COURSE_TYPE);
        assertThat(testTeachCourse.getFee()).isEqualTo(DEFAULT_FEE);
        assertThat(testTeachCourse.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testTeachCourse.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testTeachCourse.getCourseDue()).isEqualTo(DEFAULT_COURSE_DUE);
    }

    @Test
    @Transactional
    public void createTeachCourseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teachCourseRepository.findAll().size();

        // Create the TeachCourse with an existing ID
        teachCourse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeachCourseMockMvc.perform(post("/api/teach-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachCourse)))
            .andExpect(status().isBadRequest());

        // Validate the TeachCourse in the database
        List<TeachCourse> teachCourseList = teachCourseRepository.findAll();
        assertThat(teachCourseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTeachCourses() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList
        restTeachCourseMockMvc.perform(get("/api/teach-courses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teachCourse.getId().intValue())))
            .andExpect(jsonPath("$.[*].teachCourseCode").value(hasItem(DEFAULT_TEACH_COURSE_CODE.toString())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].teachCourseType").value(hasItem(DEFAULT_TEACH_COURSE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT.toString())))
            .andExpect(jsonPath("$.[*].courseDue").value(hasItem(DEFAULT_COURSE_DUE.toString())));
    }

    @Test
    @Transactional
    public void getTeachCourse() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get the teachCourse
        restTeachCourseMockMvc.perform(get("/api/teach-courses/{id}", teachCourse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(teachCourse.getId().intValue()))
            .andExpect(jsonPath("$.teachCourseCode").value(DEFAULT_TEACH_COURSE_CODE.toString()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.teachCourseType").value(DEFAULT_TEACH_COURSE_TYPE.toString()))
            .andExpect(jsonPath("$.fee").value(DEFAULT_FEE.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT.toString()))
            .andExpect(jsonPath("$.courseDue").value(DEFAULT_COURSE_DUE.toString()));
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByTeachCourseCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where teachCourseCode equals to DEFAULT_TEACH_COURSE_CODE
        defaultTeachCourseShouldBeFound("teachCourseCode.equals=" + DEFAULT_TEACH_COURSE_CODE);

        // Get all the teachCourseList where teachCourseCode equals to UPDATED_TEACH_COURSE_CODE
        defaultTeachCourseShouldNotBeFound("teachCourseCode.equals=" + UPDATED_TEACH_COURSE_CODE);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByTeachCourseCodeIsInShouldWork() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where teachCourseCode in DEFAULT_TEACH_COURSE_CODE or UPDATED_TEACH_COURSE_CODE
        defaultTeachCourseShouldBeFound("teachCourseCode.in=" + DEFAULT_TEACH_COURSE_CODE + "," + UPDATED_TEACH_COURSE_CODE);

        // Get all the teachCourseList where teachCourseCode equals to UPDATED_TEACH_COURSE_CODE
        defaultTeachCourseShouldNotBeFound("teachCourseCode.in=" + UPDATED_TEACH_COURSE_CODE);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByTeachCourseCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where teachCourseCode is not null
        defaultTeachCourseShouldBeFound("teachCourseCode.specified=true");

        // Get all the teachCourseList where teachCourseCode is null
        defaultTeachCourseShouldNotBeFound("teachCourseCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByDayIsEqualToSomething() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where day equals to DEFAULT_DAY
        defaultTeachCourseShouldBeFound("day.equals=" + DEFAULT_DAY);

        // Get all the teachCourseList where day equals to UPDATED_DAY
        defaultTeachCourseShouldNotBeFound("day.equals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByDayIsInShouldWork() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where day in DEFAULT_DAY or UPDATED_DAY
        defaultTeachCourseShouldBeFound("day.in=" + DEFAULT_DAY + "," + UPDATED_DAY);

        // Get all the teachCourseList where day equals to UPDATED_DAY
        defaultTeachCourseShouldNotBeFound("day.in=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where day is not null
        defaultTeachCourseShouldBeFound("day.specified=true");

        // Get all the teachCourseList where day is null
        defaultTeachCourseShouldNotBeFound("day.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where time equals to DEFAULT_TIME
        defaultTeachCourseShouldBeFound("time.equals=" + DEFAULT_TIME);

        // Get all the teachCourseList where time equals to UPDATED_TIME
        defaultTeachCourseShouldNotBeFound("time.equals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByTimeIsInShouldWork() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where time in DEFAULT_TIME or UPDATED_TIME
        defaultTeachCourseShouldBeFound("time.in=" + DEFAULT_TIME + "," + UPDATED_TIME);

        // Get all the teachCourseList where time equals to UPDATED_TIME
        defaultTeachCourseShouldNotBeFound("time.in=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where time is not null
        defaultTeachCourseShouldBeFound("time.specified=true");

        // Get all the teachCourseList where time is null
        defaultTeachCourseShouldNotBeFound("time.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByTeachCourseTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where teachCourseType equals to DEFAULT_TEACH_COURSE_TYPE
        defaultTeachCourseShouldBeFound("teachCourseType.equals=" + DEFAULT_TEACH_COURSE_TYPE);

        // Get all the teachCourseList where teachCourseType equals to UPDATED_TEACH_COURSE_TYPE
        defaultTeachCourseShouldNotBeFound("teachCourseType.equals=" + UPDATED_TEACH_COURSE_TYPE);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByTeachCourseTypeIsInShouldWork() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where teachCourseType in DEFAULT_TEACH_COURSE_TYPE or UPDATED_TEACH_COURSE_TYPE
        defaultTeachCourseShouldBeFound("teachCourseType.in=" + DEFAULT_TEACH_COURSE_TYPE + "," + UPDATED_TEACH_COURSE_TYPE);

        // Get all the teachCourseList where teachCourseType equals to UPDATED_TEACH_COURSE_TYPE
        defaultTeachCourseShouldNotBeFound("teachCourseType.in=" + UPDATED_TEACH_COURSE_TYPE);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByTeachCourseTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where teachCourseType is not null
        defaultTeachCourseShouldBeFound("teachCourseType.specified=true");

        // Get all the teachCourseList where teachCourseType is null
        defaultTeachCourseShouldNotBeFound("teachCourseType.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByFeeIsEqualToSomething() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where fee equals to DEFAULT_FEE
        defaultTeachCourseShouldBeFound("fee.equals=" + DEFAULT_FEE);

        // Get all the teachCourseList where fee equals to UPDATED_FEE
        defaultTeachCourseShouldNotBeFound("fee.equals=" + UPDATED_FEE);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByFeeIsInShouldWork() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where fee in DEFAULT_FEE or UPDATED_FEE
        defaultTeachCourseShouldBeFound("fee.in=" + DEFAULT_FEE + "," + UPDATED_FEE);

        // Get all the teachCourseList where fee equals to UPDATED_FEE
        defaultTeachCourseShouldNotBeFound("fee.in=" + UPDATED_FEE);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByFeeIsNullOrNotNull() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where fee is not null
        defaultTeachCourseShouldBeFound("fee.specified=true");

        // Get all the teachCourseList where fee is null
        defaultTeachCourseShouldNotBeFound("fee.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByDescIsEqualToSomething() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where desc equals to DEFAULT_DESC
        defaultTeachCourseShouldBeFound("desc.equals=" + DEFAULT_DESC);

        // Get all the teachCourseList where desc equals to UPDATED_DESC
        defaultTeachCourseShouldNotBeFound("desc.equals=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByDescIsInShouldWork() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where desc in DEFAULT_DESC or UPDATED_DESC
        defaultTeachCourseShouldBeFound("desc.in=" + DEFAULT_DESC + "," + UPDATED_DESC);

        // Get all the teachCourseList where desc equals to UPDATED_DESC
        defaultTeachCourseShouldNotBeFound("desc.in=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where desc is not null
        defaultTeachCourseShouldBeFound("desc.specified=true");

        // Get all the teachCourseList where desc is null
        defaultTeachCourseShouldNotBeFound("desc.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByCountIsEqualToSomething() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where count equals to DEFAULT_COUNT
        defaultTeachCourseShouldBeFound("count.equals=" + DEFAULT_COUNT);

        // Get all the teachCourseList where count equals to UPDATED_COUNT
        defaultTeachCourseShouldNotBeFound("count.equals=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByCountIsInShouldWork() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where count in DEFAULT_COUNT or UPDATED_COUNT
        defaultTeachCourseShouldBeFound("count.in=" + DEFAULT_COUNT + "," + UPDATED_COUNT);

        // Get all the teachCourseList where count equals to UPDATED_COUNT
        defaultTeachCourseShouldNotBeFound("count.in=" + UPDATED_COUNT);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where count is not null
        defaultTeachCourseShouldBeFound("count.specified=true");

        // Get all the teachCourseList where count is null
        defaultTeachCourseShouldNotBeFound("count.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByCourseDueIsEqualToSomething() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where courseDue equals to DEFAULT_COURSE_DUE
        defaultTeachCourseShouldBeFound("courseDue.equals=" + DEFAULT_COURSE_DUE);

        // Get all the teachCourseList where courseDue equals to UPDATED_COURSE_DUE
        defaultTeachCourseShouldNotBeFound("courseDue.equals=" + UPDATED_COURSE_DUE);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByCourseDueIsInShouldWork() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where courseDue in DEFAULT_COURSE_DUE or UPDATED_COURSE_DUE
        defaultTeachCourseShouldBeFound("courseDue.in=" + DEFAULT_COURSE_DUE + "," + UPDATED_COURSE_DUE);

        // Get all the teachCourseList where courseDue equals to UPDATED_COURSE_DUE
        defaultTeachCourseShouldNotBeFound("courseDue.in=" + UPDATED_COURSE_DUE);
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByCourseDueIsNullOrNotNull() throws Exception {
        // Initialize the database
        teachCourseRepository.saveAndFlush(teachCourse);

        // Get all the teachCourseList where courseDue is not null
        defaultTeachCourseShouldBeFound("courseDue.specified=true");

        // Get all the teachCourseList where courseDue is null
        defaultTeachCourseShouldNotBeFound("courseDue.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeachCoursesByTeacherIsEqualToSomething() throws Exception {
        // Initialize the database
        Teacher teacher = TeacherResourceIntTest.createEntity(em);
        em.persist(teacher);
        em.flush();
        teachCourse.setTeacher(teacher);
        teachCourseRepository.saveAndFlush(teachCourse);
        Long teacherId = teacher.getId();

        // Get all the teachCourseList where teacher equals to teacherId
        defaultTeachCourseShouldBeFound("teacherId.equals=" + teacherId);

        // Get all the teachCourseList where teacher equals to teacherId + 1
        defaultTeachCourseShouldNotBeFound("teacherId.equals=" + (teacherId + 1));
    }


    @Test
    @Transactional
    public void getAllTeachCoursesByCourseIsEqualToSomething() throws Exception {
        // Initialize the database
        Course course = CourseResourceIntTest.createEntity(em);
        em.persist(course);
        em.flush();
        teachCourse.setCourse(course);
        teachCourseRepository.saveAndFlush(teachCourse);
        Long courseId = course.getId();

        // Get all the teachCourseList where course equals to courseId
        defaultTeachCourseShouldBeFound("courseId.equals=" + courseId);

        // Get all the teachCourseList where course equals to courseId + 1
        defaultTeachCourseShouldNotBeFound("courseId.equals=" + (courseId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTeachCourseShouldBeFound(String filter) throws Exception {
        restTeachCourseMockMvc.perform(get("/api/teach-courses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teachCourse.getId().intValue())))
            .andExpect(jsonPath("$.[*].teachCourseCode").value(hasItem(DEFAULT_TEACH_COURSE_CODE.toString())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].teachCourseType").value(hasItem(DEFAULT_TEACH_COURSE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fee").value(hasItem(DEFAULT_FEE.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT.toString())))
            .andExpect(jsonPath("$.[*].courseDue").value(hasItem(DEFAULT_COURSE_DUE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTeachCourseShouldNotBeFound(String filter) throws Exception {
        restTeachCourseMockMvc.perform(get("/api/teach-courses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTeachCourse() throws Exception {
        // Get the teachCourse
        restTeachCourseMockMvc.perform(get("/api/teach-courses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTeachCourse() throws Exception {
        // Initialize the database
        teachCourseService.save(teachCourse);

        int databaseSizeBeforeUpdate = teachCourseRepository.findAll().size();

        // Update the teachCourse
        TeachCourse updatedTeachCourse = teachCourseRepository.findOne(teachCourse.getId());
        // Disconnect from session so that the updates on updatedTeachCourse are not directly saved in db
        em.detach(updatedTeachCourse);
        updatedTeachCourse
            .teachCourseCode(UPDATED_TEACH_COURSE_CODE)
            .day(UPDATED_DAY)
            .time(UPDATED_TIME)
            .teachCourseType(UPDATED_TEACH_COURSE_TYPE)
            .fee(UPDATED_FEE)
            .desc(UPDATED_DESC)
            .count(UPDATED_COUNT)
            .courseDue(UPDATED_COURSE_DUE);

        restTeachCourseMockMvc.perform(put("/api/teach-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeachCourse)))
            .andExpect(status().isOk());

        // Validate the TeachCourse in the database
        List<TeachCourse> teachCourseList = teachCourseRepository.findAll();
        assertThat(teachCourseList).hasSize(databaseSizeBeforeUpdate);
        TeachCourse testTeachCourse = teachCourseList.get(teachCourseList.size() - 1);
//        assertThat(testTeachCourse.getTeachCourseCode()).isEqualTo(UPDATED_TEACH_COURSE_CODE);
        assertThat(testTeachCourse.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testTeachCourse.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testTeachCourse.getTeachCourseType()).isEqualTo(UPDATED_TEACH_COURSE_TYPE);
        assertThat(testTeachCourse.getFee()).isEqualTo(UPDATED_FEE);
        assertThat(testTeachCourse.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testTeachCourse.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testTeachCourse.getCourseDue()).isEqualTo(UPDATED_COURSE_DUE);
    }

    @Test
    @Transactional
    public void updateNonExistingTeachCourse() throws Exception {
        int databaseSizeBeforeUpdate = teachCourseRepository.findAll().size();

        // Create the TeachCourse

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTeachCourseMockMvc.perform(put("/api/teach-courses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(teachCourse)))
            .andExpect(status().isCreated());

        // Validate the TeachCourse in the database
        List<TeachCourse> teachCourseList = teachCourseRepository.findAll();
        assertThat(teachCourseList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTeachCourse() throws Exception {
        // Initialize the database
        teachCourseService.save(teachCourse);

        int databaseSizeBeforeDelete = teachCourseRepository.findAll().size();

        // Get the teachCourse
        restTeachCourseMockMvc.perform(delete("/api/teach-courses/{id}", teachCourse.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TeachCourse> teachCourseList = teachCourseRepository.findAll();
        assertThat(teachCourseList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeachCourse.class);
        TeachCourse teachCourse1 = new TeachCourse();
        teachCourse1.setId(1L);
        TeachCourse teachCourse2 = new TeachCourse();
        teachCourse2.setId(teachCourse1.getId());
        assertThat(teachCourse1).isEqualTo(teachCourse2);
        teachCourse2.setId(2L);
        assertThat(teachCourse1).isNotEqualTo(teachCourse2);
        teachCourse1.setId(null);
        assertThat(teachCourse1).isNotEqualTo(teachCourse2);
    }
}
