package com.elmira.aston.homework3.repository;

import com.elmira.aston.homework3.model.Subject;

import java.util.List;

public interface SubjectRepository {

    void addSubject(Subject Subject);

    Subject getSubject(int id);

    List<Subject> getAllSubjects();

    void deleteSubject(int id);

    void updateSubject(Subject subject);

    Subject getSubjectWithStudent(int id);


}
