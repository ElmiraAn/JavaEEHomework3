package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.dao.StudentDAO;
import com.elmira.aston.homework3.dao.SubjectDAO;
import com.elmira.aston.homework3.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SubjectServiceImplTest {
    private SubjectService subjectService;

    private SubjectDAO subjectDAO;

    @BeforeEach
    void setUp() {
        subjectDAO = mock(SubjectDAO.class);
        subjectService = new SubjectServiceImpl(subjectDAO/*, studentDAO*/);
    }

    @Test
    public void addSubject() {
        Subject subject = new Subject(1, "Literature");
        subjectService.addSubject(subject);
        verify(subjectDAO).addSubject(subject);
    }

    @Test
    public void getSubject() {
        Subject subject1 = new Subject(1, "Literature");
        when(subjectDAO.getSubjectById(1)).thenReturn(subject1);
        Subject subject2 = subjectService.getSubject(1);
        verify(subjectDAO).getSubjectById(1);
        assertEquals(subject1.getName(), subject2.getName());

    }

    @Test
    public void getAllSubjects() {
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
        List<Subject> subjects = Arrays.asList(
                new Subject(1, "Maths"),
                new Subject(2, "Art")
        );
        subjectService.deleteSubject(subjects.get(0).getId());
        verify(subjectDAO).deleteSubject(subjects.get(0).getId());
    }

    @Test
    public void updateSubject() {
        Subject subject = new Subject(1, "English");
        subject.setName("Spanish");
        subjectService.updateSubject(subject);
        verify(subjectDAO).updateSubject(subject);
    }
}