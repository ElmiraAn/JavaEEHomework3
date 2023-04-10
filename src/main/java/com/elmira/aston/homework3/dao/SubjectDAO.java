package com.elmira.aston.homework3.dao;

import com.elmira.aston.homework3.model.Subject;

import java.util.List;

public interface SubjectDAO {

    void addSubject(Subject subject);

    Subject getSubjectById(int id);

    List<Subject> getAllSubjects();

    void deleteSubject(int id);

    void updateSubject(Subject subject);
}
