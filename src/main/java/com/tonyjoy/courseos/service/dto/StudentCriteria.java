package com.tonyjoy.courseos.service.dto;

import java.io.Serializable;
import com.tonyjoy.courseos.domain.enumeration.IsAfterSchoolStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Student entity. This class is used in StudentResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /students?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StudentCriteria implements Serializable {
    /**
     * Class for filtering IsAfterSchoolStatus
     */
    public static class IsAfterSchoolStatusFilter extends Filter<IsAfterSchoolStatus> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter stuCode;

    private StringFilter chnName;

    private StringFilter engName;

    private StringFilter grade;

    private StringFilter school;

    private StringFilter desc;

    private IsAfterSchoolStatusFilter isAfterSchool;

    public StudentCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStuCode() {
        return stuCode;
    }

    public void setStuCode(StringFilter stuCode) {
        this.stuCode = stuCode;
    }

    public StringFilter getChnName() {
        return chnName;
    }

    public void setChnName(StringFilter chnName) {
        this.chnName = chnName;
    }

    public StringFilter getEngName() {
        return engName;
    }

    public void setEngName(StringFilter engName) {
        this.engName = engName;
    }

    public StringFilter getGrade() {
        return grade;
    }

    public void setGrade(StringFilter grade) {
        this.grade = grade;
    }

    public StringFilter getSchool() {
        return school;
    }

    public void setSchool(StringFilter school) {
        this.school = school;
    }

    public StringFilter getDesc() {
        return desc;
    }

    public void setDesc(StringFilter desc) {
        this.desc = desc;
    }

    public IsAfterSchoolStatusFilter getIsAfterSchool() {
        return isAfterSchool;
    }

    public void setIsAfterSchool(IsAfterSchoolStatusFilter isAfterSchool) {
        this.isAfterSchool = isAfterSchool;
    }

    @Override
    public String toString() {
        return "StudentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (stuCode != null ? "stuCode=" + stuCode + ", " : "") +
                (chnName != null ? "chnName=" + chnName + ", " : "") +
                (engName != null ? "engName=" + engName + ", " : "") +
                (grade != null ? "grade=" + grade + ", " : "") +
                (school != null ? "school=" + school + ", " : "") +
                (desc != null ? "desc=" + desc + ", " : "") +
                (isAfterSchool != null ? "isAfterSchool=" + isAfterSchool + ", " : "") +
            "}";
    }

}
