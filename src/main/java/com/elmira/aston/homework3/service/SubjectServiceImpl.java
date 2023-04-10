package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.dao.SubjectDAO;
import com.elmira.aston.homework3.dao.SubjectDaoImpl;
import com.elmira.aston.homework3.model.Subject;

import java.util.List;

public class SubjectServiceImpl implements SubjectService {

    SubjectDAO subjectDAO;

    public SubjectServiceImpl() {
        this.subjectDAO = new SubjectDaoImpl();
    }

    public SubjectServiceImpl(SubjectDAO subjectDAO) {
        this.subjectDAO = subjectDAO;
    }

    @Override
    public void addSubject(Subject subject) {
        subjectDAO.addSubject(subject);
    }

    @Override
    public Subject getSubject(int id) {
        return subjectDAO.getSubjectById(id);
    }

    @Override
    public List<Subject> getAllSubjects() {
        return subjectDAO.getAllSubjects();
    }

    @Override
    public void deleteSubject(int id) {
        subjectDAO.deleteSubject(id);
    }

    @Override
    public void updateSubject(Subject subject) {
        subjectDAO.updateSubject(subject);
    }

    /*

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
    }*/
}
