package com.elmira.aston.homework3.dao;

import com.elmira.aston.homework3.model.University;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class UniversityDaoImplTest extends DaoTest {

    @Test
    public void getUniById() {
        University university1 = new University(1, "Oxford");
        University university2 = universityDAO.getUniById(1);
        Assertions.assertEquals(university1.getName(), university2.getName());
    }

    @Test
    public void getAllUniversities() {
        List<University> universities = universityDAO.getAllUniversities();
        Assertions.assertEquals(4, universities.size());
    }

    @Test
    public void addUniversity() {
        University newUni = new University();
        newUni.setName("Yale");
        universityDAO.addUniversity(newUni);
        Assertions.assertEquals(5, universityDAO.getAllUniversities().size());
        Assertions.assertEquals("Yale", universityDAO.getUniById(5).getName());
    }

    @Test
    public void updateUniversity() {
        University updateUni = universityDAO.getUniById(1);
        updateUni.setName("Oxford University");
        universityDAO.updateUniversity(updateUni);
        Assertions.assertEquals("Oxford University", universityDAO.getUniById(1).getName());
    }

    @Test
    public void deleteUniversity() {
        universityDAO.deleteUniversity(1);
        Assertions.assertEquals(3, universityDAO.getAllUniversities().size());
    }
}