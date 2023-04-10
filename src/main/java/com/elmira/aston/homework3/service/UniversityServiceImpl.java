package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.dao.UniversityDAO;
import com.elmira.aston.homework3.dao.UniversityDaoImpl;
import com.elmira.aston.homework3.model.University;

import java.util.List;

public class UniversityServiceImpl implements UniversityService {

    private UniversityDAO universityDAO;

    public UniversityServiceImpl() {
        this.universityDAO = new UniversityDaoImpl();
    }

    public UniversityServiceImpl(UniversityDAO universityDAO) {
        this.universityDAO = universityDAO;
    }

    @Override
    public University getUniversity(int id) {
        return universityDAO.getUniById(id);
    }

    @Override
    public List<University> getAllUniversities() {
        return universityDAO.getAllUniversities();
    }

    @Override
    public void addUniversity(University university) {
        universityDAO.addUniversity(university);
    }

    @Override
    public void deleteUniversity(int id) {
        universityDAO.deleteUniversity(id);
    }

    @Override
    public void updateUniversity(University university) {
        universityDAO.updateUniversity(university);
    }



/*

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
    }*/

}
