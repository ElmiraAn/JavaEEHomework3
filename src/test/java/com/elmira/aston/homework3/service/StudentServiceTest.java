package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.model.Student;
import com.elmira.aston.homework3.model.Subject;
import com.elmira.aston.homework3.repository.StudentRepository;
import com.elmira.aston.homework3.repository.SubjectRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.elmira.aston.homework3.data.StudentDataTest.*;
import static com.elmira.aston.homework3.data.SubjectDataTest.*;
import static com.elmira.aston.homework3.data.UniversityDataTest.*;

public class StudentServiceTest {
    StudentRepository studentRepository;
    SubjectRepository subjectRepository;

    @BeforeEach
    void beforeEach() {
        studentRepository = new StudentService("h2");
        subjectRepository = new SubjectService("h2");
        String DB_URL = "jdbc:h2:./db/uni;INIT=runscript from 'src/test/resources/create_tables_h2.sql'";
        //String DB_URL = "jdbc:h2:./db/uni;INIT=runscript from 'src/test/resources/db_h2.sql'";
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
        Student student = studentRepository.getStudent(1);
        Assert.assertEquals(ADAM.getName(), student.getName());
    }

    @Test
    public void addStudent() {
        beforeEach();
        Student student = new Student("Amy");
        studentRepository.addStudent(student, Cambridge.getId());
        Assert.assertEquals("Amy", studentRepository.getStudent(9).getName());
    }

    @Test
    public void deleteStudent() {
        beforeEach();
        studentRepository.deleteStudent(8);
        Assert.assertEquals(students.size() - 1, studentRepository.getAllStudents().size());
    }

    @Test
    public void updateStudent() {
        beforeEach();
        Student student = new Student(1, "Alex", Harvard);
        studentRepository.updateStudent(student);
        Assert.assertEquals("Alex", studentRepository.getStudent(1).getName());
        Assert.assertEquals(Harvard.getName(), studentRepository.getStudent(1).getUniversity().getName());
    }

    @Test
    public void getAllStudents() {
        beforeEach();
        List<Student> allStudents = studentRepository.getAllStudents();
        Assert.assertEquals(students.size(), allStudents.size());
    }

    @Test
    public void getAllStudentsWithUniversity() {
        beforeEach();
        List<Student> allStudentsWithUniversity = studentRepository.getAllStudentsWithUniversity();
        Student student = allStudentsWithUniversity.get(1);
        Assert.assertEquals(Harvard.getName(), student.getUniversity().getName());
        Assert.assertEquals(students.size(), allStudentsWithUniversity.size());
    }

    @Test
    public void addSubjectForStudent() {
        beforeEach();
        Student student = studentRepository.getStudent(1);
        Subject subject = subjectRepository.getSubject(2);
        studentRepository.addSubjectForStudent(student, subject);
        Assert.assertEquals(3, studentRepository.getSubjectsForStudent(1).size());
    }

    @Test
    public void getSubjectsForStudent() {
        beforeEach();
        List<Subject> subjects = studentRepository.getSubjectsForStudent(1);
        Assert.assertEquals(2, subjects.size());
        Assert.assertEquals(MATHS, subjects.get(0));
        Assert.assertEquals(PHYSICS, subjects.get(1));
    }

    @Test
    public void deleteCategoryForBook() {
        beforeEach();
        List<Subject> subjects = studentRepository.getSubjectsForStudent(1);
        Subject subject = subjectRepository.getSubject(1);
        Student student = studentRepository.getStudent(1);
        studentRepository.deleteCategoryForBook(student, subject);
        Assert.assertEquals(1, studentRepository.getSubjectsForStudent(1).size());

    }

    /*@Test
    public void getStudentWithSubjects() {
        beforeEach();
        Student student = studentRepository.getStudentWithSubjects(1);

        Assert.assertEquals(subjectsForStudent_1.size(), student.getSubjects().size());
        Assert.assertEquals(MATHS.getName(), student.getSubjects().get(0).getName());

    }*/

    /*@Test
    public void getStudentWithSubjects() {
        beforeEach();
        List<Subject> subjects = studentRepository.getStudentWithSubjects(1);

        Assert.assertEquals(subjectsForStudent_1.size(), subjects.size());
        Assert.assertEquals(MATHS.getName(), subjects.get(0).getName());

    }*/
}