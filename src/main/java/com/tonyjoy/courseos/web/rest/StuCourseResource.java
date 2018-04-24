package com.tonyjoy.courseos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tonyjoy.courseos.domain.StuCourse;
import com.tonyjoy.courseos.service.StuCourseService;
import com.tonyjoy.courseos.web.rest.errors.BadRequestAlertException;
import com.tonyjoy.courseos.web.rest.util.HeaderUtil;
import com.tonyjoy.courseos.web.rest.util.PaginationUtil;
import com.tonyjoy.courseos.service.dto.StuCourseCriteria;
import com.tonyjoy.courseos.service.StuCourseQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StuCourse.
 */
@RestController
@RequestMapping("/api")
public class StuCourseResource {

    private final Logger log = LoggerFactory.getLogger(StuCourseResource.class);

    private static final String ENTITY_NAME = "stuCourse";

    private final StuCourseService stuCourseService;

    private final StuCourseQueryService stuCourseQueryService;

    public StuCourseResource(StuCourseService stuCourseService, StuCourseQueryService stuCourseQueryService) {
        this.stuCourseService = stuCourseService;
        this.stuCourseQueryService = stuCourseQueryService;
    }

    /**
     * POST  /stu-courses : Create a new stuCourse.
     *
     * @param stuCourse the stuCourse to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stuCourse, or with status 400 (Bad Request) if the stuCourse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stu-courses")
    @Timed
    public ResponseEntity<StuCourse> createStuCourse(@RequestBody StuCourse stuCourse) throws URISyntaxException {
        log.debug("REST request to save StuCourse : {}", stuCourse);
        if (stuCourse.getId() != null) {
            throw new BadRequestAlertException("A new stuCourse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StuCourse result = stuCourseService.save(stuCourse);
        return ResponseEntity.created(new URI("/api/stu-courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stu-courses : Updates an existing stuCourse.
     *
     * @param stuCourse the stuCourse to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stuCourse,
     * or with status 400 (Bad Request) if the stuCourse is not valid,
     * or with status 500 (Internal Server Error) if the stuCourse couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stu-courses")
    @Timed
    public ResponseEntity<StuCourse> updateStuCourse(@RequestBody StuCourse stuCourse) throws URISyntaxException {
        log.debug("REST request to update StuCourse : {}", stuCourse);
        if (stuCourse.getId() == null) {
            return createStuCourse(stuCourse);
        }
        StuCourse result = stuCourseService.save(stuCourse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, stuCourse.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stu-courses : get all the stuCourses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of stuCourses in body
     */
    @GetMapping("/stu-courses")
    @Timed
    public ResponseEntity<List<StuCourse>> getAllStuCourses(StuCourseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StuCourses by criteria: {}", criteria);
        Page<StuCourse> page = stuCourseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/stu-courses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /stu-courses/:id : get the "id" stuCourse.
     *
     * @param id the id of the stuCourse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stuCourse, or with status 404 (Not Found)
     */
    @GetMapping("/stu-courses/{id}")
    @Timed
    public ResponseEntity<StuCourse> getStuCourse(@PathVariable Long id) {
        log.debug("REST request to get StuCourse : {}", id);
        StuCourse stuCourse = stuCourseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(stuCourse));
    }

    /**
     * DELETE  /stu-courses/:id : delete the "id" stuCourse.
     *
     * @param id the id of the stuCourse to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stu-courses/{id}")
    @Timed
    public ResponseEntity<Void> deleteStuCourse(@PathVariable Long id) {
        log.debug("REST request to delete StuCourse : {}", id);
        stuCourseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
