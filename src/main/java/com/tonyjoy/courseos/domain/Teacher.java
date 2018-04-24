package com.tonyjoy.courseos.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.tonyjoy.courseos.domain.enumeration.IsOnJobStatus;

/**
 * A Teacher.
 */
@Entity
@Table(name = "teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teacher_code")
    private String teacherCode;

    @Column(name = "name")
    private String name;

    @Column(name = "subject")
    private String subject;

    @Column(name = "college")
    private String college;

    @Column(name = "jhi_desc")
    private String desc;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_on_job")
    private IsOnJobStatus isOnJob;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public Teacher teacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
        return this;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getName() {
        return name;
    }

    public Teacher name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public Teacher subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCollege() {
        return college;
    }

    public Teacher college(String college) {
        this.college = college;
        return this;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDesc() {
        return desc;
    }

    public Teacher desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public IsOnJobStatus getIsOnJob() {
        return isOnJob;
    }

    public Teacher isOnJob(IsOnJobStatus isOnJob) {
        this.isOnJob = isOnJob;
        return this;
    }

    public void setIsOnJob(IsOnJobStatus isOnJob) {
        this.isOnJob = isOnJob;
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
        Teacher teacher = (Teacher) o;
        if (teacher.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), teacher.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + getId() +
            ", teacherCode='" + getTeacherCode() + "'" +
            ", name='" + getName() + "'" +
            ", subject='" + getSubject() + "'" +
            ", college='" + getCollege() + "'" +
            ", desc='" + getDesc() + "'" +
            ", isOnJob='" + getIsOnJob() + "'" +
            "}";
    }
}
