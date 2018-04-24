package com.tonyjoy.courseos.service.dto;

import java.io.Serializable;
import com.tonyjoy.courseos.domain.enumeration.TeachCourseType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the TeachCourse entity. This class is used in TeachCourseResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /teach-courses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TeachCourseCriteria implements Serializable {
    /**
     * Class for filtering TeachCourseType
     */
    public static class TeachCourseTypeFilter extends Filter<TeachCourseType> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter teachCourseCode;

    private StringFilter day;

    private StringFilter time;

    private TeachCourseTypeFilter teachCourseType;

    private StringFilter fee;

    private StringFilter desc;

    private StringFilter count;

    private StringFilter courseDue;

    private LongFilter teacherId;

    private LongFilter courseId;

    public TeachCourseCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTeachCourseCode() {
        return teachCourseCode;
    }

    public void setTeachCourseCode(StringFilter teachCourseCode) {
        this.teachCourseCode = teachCourseCode;
    }

    public StringFilter getDay() {
        return day;
    }

    public void setDay(StringFilter day) {
        this.day = day;
    }

    public StringFilter getTime() {
        return time;
    }

    public void setTime(StringFilter time) {
        this.time = time;
    }

    public TeachCourseTypeFilter getTeachCourseType() {
        return teachCourseType;
    }

    public void setTeachCourseType(TeachCourseTypeFilter teachCourseType) {
        this.teachCourseType = teachCourseType;
    }

    public StringFilter getFee() {
        return fee;
    }

    public void setFee(StringFilter fee) {
        this.fee = fee;
    }

    public StringFilter getDesc() {
        return desc;
    }

    public void setDesc(StringFilter desc) {
        this.desc = desc;
    }

    public StringFilter getCount() {
        return count;
    }

    public void setCount(StringFilter count) {
        this.count = count;
    }

    public StringFilter getCourseDue() {
        return courseDue;
    }

    public void setCourseDue(StringFilter courseDue) {
        this.courseDue = courseDue;
    }

    public LongFilter getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(LongFilter teacherId) {
        this.teacherId = teacherId;
    }

    public LongFilter getCourseId() {
        return courseId;
    }

    public void setCourseId(LongFilter courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "TeachCourseCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (teachCourseCode != null ? "teachCourseCode=" + teachCourseCode + ", " : "") +
                (day != null ? "day=" + day + ", " : "") +
                (time != null ? "time=" + time + ", " : "") +
                (teachCourseType != null ? "teachCourseType=" + teachCourseType + ", " : "") +
                (fee != null ? "fee=" + fee + ", " : "") +
                (desc != null ? "desc=" + desc + ", " : "") +
                (count != null ? "count=" + count + ", " : "") +
                (courseDue != null ? "courseDue=" + courseDue + ", " : "") +
                (teacherId != null ? "teacherId=" + teacherId + ", " : "") +
                (courseId != null ? "courseId=" + courseId + ", " : "") +
            "}";
    }

}
