package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.dao.UniversityDAO;
import com.elmira.aston.homework3.dao.UniversityDaoImpl;
import com.elmira.aston.homework3.model.University;

import java.util.List;

public class UniversityServiceImpl implements UniversityService {

    private UniversityDAO universityDAO;

    public UniversityServiceImpl() {
        this.universityDAO = new UniversityDaoImpl();
    }

    public UniversityServiceImpl(UniversityDAO universityDAO) {
        this.universityDAO = universityDAO;
    }

    @Override
    public University getUniversity(int id) {
        return universityDAO.getUniById(id);
    }

    @Override
    public List<University> getAllUniversities() {
        return universityDAO.getAllUniversities();
    }

    @Override
    public void addUniversity(University university) {
        universityDAO.addUniversity(university);
    }

    @Override
    public void deleteUniversity(int id) {
        universityDAO.deleteUniversity(id);
    }

    @Override
    public void updateUniversity(University university) {
        universityDAO.updateUniversity(university);
    }

}
