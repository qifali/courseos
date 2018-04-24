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

import com.tonyjoy.courseos.domain.Teacher;
import com.tonyjoy.courseos.domain.*; // for static metamodels
import com.tonyjoy.courseos.repository.TeacherRepository;
import com.tonyjoy.courseos.service.dto.TeacherCriteria;

import com.tonyjoy.courseos.domain.enumeration.IsOnJobStatus;

/**
 * Service for executing complex queries for Teacher entities in the database.
 * The main input is a {@link TeacherCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Teacher} or a {@link Page} of {@link Teacher} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TeacherQueryService extends QueryService<Teacher> {

    private final Logger log = LoggerFactory.getLogger(TeacherQueryService.class);


    private final TeacherRepository teacherRepository;

    public TeacherQueryService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    /**
     * Return a {@link List} of {@link Teacher} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Teacher> findByCriteria(TeacherCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Teacher> specification = createSpecification(criteria);
        return teacherRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Teacher} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Teacher> findByCriteria(TeacherCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Teacher> specification = createSpecification(criteria);
        return teacherRepository.findAll(specification, page);
    }

    /**
     * Function to convert TeacherCriteria to a {@link Specifications}
     */
    private Specifications<Teacher> createSpecification(TeacherCriteria criteria) {
        Specifications<Teacher> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Teacher_.id));
            }
            if (criteria.getTeacherCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTeacherCode(), Teacher_.teacherCode));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Teacher_.name));
            }
            if (criteria.getSubject() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubject(), Teacher_.subject));
            }
            if (criteria.getCollege() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCollege(), Teacher_.college));
            }
            if (criteria.getDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesc(), Teacher_.desc));
            }
            if (criteria.getIsOnJob() != null) {
                specification = specification.and(buildSpecification(criteria.getIsOnJob(), Teacher_.isOnJob));
            }
        }
        return specification;
    }

}
