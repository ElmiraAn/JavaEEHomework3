package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.model.Student;
import com.elmira.aston.homework3.model.Subject;
import com.elmira.aston.homework3.repository.SubjectRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static com.elmira.aston.homework3.data.StudentDataTest.*;
import static com.elmira.aston.homework3.data.SubjectDataTest.*;

public class SubjectServiceTest {

    private SubjectRepository subjectRepository;

    @BeforeEach
    void beforeEach() {
        subjectRepository = new SubjectService("h2");
        String DB_URL = "jdbc:h2:./db/uni;INIT=runscript from 'src/main/resources/create_tables_h2.sql'";
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
        List<Student> studentsLiterature = new ArrayList<>();
        studentsLiterature.add(SANDY);
        studentsLiterature.add(MONIKA);
        Subject subject = new Subject("Literature", studentsLiterature);
        subjectRepository.addSubject(subject);
        Assert.assertEquals("Literature", subjectRepository.getSubject(9).getName());

    }

    @Test
    public void getSubject() {
        beforeEach();
        Subject subject = subjectRepository.getSubject(1);
        Assert.assertEquals(MATHS.getName(), subject.getName());

    }

    @Test
    public void getAllSubjects() {
        beforeEach();
        List<Subject> subjects = subjectRepository.getAllSubjects();
        Assert.assertEquals(expected_subjects.size(), subjects.size());
    }

    @Test
    public void deleteSubject() {
        beforeEach();
        subjectRepository.deleteSubject(2);
        Assert.assertEquals(expected_subjects.size() - 1, subjectRepository.getAllSubjects().size());
    }

    @Test
    public void updateSubject() {
        beforeEach();
        Subject subject = new Subject(2, "WorldHistory");
        subjectRepository.updateSubject(subject);
        Assert.assertEquals(subject.getName(), subjectRepository.getSubject(2).getName());
    }

    @Test
    public void getSubjectWithStudent() {
        beforeEach();
        Subject subject = subjectRepository.getSubjectWithStudent(8);
        Assert.assertEquals(studentsForSubjects.size(), subject.getStudents().size());
        Assert.assertEquals(ADAM.getName(), subject.getStudents().get(1).getName());

    }
}