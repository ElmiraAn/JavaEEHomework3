package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.model.*;
import com.elmira.aston.homework3.repository.StudentService;
import com.elmira.aston.homework3.repository.SubjectService;

import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static com.elmira.aston.homework3.data.StudentDataTest.*;
import static com.elmira.aston.homework3.data.SubjectDataTest.*;
import static com.elmira.aston.homework3.data.UniversityDataTest.*;

public class StudentServiceTest {
    StudentService studentService;
    SubjectService subjectService;

    @BeforeEach
    void beforeEach() {
        studentService = new StudentServiceImpl("h2");
        subjectService = new SubjectServiceImpl("h2");
        String DB_URL = "jdbc:h2:./db/uni;INIT=runscript from 'src/test/resources/create_tables_h2.sql'";
        String DB_USER = "sa";
        String DB_PASSWORD = "";
        String DRIVER = "org.h2.Driver";
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL,
                    DB_USER, DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getStudent() {
        beforeEach();
        Student student = studentService.getStudent(1);
        Assert.assertEquals(ADAM.getName(), student.getName());
    }

    @Test
    public void addStudent() {
        beforeEach();
        Student student = new Student("Amy");
        studentService.addStudent(student, Cambridge.getId());
        Assert.assertEquals("Amy", studentService.getStudent(9).getName());
    }

    @Test
    public void deleteStudent() {
        beforeEach();
        studentService.deleteStudent(8);
        Assert.assertEquals(students.size() - 1, studentService.getAllStudents().size());
    }

    @Test
    public void updateStudent() {
        beforeEach();
        Student student = new Student(1, "Alex", Harvard);
        studentService.updateStudent(student);
        Assert.assertEquals("Alex", studentService.getStudent(1).getName());
        Assert.assertEquals(Harvard.getName(), studentService.getStudent(1).getUniversity().getName());
    }

    @Test
    public void getAllStudents() {
        beforeEach();
        List<Student> allStudents = studentService.getAllStudents();
        Assert.assertEquals(students.size(), allStudents.size());
    }

    @Test
    public void getAllStudentsWithUniversity() {
        beforeEach();
        List<Student> allStudentsWithUniversity = studentService.getAllStudentsWithUniversity();
        Student student = allStudentsWithUniversity.get(1);
        Assert.assertEquals(Harvard.getName(), student.getUniversity().getName());
        Assert.assertEquals(students.size(), allStudentsWithUniversity.size());
    }

    @Test
    public void addSubjectForStudent() {
        beforeEach();
        Student student = studentService.getStudent(1);
        Subject subject = subjectService.getSubject(2);
        studentService.addSubjectForStudent(student, subject);
        Assert.assertEquals(3, studentService.getSubjectsForStudent(1).size());
    }

    @Test
    public void getSubjectsForStudent() {
        beforeEach();
        List<Subject> subjects = studentService.getSubjectsForStudent(1);
        Assert.assertEquals(2, subjects.size());
        Assert.assertEquals(MATHS, subjects.get(0));
        Assert.assertEquals(PHYSICS, subjects.get(1));
    }

    @Test
    public void deleteCategoryForBook() {
        beforeEach();
        List<Subject> subjects = studentService.getSubjectsForStudent(1);
        Subject subject = subjectService.getSubject(1);
        Student student = studentService.getStudent(1);
        studentService.deleteSubjectForStudent(student, subject);
        Assert.assertEquals(1, studentService.getSubjectsForStudent(1).size());
    }
}