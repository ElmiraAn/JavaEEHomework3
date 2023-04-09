package com.elmira.aston.homework3.dao;

import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DaoTest {
    protected StudentDAO studentDAO;
    protected UniversityDAO universityDAO;
    protected SubjectDAO subjectDAO;
    private Properties properties;
    private final String filename = "db_h2.properties";

    @BeforeEach
    void setUp() throws IOException, SQLException {
        setStartProperties();
        prepareDB();
        studentDAO = new StudentDaoImpl(filename);
        universityDAO = new UniversityDaoImpl(filename);
        subjectDAO = new SubjectDaoImpl(filename);
    }

    private void setStartProperties() throws IOException {
        properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        properties.load(inputStream);
        inputStream.close();
    }

    private void prepareDB() throws SQLException {
        String prepare = "jdbc:h2:./db/library;INIT=RUNSCRIPT FROM 'classpath:dbH2init.sql';DB_CLOSE_DELAY=-1";
        Connection connection = DriverManager.getConnection(
                prepare,
                properties.getProperty("datasource.username"),
                properties.getProperty("datasource.password")
        );
        connection.close();
    }
}
