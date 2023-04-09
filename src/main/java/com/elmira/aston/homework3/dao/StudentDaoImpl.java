package com.elmira.aston.homework3.dao;

import com.elmira.aston.homework3.connection.MyConnection;
import com.elmira.aston.homework3.model.Student;
import com.elmira.aston.homework3.model.University;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDAO{

    MyConnection connect;

    public StudentDaoImpl() {
        connect = MyConnection.getInstance();
    }

    public StudentDaoImpl(String file) {
        connect = MyConnection.getInstance(file);
    }

    @Override
    public void addStudent(Student student, int universityId) {
        try (Connection connection = connect.getConnection();
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
    public Student getStudentById(int id) {
        Student student = null;
        try (Connection connection = connect.getConnection();
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
        try (Connection connection = connect.getConnection();
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
        try (Connection connection = connect.getConnection();
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
        try (Connection connection = connect.getConnection();
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
}
