package com.tonyjoy.courseos.service;

import com.tonyjoy.courseos.domain.Teacher;
import com.tonyjoy.courseos.repository.TeacherRepository;
import com.tonyjoy.courseos.service.util.GenerateCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Teacher.
 */
@Service
@Transactional
public class TeacherService {

    private final Logger log = LoggerFactory.getLogger(TeacherService.class);

    private final TeacherRepository teacherRepository;

    private static final String TEACH = "teach-";

    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    /**
     * Save a teacher.
     *
     * @param teacher the entity to save
     * @return the persisted entity
     */
    public Teacher save(Teacher teacher) {
        teacher = teacherRepository.save(teacher);
        teacher.setTeacherCode(TEACH + GenerateCodeUtil.generate6StringCode(teacher.getId()));
        return teacher;
    }

    /**
     * Get all the teachers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Teacher> findAll(Pageable pageable) {
        log.debug("Request to get all Teachers");
        return teacherRepository.findAll(pageable);
    }

    /**
     * Get one teacher by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Teacher findOne(Long id) {
        log.debug("Request to get Teacher : {}", id);
        return teacherRepository.findOne(id);
    }

    /**
     * Delete the teacher by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Teacher : {}", id);
        teacherRepository.delete(id);
    }
}
