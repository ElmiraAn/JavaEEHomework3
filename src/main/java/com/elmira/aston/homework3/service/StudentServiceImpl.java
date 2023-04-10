package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.dao.StudentDAO;
import com.elmira.aston.homework3.dao.StudentDaoImpl;
import com.elmira.aston.homework3.model.Student;

import java.util.List;

public class StudentServiceImpl implements StudentService {

    StudentDAO studentDAO;

    public StudentServiceImpl() {
        this.studentDAO = new StudentDaoImpl();
    }

    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public void addStudent(Student student, int universityId) {
        studentDAO.addStudent(student, universityId);
    }

    @Override
    public Student getStudent(int id) {
        return studentDAO.getStudentById(id);
    }

    @Override
    public void deleteStudent(int id) {
        studentDAO.deleteStudent(id);
    }

    @Override
    public void updateStudent(Student student) {
        studentDAO.updateStudent(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }



   /*
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

    @Override
    public void addSubjectForStudent(Student student, Subject subject) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO student_subject (student_id, subject_id) VALUES (?, ?)")) {
            ps.setInt(1, student.getId());
            ps.setInt(2, subject.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Subject> getSubjectsForStudent(int studentId) {
        List<Subject> subjects = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT sub.subject_name FROM students st " +
                             "JOIN student_subject ss on ss.student_id = st.student_id " +
                             "JOIN subjects sub on ss.subject_id = sub.subject_id WHERE ss.student_id=?")) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //String studentName = rs.getString("student_name");
                //int subjectId = rs.getInt("subject_id");
                String subjectName = rs.getString("subject_name");
                subjects.add(new Subject(subjectName));
                //studentWithSubjects = new Student(id, studentName,  subjects);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subjects;
    }

    @Override
    public void deleteSubjectForStudent(Student student, Subject subject) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM student_subject WHERE student_id=? and subject_id=?")) {
            ps.setInt(1, student.getId());
            ps.setInt(2, subject.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/

    //Лишний метод
    /*@Override
    public Student getStudentWithSubjects(int id) {
        Student studentWithSubjects = null;
        List<Subject> subjects = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT st.student_name, sub.subject_name, sub.subject_id FROM students st " +
                             "JOIN student_subject ss on ss.student_id = st.student_id "+
                             "JOIN subjects sub on ss.subject_id = sub.subject_id WHERE st.student_id=?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String studentName = rs.getString("student_name");
                int subjectId = rs.getInt("subject_id");
                String subjectName = rs.getString("subject_name");
                subjects.add(new Subject(subjectId, subjectName));
                studentWithSubjects = new Student(id, studentName,  subjects);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return studentWithSubjects;
    }*/
}
