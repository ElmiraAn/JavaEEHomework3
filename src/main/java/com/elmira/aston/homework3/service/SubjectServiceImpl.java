package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.dao.SubjectDAO;
import com.elmira.aston.homework3.dao.SubjectDaoImpl;
import com.elmira.aston.homework3.model.Subject;

import java.util.List;

public class SubjectServiceImpl implements SubjectService {

    SubjectDAO subjectDAO;

    public SubjectServiceImpl() {
        this.subjectDAO = new SubjectDaoImpl();
    }

    public SubjectServiceImpl(SubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    @Override
    public void addSubject(Subject subject) {
        subjectDAO.addSubject(subject);
    }

    @Override
    public Subject getSubject(int id) {
        return subjectDAO.getSubjectById(id);
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectDAO.getAllSubjects();
    }

    @Override
    public void deleteSubject(int id) {
        subjectDAO.deleteSubject(id);
    }

    @Override
    public void updateSubject(Subject subject) {
        subjectDAO.updateSubject(subject);
    }
}
