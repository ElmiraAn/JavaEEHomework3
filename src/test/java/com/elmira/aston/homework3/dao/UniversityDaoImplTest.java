package com.elmira.aston.homework3.dao;

import com.elmira.aston.homework3.model.University;
import org.junit.Assert;
import org.junit.Test;

import static com.elmira.aston.homework3.data.UniversityDataTest.Harvard;
import static org.junit.Assert.*;

public class UniversityDaoImplTest {

    @Test
    public void getUniById() {
        University university1 = new University(1, "Yale");
        University university2 =
        Assert.assertEquals(Harvard.getName(), university.getName());

    }

    @Test
    public void getAllUniversities() {
    }

    @Test
    public void addUniversity() {
    }

    @Test
    public void updateUniversity() {
    }

    @Test
    public void deleteUniversity() {
    }
}