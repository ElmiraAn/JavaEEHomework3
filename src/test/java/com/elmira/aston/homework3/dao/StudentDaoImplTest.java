package com.elmira.aston.homework3.dao;

import com.elmira.aston.homework3.model.Student;
import com.elmira.aston.homework3.model.University;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class StudentDaoImplTest extends  DaoTest{

    @Test
    public void addStudent() {
        Student newStudent = new Student();
        newStudent.setName("Mark");
        studentDAO.addStudent(newStudent, 1);
        Assertions.assertEquals(newStudent.getName(), studentDAO.getStudentById(9).getName());

    }

    @Test
    public void getStudentById() {
        Student student1 = new Student(1, "Adam");
        Student student2 = studentDAO.getStudentById(1);
        Assertions.assertEquals(student1.getName(), student2.getName());

    }

    @Test
    public void deleteStudent() {
        Assertions.assertEquals(8, studentDAO.getAllStudents().size());
        studentDAO.deleteStudent(2);
        Assertions.assertEquals(7, studentDAO.getAllStudents().size());

    }

    @Test
    public void updateStudent() {
        Student updatedStudent = studentDAO.getStudentById(1);
        updatedStudent.setName("Adam Sandler");

        updatedStudent.setUniversity(new University(1, "Oxford"));
        studentDAO.updateStudent(updatedStudent);
        Assertions.assertEquals("Adam Sandler", studentDAO.getStudentById(1).getName());
    }

    @Test
    public void getAllStudents() {
        List<Student> studentList = studentDAO.getAllStudents();
        Assertions.assertEquals(8, studentList.size());
    }
}