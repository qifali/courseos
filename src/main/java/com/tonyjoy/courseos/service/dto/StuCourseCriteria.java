package com.tonyjoy.courseos.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the StuCourse entity. This class is used in StuCourseResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /stu-courses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StuCourseCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter desc;

    private StringFilter phase;

    private LongFilter studentId;

    private LongFilter teachCourseId;

    public StuCourseCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDesc() {
        return desc;
    }

    public void setDesc(StringFilter desc) {
        this.desc = desc;
    }

    public StringFilter getPhase() {
        return phase;
    }

    public void setPhase(StringFilter phase) {
        this.phase = phase;
    }

    public LongFilter getStudentId() {
        return studentId;
    }

    public void setStudentId(LongFilter studentId) {
        this.studentId = studentId;
    }

    public LongFilter getTeachCourseId() {
        return teachCourseId;
    }

    public void setTeachCourseId(LongFilter teachCourseId) {
        this.teachCourseId = teachCourseId;
    }

    @Override
    public String toString() {
        return "StuCourseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (desc != null ? "desc=" + desc + ", " : "") +
                (phase != null ? "phase=" + phase + ", " : "") +
                (studentId != null ? "studentId=" + studentId + ", " : "") +
                (teachCourseId != null ? "teachCourseId=" + teachCourseId + ", " : "") +
            "}";
    }

}
