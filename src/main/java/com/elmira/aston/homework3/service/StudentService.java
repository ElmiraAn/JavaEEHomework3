package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.model.Student;

import java.util.List;

public interface StudentService {

    void addStudent(Student student, int universityId);

    Student getStudent(int id);

    void deleteStudent(int id);

    void updateStudent(Student student);

    List<Student> getAllStudents();

}
