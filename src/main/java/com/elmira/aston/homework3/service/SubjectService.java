package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.model.Student;
import com.elmira.aston.homework3.model.Subject;
import com.elmira.aston.homework3.repository.SubjectRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectService implements SubjectRepository {

    private String JDBC_URL;
    private String USERNAME;
    private String PASSWORD;
    private String DRIVER;

    public SubjectService() {
    }

    public SubjectService(String database) {
        if (database.equals("mysql")) {
            //JDBC_URL = "jdbc:mysql://localhost:3306/aston_db?useSSL=false&amp";
            JDBC_URL = "jdbc:mysql://localhost:3306/aston_db";
            USERNAME = "bestuser";
            PASSWORD = "bestuser";
            DRIVER = "com.mysql.jdbc.Driver";
        } else if (database.equals("h2")) {
            JDBC_URL = "jdbc:h2:./db/uni;DB_CLOSE_DELAY=-1;";
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

    @Override
    public void addSubject(Subject subject) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO subjects (subject_name) VALUES  (?)")) {
            ps.setString(1, subject.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Subject getSubject(int id) {
        Subject subject = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT sub.subject_name FROM subjects sub WHERE sub.subject_id=?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("subject_name");
                subject = new Subject(id, name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subject;
    }

    @Override
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT sub.subject_id, sub.subject_name FROM subjects sub")) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("subject_id");
                String name = rs.getString("subject_name");
                subjects.add(new Subject(id, name));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subjects;
    }

    @Override
    public void deleteSubject(int id) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM subjects WHERE subject_id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateSubject(Subject subject) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE subjects SET subject_name=? WHERE subject_id=?")) {
            ps.setString(1, subject.getName());
            ps.setInt(2, subject.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Subject getSubjectWithStudent(int id) {
        List<Student> students = new ArrayList<>();
        Subject subject = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT sub.subject_name, s.student_name FROM subjects sub " +
                             "JOIN student_subject ss on sub.subject_id = ss.subject_id " +
                             "JOIN students s on s.student_id = ss.student_id WHERE ss.subject_id=?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("subject_name");
                String studentName = rs.getString("student_name");
                students.add(new Student(studentName));
                subject = new Subject(id, name);
                subject.setStudents(students);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subject;
    }
}
