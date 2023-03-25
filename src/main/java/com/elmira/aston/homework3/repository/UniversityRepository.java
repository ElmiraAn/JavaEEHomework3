package com.elmira.aston.homework3.repository;

import com.elmira.aston.homework3.model.University;

import java.util.List;

public interface UniversityRepository {

    void addUniversity(University university);

    University getUniversity(int id);

    List<University> getAllUniversities();

    void deleteUniversity(int id);

    void updateUniversity(University university);

    University getUniversityWithStudents(int id);
}
