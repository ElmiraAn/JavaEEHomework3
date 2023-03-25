package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.model.Student;
import com.elmira.aston.homework3.model.University;
import com.elmira.aston.homework3.repository.UniversityRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.elmira.aston.homework3.data.UniversityDataTest.*;

public class UniversityServiceTest {
    private UniversityRepository repository;

    @BeforeEach
    void beforeEach(){
        repository = new UniversityService("h2");
        String DB_URL = "jdbc:h2:./db/uni;INIT=runscript from 'src/main/resources/create_tables_h2.sql'";
        String DB_USER = "sa";
        String DB_PASSWORD = "";
        String DRIVER="org.h2.Driver";
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DB_URL,
                    DB_USER, DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addUniversity() {
        beforeEach();
        University university = new University("Yale");
        repository.addUniversity(university);
        Assert.assertEquals(Yale.getName(), repository.getUniversity(7).getName());
    }

    @Test
    public void getUniversity() {
        beforeEach();
        University university = repository.getUniversity(Harvard.getId());

        //Assert.assertEquals(PUSHKIN.getName(), test.getName());
    }

    @Test
    public void getAllUniversities() {
        beforeEach();
        List<University> allUniversities = this.repository.getAllUniversities();
        Assert.assertEquals(UNIVERSITIES.size(), allUniversities.size());
    }

    @Test
    public void deleteUniversity() {
        beforeEach();
        repository.deleteUniversity(Cambridge.getId());
        Assert.assertEquals(UNIVERSITIES.size()-1, repository.getAllUniversities().size());
    }

    @Test
    public void updateUniversity() {
        beforeEach();
        University university = new University(Oxford.getId(), "Oxford University");
        repository.updateUniversity(university);
        Assert.assertEquals("Oxford University", repository.getUniversity(Oxford.getId()).getName());
    }

    @Test
    public void getUniversityWithStudents() {
        beforeEach();
        University university = repository.getUniversityWithStudents(3);
        List<Student> students = university.getStudents();
        for (Student student:students) {
            System.out.println(student.getName());
        }
        Assert.assertEquals(2, repository.getUniversityWithStudents(3).getStudents().size());
    }
}