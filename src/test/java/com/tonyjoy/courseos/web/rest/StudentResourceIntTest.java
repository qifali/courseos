package com.tonyjoy.courseos.web.rest;

import com.tonyjoy.courseos.CourseosApp;

import com.tonyjoy.courseos.domain.Student;
import com.tonyjoy.courseos.repository.StudentRepository;
import com.tonyjoy.courseos.service.StudentService;
import com.tonyjoy.courseos.web.rest.errors.ExceptionTranslator;
import com.tonyjoy.courseos.service.dto.StudentCriteria;
import com.tonyjoy.courseos.service.StudentQueryService;

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

import com.tonyjoy.courseos.domain.enumeration.IsAfterSchoolStatus;
/**
 * Test class for the StudentResource REST controller.
 *
 * @see StudentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CourseosApp.class)
public class StudentResourceIntTest {

    private static final String DEFAULT_STU_CODE = "AAAAAAAAAA";
    private static final String UPDATED_STU_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CHN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

    private static final String DEFAULT_SCHOOL = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final IsAfterSchoolStatus DEFAULT_IS_AFTER_SCHOOL = IsAfterSchoolStatus.YES;
    private static final IsAfterSchoolStatus UPDATED_IS_AFTER_SCHOOL = IsAfterSchoolStatus.NO;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentQueryService studentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStudentMockMvc;

    private Student student;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentResource studentResource = new StudentResource(studentService, studentQueryService);
        this.restStudentMockMvc = MockMvcBuilders.standaloneSetup(studentResource)
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
    public static Student createEntity(EntityManager em) {
        Student student = new Student()
            .stuCode(DEFAULT_STU_CODE)
            .chnName(DEFAULT_CHN_NAME)
            .engName(DEFAULT_ENG_NAME)
            .grade(DEFAULT_GRADE)
            .school(DEFAULT_SCHOOL)
            .desc(DEFAULT_DESC)
            .isAfterSchool(DEFAULT_IS_AFTER_SCHOOL);
        return student;
    }

    @Before
    public void initTest() {
        student = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = studentList.get(studentList.size() - 1);
//        assertThat(testStudent.getStuCode()).isEqualTo(DEFAULT_STU_CODE);
        assertThat(testStudent.getChnName()).isEqualTo(DEFAULT_CHN_NAME);
        assertThat(testStudent.getEngName()).isEqualTo(DEFAULT_ENG_NAME);
        assertThat(testStudent.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testStudent.getSchool()).isEqualTo(DEFAULT_SCHOOL);
        assertThat(testStudent.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testStudent.getIsAfterSchool()).isEqualTo(DEFAULT_IS_AFTER_SCHOOL);
    }

    @Test
    @Transactional
    public void createStudentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student with an existing ID
        student.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList
        restStudentMockMvc.perform(get("/api/students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].stuCode").value(hasItem(DEFAULT_STU_CODE.toString())))
            .andExpect(jsonPath("$.[*].chnName").value(hasItem(DEFAULT_CHN_NAME.toString())))
            .andExpect(jsonPath("$.[*].engName").value(hasItem(DEFAULT_ENG_NAME.toString())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.toString())))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].isAfterSchool").value(hasItem(DEFAULT_IS_AFTER_SCHOOL.toString())));
    }

    @Test
    @Transactional
    public void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.stuCode").value(DEFAULT_STU_CODE.toString()))
            .andExpect(jsonPath("$.chnName").value(DEFAULT_CHN_NAME.toString()))
            .andExpect(jsonPath("$.engName").value(DEFAULT_ENG_NAME.toString()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.toString()))
            .andExpect(jsonPath("$.school").value(DEFAULT_SCHOOL.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()))
            .andExpect(jsonPath("$.isAfterSchool").value(DEFAULT_IS_AFTER_SCHOOL.toString()));
    }

    @Test
    @Transactional
    public void getAllStudentsByStuCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where stuCode equals to DEFAULT_STU_CODE
        defaultStudentShouldBeFound("stuCode.equals=" + DEFAULT_STU_CODE);

        // Get all the studentList where stuCode equals to UPDATED_STU_CODE
        defaultStudentShouldNotBeFound("stuCode.equals=" + UPDATED_STU_CODE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStuCodeIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where stuCode in DEFAULT_STU_CODE or UPDATED_STU_CODE
        defaultStudentShouldBeFound("stuCode.in=" + DEFAULT_STU_CODE + "," + UPDATED_STU_CODE);

        // Get all the studentList where stuCode equals to UPDATED_STU_CODE
        defaultStudentShouldNotBeFound("stuCode.in=" + UPDATED_STU_CODE);
    }

    @Test
    @Transactional
    public void getAllStudentsByStuCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where stuCode is not null
        defaultStudentShouldBeFound("stuCode.specified=true");

        // Get all the studentList where stuCode is null
        defaultStudentShouldNotBeFound("stuCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentsByChnNameIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where chnName equals to DEFAULT_CHN_NAME
        defaultStudentShouldBeFound("chnName.equals=" + DEFAULT_CHN_NAME);

        // Get all the studentList where chnName equals to UPDATED_CHN_NAME
        defaultStudentShouldNotBeFound("chnName.equals=" + UPDATED_CHN_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByChnNameIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where chnName in DEFAULT_CHN_NAME or UPDATED_CHN_NAME
        defaultStudentShouldBeFound("chnName.in=" + DEFAULT_CHN_NAME + "," + UPDATED_CHN_NAME);

        // Get all the studentList where chnName equals to UPDATED_CHN_NAME
        defaultStudentShouldNotBeFound("chnName.in=" + UPDATED_CHN_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByChnNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where chnName is not null
        defaultStudentShouldBeFound("chnName.specified=true");

        // Get all the studentList where chnName is null
        defaultStudentShouldNotBeFound("chnName.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentsByEngNameIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where engName equals to DEFAULT_ENG_NAME
        defaultStudentShouldBeFound("engName.equals=" + DEFAULT_ENG_NAME);

        // Get all the studentList where engName equals to UPDATED_ENG_NAME
        defaultStudentShouldNotBeFound("engName.equals=" + UPDATED_ENG_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByEngNameIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where engName in DEFAULT_ENG_NAME or UPDATED_ENG_NAME
        defaultStudentShouldBeFound("engName.in=" + DEFAULT_ENG_NAME + "," + UPDATED_ENG_NAME);

        // Get all the studentList where engName equals to UPDATED_ENG_NAME
        defaultStudentShouldNotBeFound("engName.in=" + UPDATED_ENG_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByEngNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where engName is not null
        defaultStudentShouldBeFound("engName.specified=true");

        // Get all the studentList where engName is null
        defaultStudentShouldNotBeFound("engName.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentsByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where grade equals to DEFAULT_GRADE
        defaultStudentShouldBeFound("grade.equals=" + DEFAULT_GRADE);

        // Get all the studentList where grade equals to UPDATED_GRADE
        defaultStudentShouldNotBeFound("grade.equals=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    public void getAllStudentsByGradeIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where grade in DEFAULT_GRADE or UPDATED_GRADE
        defaultStudentShouldBeFound("grade.in=" + DEFAULT_GRADE + "," + UPDATED_GRADE);

        // Get all the studentList where grade equals to UPDATED_GRADE
        defaultStudentShouldNotBeFound("grade.in=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    public void getAllStudentsByGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where grade is not null
        defaultStudentShouldBeFound("grade.specified=true");

        // Get all the studentList where grade is null
        defaultStudentShouldNotBeFound("grade.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentsBySchoolIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where school equals to DEFAULT_SCHOOL
        defaultStudentShouldBeFound("school.equals=" + DEFAULT_SCHOOL);

        // Get all the studentList where school equals to UPDATED_SCHOOL
        defaultStudentShouldNotBeFound("school.equals=" + UPDATED_SCHOOL);
    }

    @Test
    @Transactional
    public void getAllStudentsBySchoolIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where school in DEFAULT_SCHOOL or UPDATED_SCHOOL
        defaultStudentShouldBeFound("school.in=" + DEFAULT_SCHOOL + "," + UPDATED_SCHOOL);

        // Get all the studentList where school equals to UPDATED_SCHOOL
        defaultStudentShouldNotBeFound("school.in=" + UPDATED_SCHOOL);
    }

    @Test
    @Transactional
    public void getAllStudentsBySchoolIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where school is not null
        defaultStudentShouldBeFound("school.specified=true");

        // Get all the studentList where school is null
        defaultStudentShouldNotBeFound("school.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentsByDescIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where desc equals to DEFAULT_DESC
        defaultStudentShouldBeFound("desc.equals=" + DEFAULT_DESC);

        // Get all the studentList where desc equals to UPDATED_DESC
        defaultStudentShouldNotBeFound("desc.equals=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllStudentsByDescIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where desc in DEFAULT_DESC or UPDATED_DESC
        defaultStudentShouldBeFound("desc.in=" + DEFAULT_DESC + "," + UPDATED_DESC);

        // Get all the studentList where desc equals to UPDATED_DESC
        defaultStudentShouldNotBeFound("desc.in=" + UPDATED_DESC);
    }

    @Test
    @Transactional
    public void getAllStudentsByDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where desc is not null
        defaultStudentShouldBeFound("desc.specified=true");

        // Get all the studentList where desc is null
        defaultStudentShouldNotBeFound("desc.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentsByIsAfterSchoolIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where isAfterSchool equals to DEFAULT_IS_AFTER_SCHOOL
        defaultStudentShouldBeFound("isAfterSchool.equals=" + DEFAULT_IS_AFTER_SCHOOL);

        // Get all the studentList where isAfterSchool equals to UPDATED_IS_AFTER_SCHOOL
        defaultStudentShouldNotBeFound("isAfterSchool.equals=" + UPDATED_IS_AFTER_SCHOOL);
    }

    @Test
    @Transactional
    public void getAllStudentsByIsAfterSchoolIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where isAfterSchool in DEFAULT_IS_AFTER_SCHOOL or UPDATED_IS_AFTER_SCHOOL
        defaultStudentShouldBeFound("isAfterSchool.in=" + DEFAULT_IS_AFTER_SCHOOL + "," + UPDATED_IS_AFTER_SCHOOL);

        // Get all the studentList where isAfterSchool equals to UPDATED_IS_AFTER_SCHOOL
        defaultStudentShouldNotBeFound("isAfterSchool.in=" + UPDATED_IS_AFTER_SCHOOL);
    }

    @Test
    @Transactional
    public void getAllStudentsByIsAfterSchoolIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where isAfterSchool is not null
        defaultStudentShouldBeFound("isAfterSchool.specified=true");

        // Get all the studentList where isAfterSchool is null
        defaultStudentShouldNotBeFound("isAfterSchool.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultStudentShouldBeFound(String filter) throws Exception {
        restStudentMockMvc.perform(get("/api/students?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].stuCode").value(hasItem(DEFAULT_STU_CODE.toString())))
            .andExpect(jsonPath("$.[*].chnName").value(hasItem(DEFAULT_CHN_NAME.toString())))
            .andExpect(jsonPath("$.[*].engName").value(hasItem(DEFAULT_ENG_NAME.toString())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.toString())))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())))
            .andExpect(jsonPath("$.[*].isAfterSchool").value(hasItem(DEFAULT_IS_AFTER_SCHOOL.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultStudentShouldNotBeFound(String filter) throws Exception {
        restStudentMockMvc.perform(get("/api/students?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudent() throws Exception {
        // Initialize the database
        studentService.save(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        Student updatedStudent = studentRepository.findOne(student.getId());
        // Disconnect from session so that the updates on updatedStudent are not directly saved in db
        em.detach(updatedStudent);
        updatedStudent
            .stuCode(UPDATED_STU_CODE)
            .chnName(UPDATED_CHN_NAME)
            .engName(UPDATED_ENG_NAME)
            .grade(UPDATED_GRADE)
            .school(UPDATED_SCHOOL)
            .desc(UPDATED_DESC)
            .isAfterSchool(UPDATED_IS_AFTER_SCHOOL);

        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudent)))
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
//        assertThat(testStudent.getStuCode()).isEqualTo(UPDATED_STU_CODE);
        assertThat(testStudent.getChnName()).isEqualTo(UPDATED_CHN_NAME);
        assertThat(testStudent.getEngName()).isEqualTo(UPDATED_ENG_NAME);
        assertThat(testStudent.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testStudent.getSchool()).isEqualTo(UPDATED_SCHOOL);
        assertThat(testStudent.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testStudent.getIsAfterSchool()).isEqualTo(UPDATED_IS_AFTER_SCHOOL);
    }

    @Test
    @Transactional
    public void updateNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Create the Student

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStudent() throws Exception {
        // Initialize the database
        studentService.save(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Get the student
        restStudentMockMvc.perform(delete("/api/students/{id}", student.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Student.class);
        Student student1 = new Student();
        student1.setId(1L);
        Student student2 = new Student();
        student2.setId(student1.getId());
        assertThat(student1).isEqualTo(student2);
        student2.setId(2L);
        assertThat(student1).isNotEqualTo(student2);
        student1.setId(null);
        assertThat(student1).isNotEqualTo(student2);
    }
}
