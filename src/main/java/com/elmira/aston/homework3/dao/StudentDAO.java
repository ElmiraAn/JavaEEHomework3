package com.elmira.aston.homework3.dao;

import com.elmira.aston.homework3.model.Student;

import java.util.List;

public interface StudentDAO {
    void addStudent(Student student, int universityId);

    Student getStudentById(int id);

    void deleteStudent(int id);

    void updateStudent(Student student);

    List<Student> getAllStudents();

}
