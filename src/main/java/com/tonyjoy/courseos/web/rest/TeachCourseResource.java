package com.tonyjoy.courseos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tonyjoy.courseos.domain.TeachCourse;
import com.tonyjoy.courseos.service.TeachCourseService;
import com.tonyjoy.courseos.web.rest.errors.BadRequestAlertException;
import com.tonyjoy.courseos.web.rest.util.HeaderUtil;
import com.tonyjoy.courseos.web.rest.util.PaginationUtil;
import com.tonyjoy.courseos.service.dto.TeachCourseCriteria;
import com.tonyjoy.courseos.service.TeachCourseQueryService;
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
 * REST controller for managing TeachCourse.
 */
@RestController
@RequestMapping("/api")
public class TeachCourseResource {

    private final Logger log = LoggerFactory.getLogger(TeachCourseResource.class);

    private static final String ENTITY_NAME = "teachCourse";

    private final TeachCourseService teachCourseService;

    private final TeachCourseQueryService teachCourseQueryService;

    public TeachCourseResource(TeachCourseService teachCourseService, TeachCourseQueryService teachCourseQueryService) {
        this.teachCourseService = teachCourseService;
        this.teachCourseQueryService = teachCourseQueryService;
    }

    /**
     * POST  /teach-courses : Create a new teachCourse.
     *
     * @param teachCourse the teachCourse to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teachCourse, or with status 400 (Bad Request) if the teachCourse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/teach-courses")
    @Timed
    public ResponseEntity<TeachCourse> createTeachCourse(@RequestBody TeachCourse teachCourse) throws URISyntaxException {
        log.debug("REST request to save TeachCourse : {}", teachCourse);
        if (teachCourse.getId() != null) {
            throw new BadRequestAlertException("A new teachCourse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeachCourse result = teachCourseService.save(teachCourse);
        return ResponseEntity.created(new URI("/api/teach-courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /teach-courses : Updates an existing teachCourse.
     *
     * @param teachCourse the teachCourse to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teachCourse,
     * or with status 400 (Bad Request) if the teachCourse is not valid,
     * or with status 500 (Internal Server Error) if the teachCourse couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/teach-courses")
    @Timed
    public ResponseEntity<TeachCourse> updateTeachCourse(@RequestBody TeachCourse teachCourse) throws URISyntaxException {
        log.debug("REST request to update TeachCourse : {}", teachCourse);
        if (teachCourse.getId() == null) {
            return createTeachCourse(teachCourse);
        }
        TeachCourse result = teachCourseService.save(teachCourse);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, teachCourse.getId().toString()))
            .body(result);
    }

    /**
     * GET  /teach-courses : get all the teachCourses.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of teachCourses in body
     */
    @GetMapping("/teach-courses")
    @Timed
    public ResponseEntity<List<TeachCourse>> getAllTeachCourses(TeachCourseCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TeachCourses by criteria: {}", criteria);
        Page<TeachCourse> page = teachCourseQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teach-courses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /teach-courses/:id : get the "id" teachCourse.
     *
     * @param id the id of the teachCourse to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teachCourse, or with status 404 (Not Found)
     */
    @GetMapping("/teach-courses/{id}")
    @Timed
    public ResponseEntity<TeachCourse> getTeachCourse(@PathVariable Long id) {
        log.debug("REST request to get TeachCourse : {}", id);
        TeachCourse teachCourse = teachCourseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(teachCourse));
    }

    /**
     * DELETE  /teach-courses/:id : delete the "id" teachCourse.
     *
     * @param id the id of the teachCourse to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/teach-courses/{id}")
    @Timed
    public ResponseEntity<Void> deleteTeachCourse(@PathVariable Long id) {
        log.debug("REST request to delete TeachCourse : {}", id);
        teachCourseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
