package com.tonyjoy.courseos.service.dto;

import java.io.Serializable;
import com.tonyjoy.courseos.domain.enumeration.IsOnJobStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Teacher entity. This class is used in TeacherResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /teachers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TeacherCriteria implements Serializable {
    /**
     * Class for filtering IsOnJobStatus
     */
    public static class IsOnJobStatusFilter extends Filter<IsOnJobStatus> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter teacherCode;

    private StringFilter name;

    private StringFilter subject;

    private StringFilter college;

    private StringFilter desc;

    private IsOnJobStatusFilter isOnJob;

    public TeacherCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(StringFilter teacherCode) {
        this.teacherCode = teacherCode;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getSubject() {
        return subject;
    }

    public void setSubject(StringFilter subject) {
        this.subject = subject;
    }

    public StringFilter getCollege() {
        return college;
    }

    public void setCollege(StringFilter college) {
        this.college = college;
    }

    public StringFilter getDesc() {
        return desc;
    }

    public void setDesc(StringFilter desc) {
        this.desc = desc;
    }

    public IsOnJobStatusFilter getIsOnJob() {
        return isOnJob;
    }

    public void setIsOnJob(IsOnJobStatusFilter isOnJob) {
        this.isOnJob = isOnJob;
    }

    @Override
    public String toString() {
        return "TeacherCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (teacherCode != null ? "teacherCode=" + teacherCode + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (subject != null ? "subject=" + subject + ", " : "") +
                (college != null ? "college=" + college + ", " : "") +
                (desc != null ? "desc=" + desc + ", " : "") +
                (isOnJob != null ? "isOnJob=" + isOnJob + ", " : "") +
            "}";
    }

}
