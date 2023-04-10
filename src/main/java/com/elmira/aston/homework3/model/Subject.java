package com.elmira.aston.homework3.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Subject {

    private int id;
    private String name;
    private List<Student> students;

    public Subject() {
    }

    public Subject(String name) {
        this.name = name;
    }

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Subject(String name, List<Student> students) {
        this.name = name;
        this.students = students;
    }

    public Subject(int id, String name, List<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id == subject.id && Objects.equals(name, subject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
