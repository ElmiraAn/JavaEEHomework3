package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.model.Student;
import com.elmira.aston.homework3.model.University;
import com.elmira.aston.homework3.repository.UniversityRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UniversityService implements UniversityRepository {

    private String JDBC_URL;
    private String USERNAME;
    private String PASSWORD;
    private String DRIVER;

    public Connection connection;

    public UniversityService() {
    }

    public UniversityService(String database) {
        if (database.equals("mysql")) {
            JDBC_URL = "jdbc:mysql://localhost:3306/aston_db?useSSL=false&amp";
            USERNAME = "bestuser";
            PASSWORD = "bestuser";
            DRIVER = "com.mysql.jdbc.Driver";
        } else if (database.equals("h2")) {
            JDBC_URL = "jdbc:h2:./db/uni;DB_CLOSE-DELAY=-1";
            USERNAME = "sa";
            PASSWORD = "";
            DRIVER = "org.h2.Driver";
        }
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(JDBC_URL,
                    USERNAME, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void addUniversity(University university) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO  universities (university_name) VALUES (?)")) {
            ps.setString(1, university.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public University getUniversity(int id) {
        University university = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT universities.university_name FROM universities WHERE universities.university_id=?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("university_name");
                university = new University(id, name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return university;
    }

    public List<University> getAllUniversities() {
        List<University> universities = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT u.university_id, u.university_name FROM universities u")) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("university_id");//!!!!!!!!!!!!!1
                String name = rs.getString("university_name");
                universities.add(new University(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return universities;
    }

    public void deleteUniversity(int id) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM universities u WHERE u.university_id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateUniversity(University university) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE universities u SET u.university_name=? WHERE u.university_id=?")) {
            ps.setString(1, university.getName());
            ps.setInt(2, university.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public University getUniversityWithStudents(int id) {
        University universityWithStudents = null;
        List<Student> students = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT u.university_name, s.student_id, s.student_name FROM universities u " +
                             "JOIN students s on u.university_id = s.university_id WHERE s.university_id=?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String uniName = rs.getString("university_name");
                int studentId = rs.getInt("student_id");
                String studentName = rs.getString("student_name");
                students.add(new Student(studentId, studentName));
                universityWithStudents = new University(id, uniName, students);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return universityWithStudents;
    }
}
