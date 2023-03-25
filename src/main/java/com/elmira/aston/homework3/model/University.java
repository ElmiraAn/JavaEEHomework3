package com.elmira.aston.homework3.model;

import java.util.List;

public class University {
    private int id;
    private String name;
    private List<Student> students;

    public University() {
    }

    public University(String name) {
        this.name = name;
    }

    public University(String name, List<Student> students) {
        this.name = name;
        this.students = students;
    }

    public University(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public University(int id, String name, List<Student> students) {
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
}
