package com.elmira.aston.homework3.repository;

import com.elmira.aston.homework3.model.Student;
import com.elmira.aston.homework3.model.Subject;

import java.util.List;

public interface StudentRepository {

    void addStudent(Student student, int universityId);
    Student getStudent(int id);
    void deleteStudent(int id);
    void updateStudent(Student student);
    List<Student> getAllStudents();
    List<Student> getAllStudentsWithUniversity();

    //Student getStudentWithSubjects(int id);

    //List<Subject> getStudentWithSubjects(int id);
    void addSubjectForStudent(Student student, Subject subject);

    List<Subject> getSubjectsForStudent(int studentId);

    void deleteCategoryForBook(Student student, Subject subject);


}
