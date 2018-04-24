package com.tonyjoy.courseos.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.tonyjoy.courseos.domain.StuCourse;
import com.tonyjoy.courseos.domain.*; // for static metamodels
import com.tonyjoy.courseos.repository.StuCourseRepository;
import com.tonyjoy.courseos.service.dto.StuCourseCriteria;


/**
 * Service for executing complex queries for StuCourse entities in the database.
 * The main input is a {@link StuCourseCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StuCourse} or a {@link Page} of {@link StuCourse} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StuCourseQueryService extends QueryService<StuCourse> {

    private final Logger log = LoggerFactory.getLogger(StuCourseQueryService.class);


    private final StuCourseRepository stuCourseRepository;

    public StuCourseQueryService(StuCourseRepository stuCourseRepository) {
        this.stuCourseRepository = stuCourseRepository;
    }

    /**
     * Return a {@link List} of {@link StuCourse} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StuCourse> findByCriteria(StuCourseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<StuCourse> specification = createSpecification(criteria);
        return stuCourseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StuCourse} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StuCourse> findByCriteria(StuCourseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<StuCourse> specification = createSpecification(criteria);
        return stuCourseRepository.findAll(specification, page);
    }

    /**
     * Function to convert StuCourseCriteria to a {@link Specifications}
     */
    private Specifications<StuCourse> createSpecification(StuCourseCriteria criteria) {
        Specifications<StuCourse> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StuCourse_.id));
            }
            if (criteria.getDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesc(), StuCourse_.desc));
            }
            if (criteria.getPhase() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhase(), StuCourse_.phase));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStudentId(), StuCourse_.student, Student_.id));
            }
            if (criteria.getTeachCourseId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTeachCourseId(), StuCourse_.teachCourse, TeachCourse_.id));
            }
        }
        return specification;
    }

}
