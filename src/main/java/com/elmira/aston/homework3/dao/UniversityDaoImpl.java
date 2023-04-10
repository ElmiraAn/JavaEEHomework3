package com.elmira.aston.homework3.dao;

import com.elmira.aston.homework3.connection.MyConnection;
import com.elmira.aston.homework3.model.University;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UniversityDaoImpl implements UniversityDAO {
    MyConnection connect;

    public UniversityDaoImpl() {
        connect = MyConnection.getInstance();
    }

    public UniversityDaoImpl(String file) {
        connect = MyConnection.getInstance(file);
    }

    @Override
    public University getUniById(int id) {
        University university = null;
        try (Connection connection = connect.getConnection();
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

    @Override
    public List<University> getAllUniversities() {
        List<University> universities = new ArrayList<>();
        try (Connection connection = connect.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT u.university_id, u.university_name FROM universities u")) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("university_id");
                String name = rs.getString("university_name");
                universities.add(new University(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return universities;
    }

    @Override
    public void addUniversity(University university) {
        try (Connection connection = connect.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO  universities (university_name) VALUES (?)")) {
            ps.setString(1, university.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUniversity(University university) {
        try (Connection connection = connect.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE universities u SET u.university_name=? WHERE u.university_id=?")) {
            ps.setString(1, university.getName());
            ps.setInt(2, university.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUniversity(int id) {
        try (Connection connection = connect.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM universities u WHERE u.university_id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
