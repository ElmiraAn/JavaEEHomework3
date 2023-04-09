package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.dao.UniversityDAO;
import com.elmira.aston.homework3.model.*;
import com.elmira.aston.homework3.repository.UniversityService;
import org.junit.*;
import org.junit.jupiter.api.BeforeEach;

import java.sql.*;
import java.util.*;

import static com.elmira.aston.homework3.data.UniversityDataTest.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class UniversityServiceImplTest {
    private UniversityService service;
    private UniversityDAO universityDAO;

    @BeforeEach
    void setUp() {
        /*service = new UniversityServiceImpl("h2");
        String DB_URL = "jdbc:h2:./db/uni;INIT=runscript from 'src/test/resources/create_tables_h2.sql'";
        String DB_USER = "sa";
        String DB_PASSWORD = "";
        String DRIVER = "org.h2.Driver";
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL,
                    DB_USER, DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/
        universityDAO = mock(UniversityDAO.class);
        service = new UniversityServiceImpl(universityDAO);

    }

    @Test
    public void addUniversity() {
        //setUp();
        University university = new University("Yale");
        service.addUniversity(university);
        verify(universityDAO).addUniversity(university);
        //Assert.assertEquals(Yale.getName(), service.getUniversity(5).getName());
    }

    @Test
    public void getUniversity() {
        //setUp();
        University university = service.getUniversity(Harvard.getId());
        Assert.assertEquals(Harvard.getName(), university.getName());
    }

    @Test
    public void getAllUniversities() {
        setUp();
        List<University> allUniversities = this.service.getAllUniversities();
        Assert.assertEquals(UNIVERSITIES.size(), allUniversities.size());
    }

    @Test
    public void deleteUniversity() {
        setUp();
        service.deleteUniversity(Cambridge.getId());
        Assert.assertEquals(UNIVERSITIES.size() - 1, service.getAllUniversities().size());
    }

    @Test
    public void updateUniversity() {
        setUp();
        University university = new University(Oxford.getId(), "Oxford University");
        service.updateUniversity(university);
        Assert.assertEquals("Oxford University", service.getUniversity(Oxford.getId()).getName());
    }

    @Test
    public void getUniversityWithStudents() {
        setUp();
        University university = service.getUniversityWithStudents(3);
        List<Student> students = university.getStudents();
        for (Student student : students) {
            System.out.println(student.getName());
        }
        Assert.assertEquals(2, service.getUniversityWithStudents(3).getStudents().size());
    }
}