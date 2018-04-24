package com.tonyjoy.courseos.service;

import com.tonyjoy.courseos.domain.TeachCourse;
import com.tonyjoy.courseos.repository.TeachCourseRepository;
import com.tonyjoy.courseos.service.util.GenerateCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing TeachCourse.
 */
@Service
@Transactional
public class TeachCourseService {

    private final Logger log = LoggerFactory.getLogger(TeachCourseService.class);

    private final TeachCourseRepository teachCourseRepository;

    private static final String TEACHCOU = "teachcou-";

    public TeachCourseService(TeachCourseRepository teachCourseRepository) {
        this.teachCourseRepository = teachCourseRepository;
    }

    /**
     * Save a teachCourse.
     *
     * @param teachCourse the entity to save
     * @return the persisted entity
     */
    public TeachCourse save(TeachCourse teachCourse) {
        teachCourse = teachCourseRepository.save(teachCourse);
        teachCourse.setTeachCourseCode(TEACHCOU + GenerateCodeUtil.generate6StringCode(teachCourse.getId()));
        return teachCourse;
    }

    /**
     * Get all the teachCourses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TeachCourse> findAll(Pageable pageable) {
        log.debug("Request to get all TeachCourses");
        return teachCourseRepository.findAll(pageable);
    }

    /**
     * Get one teachCourse by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public TeachCourse findOne(Long id) {
        log.debug("Request to get TeachCourse : {}", id);
        return teachCourseRepository.findOne(id);
    }

    /**
     * Delete the teachCourse by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TeachCourse : {}", id);
        teachCourseRepository.delete(id);
    }
}
