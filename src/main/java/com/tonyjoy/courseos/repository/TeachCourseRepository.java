package com.tonyjoy.courseos.repository;

import com.tonyjoy.courseos.domain.TeachCourse;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TeachCourse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeachCourseRepository extends JpaRepository<TeachCourse, Long>, JpaSpecificationExecutor<TeachCourse> {

}
