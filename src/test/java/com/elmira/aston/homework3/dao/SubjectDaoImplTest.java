package com.elmira.aston.homework3.dao;

import com.elmira.aston.homework3.model.Subject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class SubjectDaoImplTest extends DaoTest {

    @Test
    public void addSubject() {
        Subject newSubject = new Subject();
        newSubject.setName("English");
        subjectDAO.addSubject(newSubject);
        Assertions.assertEquals(newSubject.getName(), subjectDAO.getSubjectById(9).getName());
    }

    @Test
    public void getSubjectById() {
        Subject subject1 = new Subject(1, "Maths");
        Subject subject2 = subjectDAO.getSubjectById(1);
        Assertions.assertEquals(subject1.getName(), subject2.getName());
    }

    @Test
    public void getAllSubjects() {
        List<Subject> subjects = subjectDAO.getAllSubjects();
        Assertions.assertEquals(8, subjects.size());
    }

    @Test
    public void deleteSubject() {
        Assertions.assertEquals(8, subjectDAO.getAllSubjects().size());
        subjectDAO.deleteSubject(2);
        Assertions.assertEquals(7, subjectDAO.getAllSubjects().size());
    }

    @Test
    public void updateSubject() {
        Subject update_subject = subjectDAO.getSubjectById(1);
        update_subject.setName("Higher Maths");
        subjectDAO.updateSubject(update_subject);
        Assertions.assertEquals("Higher Maths", subjectDAO.getSubjectById(1).getName());
    }
}