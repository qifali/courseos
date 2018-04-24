package com.tonyjoy.courseos.repository;

import com.tonyjoy.courseos.domain.StuCourse;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the StuCourse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StuCourseRepository extends JpaRepository<StuCourse, Long>, JpaSpecificationExecutor<StuCourse> {

}
