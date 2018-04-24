package com.tonyjoy.courseos.service;

import com.tonyjoy.courseos.domain.StuCourse;
import com.tonyjoy.courseos.repository.StuCourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing StuCourse.
 */
@Service
@Transactional
public class StuCourseService {

    private final Logger log = LoggerFactory.getLogger(StuCourseService.class);

    private final StuCourseRepository stuCourseRepository;

    public StuCourseService(StuCourseRepository stuCourseRepository) {
        this.stuCourseRepository = stuCourseRepository;
    }

    /**
     * Save a stuCourse.
     *
     * @param stuCourse the entity to save
     * @return the persisted entity
     */
    public StuCourse save(StuCourse stuCourse) {
        log.debug("Request to save StuCourse : {}", stuCourse);
        return stuCourseRepository.save(stuCourse);
    }

    /**
     * Get all the stuCourses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<StuCourse> findAll(Pageable pageable) {
        log.debug("Request to get all StuCourses");
        return stuCourseRepository.findAll(pageable);
    }

    /**
     * Get one stuCourse by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public StuCourse findOne(Long id) {
        log.debug("Request to get StuCourse : {}", id);
        return stuCourseRepository.findOne(id);
    }

    /**
     * Delete the stuCourse by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete StuCourse : {}", id);
        stuCourseRepository.delete(id);
    }
}
