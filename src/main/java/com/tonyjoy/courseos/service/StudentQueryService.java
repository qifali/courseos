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

import com.tonyjoy.courseos.domain.Student;
import com.tonyjoy.courseos.domain.*; // for static metamodels
import com.tonyjoy.courseos.repository.StudentRepository;
import com.tonyjoy.courseos.service.dto.StudentCriteria;

import com.tonyjoy.courseos.domain.enumeration.IsAfterSchoolStatus;

/**
 * Service for executing complex queries for Student entities in the database.
 * The main input is a {@link StudentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Student} or a {@link Page} of {@link Student} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StudentQueryService extends QueryService<Student> {

    private final Logger log = LoggerFactory.getLogger(StudentQueryService.class);


    private final StudentRepository studentRepository;

    public StudentQueryService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Return a {@link List} of {@link Student} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Student> findByCriteria(StudentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Student> specification = createSpecification(criteria);
        return studentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Student} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Student> findByCriteria(StudentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Student> specification = createSpecification(criteria);
        return studentRepository.findAll(specification, page);
    }

    /**
     * Function to convert StudentCriteria to a {@link Specifications}
     */
    private Specifications<Student> createSpecification(StudentCriteria criteria) {
        Specifications<Student> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Student_.id));
            }
            if (criteria.getStuCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStuCode(), Student_.stuCode));
            }
            if (criteria.getChnName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChnName(), Student_.chnName));
            }
            if (criteria.getEngName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEngName(), Student_.engName));
            }
            if (criteria.getGrade() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGrade(), Student_.grade));
            }
            if (criteria.getSchool() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSchool(), Student_.school));
            }
            if (criteria.getDesc() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesc(), Student_.desc));
            }
            if (criteria.getIsAfterSchool() != null) {
                specification = specification.and(buildSpecification(criteria.getIsAfterSchool(), Student_.isAfterSchool));
            }
        }
        return specification;
    }

}
