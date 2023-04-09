package com.elmira.aston.homework3.dao;

import com.elmira.aston.homework3.model.University;

import java.util.List;

public interface UniversityDAO {
    University getUniById(int id);
    List<University> getAllUniversities();
    void addUniversity(University university);
    void updateUniversity(University university);
    void deleteUniversity(int id);

}
