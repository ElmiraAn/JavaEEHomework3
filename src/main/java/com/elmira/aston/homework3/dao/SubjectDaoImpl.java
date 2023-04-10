package com.elmira.aston.homework3.dao;

import com.elmira.aston.homework3.connection.MyConnection;
import com.elmira.aston.homework3.model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SubjectDaoImpl implements SubjectDAO {
    MyConnection connect;

    public SubjectDaoImpl() {
        connect = MyConnection.getInstance();
    }

    public SubjectDaoImpl(String file) {
        connect = MyConnection.getInstance(file);
    }

    @Override
    public void addSubject(Subject subject) {
        try (Connection connection = connect.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO subjects (subject_name) VALUES  (?)")) {
            ps.setString(1, subject.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Subject getSubjectById(int id) {
        Subject subject = null;
        try (Connection connection = connect.getConnection();
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
        try (Connection connection = connect.getConnection();
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
        try (Connection connection = connect.getConnection();
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
        try (Connection connection = connect.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE subjects SET subject_name=? WHERE subject_id=?")) {
            ps.setString(1, subject.getName());
            ps.setInt(2, subject.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
