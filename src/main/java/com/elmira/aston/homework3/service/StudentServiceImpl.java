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
}
