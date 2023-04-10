package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.model.University;

import java.util.List;

public interface UniversityService {

    University getUniversity(int id);

    List<University> getAllUniversities();

    void addUniversity(University university);

    void deleteUniversity(int id);

    void updateUniversity(University university);

}
