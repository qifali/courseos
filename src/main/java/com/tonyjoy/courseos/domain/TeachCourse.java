package com.tonyjoy.courseos.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.tonyjoy.courseos.domain.enumeration.TeachCourseType;

/**
 * A TeachCourse.
 */
@Entity
@Table(name = "teach_course")
public class TeachCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teach_course_code")
    private String teachCourseCode;

    @Column(name = "day")
    private String day;

    @Column(name = "jhi_time")
    private String time;

    @Enumerated(EnumType.STRING)
    @Column(name = "teach_course_type")
    private TeachCourseType teachCourseType;

    @Column(name = "fee")
    private String fee;

    @Column(name = "jhi_desc")
    private String desc;

    @Column(name = "count")
    private String count;

    @Column(name = "course_due")
    private String courseDue;

    @ManyToOne
    private Teacher teacher;

    @ManyToOne
    private Course course;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeachCourseCode() {
        return teachCourseCode;
    }

    public TeachCourse teachCourseCode(String teachCourseCode) {
        this.teachCourseCode = teachCourseCode;
        return this;
    }

    public void setTeachCourseCode(String teachCourseCode) {
        this.teachCourseCode = teachCourseCode;
    }

    public String getDay() {
        return day;
    }

    public TeachCourse day(String day) {
        this.day = day;
        return this;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public TeachCourse time(String time) {
        this.time = time;
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public TeachCourseType getTeachCourseType() {
        return teachCourseType;
    }

    public TeachCourse teachCourseType(TeachCourseType teachCourseType) {
        this.teachCourseType = teachCourseType;
        return this;
    }

    public void setTeachCourseType(TeachCourseType teachCourseType) {
        this.teachCourseType = teachCourseType;
    }

    public String getFee() {
        return fee;
    }

    public TeachCourse fee(String fee) {
        this.fee = fee;
        return this;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getDesc() {
        return desc;
    }

    public TeachCourse desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCount() {
        return count;
    }

    public TeachCourse count(String count) {
        this.count = count;
        return this;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCourseDue() {
        return courseDue;
    }

    public TeachCourse courseDue(String courseDue) {
        this.courseDue = courseDue;
        return this;
    }

    public void setCourseDue(String courseDue) {
        this.courseDue = courseDue;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public TeachCourse teacher(Teacher teacher) {
        this.teacher = teacher;
        return this;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public TeachCourse course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TeachCourse teachCourse = (TeachCourse) o;
        if (teachCourse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teachCourse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TeachCourse{" +
            "id=" + getId() +
            ", teachCourseCode='" + getTeachCourseCode() + "'" +
            ", day='" + getDay() + "'" +
            ", time='" + getTime() + "'" +
            ", teachCourseType='" + getTeachCourseType() + "'" +
            ", fee='" + getFee() + "'" +
            ", desc='" + getDesc() + "'" +
            ", count='" + getCount() + "'" +
            ", courseDue='" + getCourseDue() + "'" +
            "}";
    }
}
