package com.tonyjoy.courseos.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.tonyjoy.courseos.domain.enumeration.IsAfterSchoolStatus;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stu_code")
    private String stuCode;

    @Column(name = "chn_name")
    private String chnName;

    @Column(name = "eng_name")
    private String engName;

    @Column(name = "grade")
    private String grade;

    @Column(name = "school")
    private String school;

    @Column(name = "jhi_desc")
    private String desc;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_after_school")
    private IsAfterSchoolStatus isAfterSchool;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStuCode() {
        return stuCode;
    }

    public Student stuCode(String stuCode) {
        this.stuCode = stuCode;
        return this;
    }

    public void setStuCode(String stuCode) {
        this.stuCode = stuCode;
    }

    public String getChnName() {
        return chnName;
    }

    public Student chnName(String chnName) {
        this.chnName = chnName;
        return this;
    }

    public void setChnName(String chnName) {
        this.chnName = chnName;
    }

    public String getEngName() {
        return engName;
    }

    public Student engName(String engName) {
        this.engName = engName;
        return this;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getGrade() {
        return grade;
    }

    public Student grade(String grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSchool() {
        return school;
    }

    public Student school(String school) {
        this.school = school;
        return this;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDesc() {
        return desc;
    }

    public Student desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public IsAfterSchoolStatus getIsAfterSchool() {
        return isAfterSchool;
    }

    public Student isAfterSchool(IsAfterSchoolStatus isAfterSchool) {
        this.isAfterSchool = isAfterSchool;
        return this;
    }

    public void setIsAfterSchool(IsAfterSchoolStatus isAfterSchool) {
        this.isAfterSchool = isAfterSchool;
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
        Student student = (Student) o;
        if (student.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", stuCode='" + getStuCode() + "'" +
            ", chnName='" + getChnName() + "'" +
            ", engName='" + getEngName() + "'" +
            ", grade='" + getGrade() + "'" +
            ", school='" + getSchool() + "'" +
            ", desc='" + getDesc() + "'" +
            ", isAfterSchool='" + getIsAfterSchool() + "'" +
            "}";
    }
}
