package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.dao.UniversityDAO;
import com.elmira.aston.homework3.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UniversityServiceImplTest {
    private UniversityService service;
    private UniversityDAO universityDAO;

    @BeforeEach
    void setUp() {
        universityDAO = mock(UniversityDAO.class);
        service = new UniversityServiceImpl(universityDAO);
    }

    @Test
    public void addUniversity() {
        University university = new University("Yale");
        service.addUniversity(university);
        verify(universityDAO).addUniversity(university);
    }

    @Test
    public void getUniversity() {
        University university = new University(1, "Yale");
        when(universityDAO.getUniById(1)).thenReturn(university);
        University university2 = service.getUniversity(1);
        verify(universityDAO).getUniById(1);
        assertEquals(university.getName(), university2.getName());
    }

    @Test
    public void getAllUniversities() {
        List<University> universities1 = Arrays.asList(
                new University(1, "Oxford"),
                new University(2, "Yale")
        );
        when(universityDAO.getAllUniversities()).thenReturn(universities1);
        List<University> universities2 = service.getAllUniversities();
        verify(universityDAO).getAllUniversities();
        assertEquals(universities1.size(), universities2.size());
    }

    @Test
    public void deleteUniversity() {
        List<University> universities1 = Arrays.asList(
                new University(1, "Oxford"),
                new University(2, "Yale")
        );
        service.deleteUniversity(universities1.get(1).getId());
        verify(universityDAO).deleteUniversity(universities1.get(1).getId());
    }

    @Test
    public void updateUniversity() {
        University university = new University(1, "Yale");
        university.setName("Update Yale");
        service.updateUniversity(university);
        verify(universityDAO).updateUniversity(university);
    }
       /*

    @Test
    public void getUniversityWithStudents() {
        setUp();
        University university = service.getUniversityWithStudents(3);
        List<Student> students = university.getStudents();
        for (Student student : students) {
            System.out.println(student.getName());
        }
        Assert.assertEquals(2, service.getUniversityWithStudents(3).getStudents().size());
    }*/
}