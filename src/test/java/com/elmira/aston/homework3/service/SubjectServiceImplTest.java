package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.model.*;
import com.elmira.aston.homework3.repository.SubjectService;
import org.junit.*;
import org.junit.jupiter.api.BeforeEach;

import java.sql.*;
import java.util.*;
import static com.elmira.aston.homework3.data.StudentDataTest.*;
import static com.elmira.aston.homework3.data.SubjectDataTest.*;

public class SubjectServiceImplTest {
    private SubjectService subjectService;

    @BeforeEach
    void beforeEach() {
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
    public void addSubject() {
        beforeEach();
        Subject subject = new Subject("Literature");
        subjectService.addSubject(subject);
        Assert.assertEquals("Literature", subjectService.getSubject(9).getName());
    }

    @Test
    public void getSubject() {
        beforeEach();
        Subject subject = subjectService.getSubject(1);
        Assert.assertEquals(MATHS.getName(), subject.getName());
    }

    @Test
    public void getAllSubjects() {
        beforeEach();
        List<Subject> subjects = subjectService.getAllSubjects();
        Assert.assertEquals(expected_subjects.size(), subjects.size());
    }

    @Test
    public void deleteSubject() {
        beforeEach();
        subjectService.deleteSubject(2);
        Assert.assertEquals(expected_subjects.size() - 1, subjectService.getAllSubjects().size());
    }

    @Test
    public void updateSubject() {
        beforeEach();
        Subject subject = new Subject(2, "WorldHistory");
        subjectService.updateSubject(subject);
        Assert.assertEquals(subject.getName(), subjectService.getSubject(2).getName());
    }

    @Test
    public void getSubjectWithStudent() {
        beforeEach();
        Subject subject = subjectService.getSubjectWithStudent(1);
        Assert.assertEquals(subjectsFotStudent.size(), subject.getStudents().size());
        Assert.assertEquals(ADAM.getName(), subject.getStudents().get(0).getName());
    }
}