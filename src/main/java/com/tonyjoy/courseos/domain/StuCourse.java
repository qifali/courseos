package com.tonyjoy.courseos.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A StuCourse.
 */
@Entity
@Table(name = "stu_course")
public class StuCourse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_desc")
    private String desc;

    @Column(name = "phase")
    private String phase;

    @ManyToOne
    private Student student;

    @ManyToOne
    private TeachCourse teachCourse;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public StuCourse desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPhase() {
        return phase;
    }

    public StuCourse phase(String phase) {
        this.phase = phase;
        return this;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public Student getStudent() {
        return student;
    }

    public StuCourse student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public TeachCourse getTeachCourse() {
        return teachCourse;
    }

    public StuCourse teachCourse(TeachCourse teachCourse) {
        this.teachCourse = teachCourse;
        return this;
    }

    public void setTeachCourse(TeachCourse teachCourse) {
        this.teachCourse = teachCourse;
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
        StuCourse stuCourse = (StuCourse) o;
        if (stuCourse.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), stuCourse.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StuCourse{" +
            "id=" + getId() +
            ", desc='" + getDesc() + "'" +
            ", phase='" + getPhase() + "'" +
            "}";
    }
}
