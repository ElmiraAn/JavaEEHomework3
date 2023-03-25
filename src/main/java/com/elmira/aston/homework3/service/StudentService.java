package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.model.Student;
import com.elmira.aston.homework3.model.University;
import com.elmira.aston.homework3.repository.StudentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements StudentRepository {

    private String JDBC_URL;
    private String USERNAME;
    private String PASSWORD;
    private String DRIVER;

    public StudentService() {
    }

    public StudentService(String database) {
        if (database.equals("mysql")) {
            JDBC_URL = "jdbc:mysql://localhost:3306/aston_db?useSSL=false&amp";
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
    public void addStudent(Student student, int universityId) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO students (student_name, university_id) VALUES (?, ?)")) {
            ps.setString(1, student.getName());
            ps.setInt(2, universityId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Student getStudent(int id) {
        Student student = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT s.student_name, uni.university_name AS uniName FROM students s " +
                             "LEFT JOIN universities uni on uni.university_id = s.university_id WHERE s.student_id=?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("student_name");
                String uniName = rs.getString("uniName");
                student = new Student(id, name, new University(uniName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return student;
    }


    @Override
    public void deleteStudent(int id) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM students s WHERE s.student_id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateStudent(Student student) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE students s SET s.student_name=?, s.university_id=? WHERE s.student_id=?")) {
            ps.setString(1, student.getName());
            ps.setInt(2, student.getUniversity().getId());
            ps.setInt(3, student.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT s.student_id, s.student_name FROM students s ")) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("student_id");
                String name = rs.getString("student_name");
                students.add(new Student(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    @Override
    public List<Student> getAllStudentsWithUniversity() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT s.student_id, s.student_name, uni.university_name AS uniName FROM students s " +
                             "JOIN universities uni on uni.university_id = s.university_id")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("student_id");
                String studentName = rs.getString("student_name");
                String uniName = rs.getString("uniName");
                students.add(new Student(id, studentName, new University(uniName)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
}
