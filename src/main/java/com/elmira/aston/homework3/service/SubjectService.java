package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.model.Subject;

import java.util.List;

public interface SubjectService {

    void addSubject(Subject subject);

    Subject getSubject(int id);

    List<Subject> getAllSubjects();

    void deleteSubject(int id);

    void updateSubject(Subject subject);

}
