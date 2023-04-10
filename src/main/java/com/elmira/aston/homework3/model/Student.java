package com.elmira.aston.homework3.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Student {
    private int id;
    private String name;
    private University university;
    private List<Subject> subjects;


    public Student() {
    }

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, University university) {
        this.name = name;
        this.university = university;
    }

    public Student(int id, String name, List<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.subjects = subjects;
    }

    public Student(String name, List<Subject> subjects) {
        this.name = name;
        this.subjects = subjects;
    }

    public Student(String name, University university, List<Subject> subjects) {
        this.name = name;
        this.university = university;
        this.subjects = subjects;
    }

    public Student(int id, String name, University university) {
        this.id = id;
        this.name = name;
        this.university = university;
    }

    public Student(int id, String name, University university, List<Subject> subjects) {
        this.id = id;
        this.name = name;
        this.university = university;
        this.subjects = subjects;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
