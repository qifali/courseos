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

import com.tonyjoy.courseos.domain.TeachCourse;
import com.tonyjoy.courseos.domain.*; // for static metamodels
import com.tonyjoy.courseos.repository.TeachCourseRepository;
import com.tonyjoy.courseos.service.dto.TeachCourseCriteria;

import com.tonyjoy.courseos.domain.enumeration.TeachCourseType;

/**
 * Service for executing complex queries for TeachCourse entities in the database.
 * The main input is a {@link TeachCourseCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TeachCourse} or a {@link Page} of {@link TeachCourse} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TeachCourseQueryService extends QueryService<TeachCourse> {

    private final Logger log = LoggerFactory.getLogger(TeachCourseQueryService.class);


    private final TeachCourseRepository teachCourseRepository;

    public TeachCourseQueryService(TeachCourseRepository teachCourseRepository) {
        this.teachCourseRepository = teachCourseRepository;
    }

    /**
     * Return a {@link List} of {@link TeachCourse} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TeachCourse> findByCriteria(TeachCourseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<TeachCourse> specification = createSpecification(criteria);
        return teachCourseRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TeachCourse} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TeachCourse> findByCriteria(TeachCourseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<TeachCourse> specification = createSpecification(criteria);
        return teachCourseRepository.findAll(specification, page);
    }

    /**
     * Function to convert TeachCourseCriteria to a {@link Specifications}
     */
    private Specifications<TeachCourse> createSpecification(TeachCourseCriteria criteria) {
        Specifications<TeachCourse> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TeachCourse_.id));
            }
            if (criteria.getTeachCourseCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeachCourseCode(), TeachCourse_.teachCourseCode));
            }
            if (criteria.getDay() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDay(), TeachCourse_.day));
            }
            if (criteria.getTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTime(), TeachCourse_.time));
            }
            if (criteria.getTeachCourseType() != null) {
                specification = specification.and(buildSpecification(criteria.getTeachCourseType(), TeachCourse_.teachCourseType));
            }
            if (criteria.getFee() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFee(), TeachCourse_.fee));
            }
            if (criteria.getDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesc(), TeachCourse_.desc));
            }
            if (criteria.getCount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCount(), TeachCourse_.count));
            }
            if (criteria.getCourseDue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCourseDue(), TeachCourse_.courseDue));
            }
            if (criteria.getTeacherId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTeacherId(), TeachCourse_.teacher, Teacher_.id));
            }
            if (criteria.getCourseId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCourseId(), TeachCourse_.course, Course_.id));
            }
        }
        return specification;
    }

}
