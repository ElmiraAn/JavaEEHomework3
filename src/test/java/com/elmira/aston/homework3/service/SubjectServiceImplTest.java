package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.dao.StudentDAO;
import com.elmira.aston.homework3.dao.SubjectDAO;
import com.elmira.aston.homework3.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import static com.elmira.aston.homework3.data.StudentDataTest.*;
import static com.elmira.aston.homework3.data.SubjectDataTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SubjectServiceImplTest {
    private SubjectService subjectService;

    private SubjectDAO subjectDAO;
    private StudentDAO studentDAO;

    @BeforeEach
    void setUp() {
        /*subjectService = new SubjectServiceImpl("h2");
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
        }*/
        subjectDAO = mock(SubjectDAO.class);
        //studentDAO = mock(StudentDAO.class);
        subjectService = new SubjectServiceImpl(subjectDAO/*, studentDAO*/);


    }

    @Test
    public void addSubject() {
        Subject subject = new Subject(1, "Literature");
        subjectService.addSubject(subject);
        //Assert.assertEquals("Literature", subjectService.getSubject(9).getName());
        verify(subjectDAO).addSubject(subject);

    }

    @Test
    public void getSubject() {
        /*Subject subject = subjectService.getSubject(1);
        Assert.assertEquals(MATHS.getName(), subject.getName());*/
        Subject subject1 = new Subject(1, "Literature");
        when(subjectDAO.getSubjectById(1)).thenReturn(subject1);
        Subject subject2 = subjectService.getSubject(1);
        verify(subjectDAO).getSubjectById(1);
        assertEquals(subject1.getName(), subject2.getName());

    }

    @Test
    public void getAllSubjects() {
        /*List<Subject> subjects = subjectService.getAllSubjects();
        Assert.assertEquals(expected_subjects.size(), subjects.size());*/
        List<Subject> subjects1 = Arrays.asList(
                new Subject(1, "Maths"),
                new Subject(2, "Art")
        );
        when(subjectDAO.getAllSubjects()).thenReturn(subjects1);
        List<Subject> subjects2 = subjectService.getAllSubjects();
        verify(subjectDAO).getAllSubjects();
        assertEquals(subjects1.size(), subjects2.size());

    }

    @Test
    public void deleteSubject() {
        /*subjectService.deleteSubject(2);
        Assert.assertEquals(expected_subjects.size() - 1, subjectService.getAllSubjects().size());
    */
        List<Subject> subjects = Arrays.asList(
                new Subject(1, "Maths"),
                new Subject(2, "Art")
        );
        subjectService.deleteSubject(subjects.get(0).getId());
        verify(subjectDAO).deleteSubject(subjects.get(0).getId());
    }

    @Test
    public void updateSubject() {
        /*Subject subject = new Subject(2, "WorldHistory");
        subjectService.updateSubject(subject);
        Assert.assertEquals(subject.getName(), subjectService.getSubject(2).getName());
    */
        Subject subject = new Subject(1, "English");
        subject.setName("Spanish");
        subjectService.updateSubject(subject);
        verify(subjectDAO).updateSubject(subject);
    }

    /*@Test
    public void getSubjectWithStudent() {
        setUp();
        Subject subject = subjectService.getSubjectWithStudent(1);
        Assert.assertEquals(subjectsFotStudent.size(), subject.getStudents().size());
        Assert.assertEquals(ADAM.getName(), subject.getStudents().get(0).getName());
    }*/
}