package com.elmira.aston.homework3.service;

import com.elmira.aston.homework3.dao.StudentDAO;
import com.elmira.aston.homework3.dao.UniversityDAO;
import com.elmira.aston.homework3.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StudentServiceImplTest {
    private StudentService studentService;
    private UniversityDAO universityDAO;
    private StudentDAO studentDAO;

    @BeforeEach
    void setUp() {
        universityDAO = mock(UniversityDAO.class);
        studentDAO = mock(StudentDAO.class);
        studentService = new StudentServiceImpl(studentDAO);
    }

    @Test
    public void getStudent() {
        Student student1 = new Student(1, "Adam");
        when(studentDAO.getStudentById(1)).thenReturn(student1);
        Student student2 = studentService.getStudent(1);
        verify(studentDAO).getStudentById(1);
        assertEquals(student1.getName(), student2.getName());
    }

    @Test
    public void addStudent() {
        Student newStudent = new Student(1, "Elena", new University(1, "Oxford"));
        studentService.addStudent(newStudent, 1);
        verify(studentDAO).addStudent(newStudent, 1);
    }

    @Test
    public void deleteStudent() {
        Student student = new Student(1, "Book");
        studentService.deleteStudent(1);
        verify(studentDAO).deleteStudent(1);

    }

    @Test
    public void updateStudent() {
        Student updatedStudent = new Student(1, "Adam", new University(1, "Oxford"));
        updatedStudent.setName("Amanda");

        studentService.updateStudent(updatedStudent);
        verify(studentDAO).updateStudent(updatedStudent);
    }

    @Test
    public void getAllStudents() {
        List<Student> students1 = Arrays.asList(
                new Student(1, "Alex"),
                new Student(2, "Ivan")
        );

        when(studentDAO.getAllStudents()).thenReturn(students1);

        List<Student> students2 = studentService.getAllStudents();

        verify(studentDAO).getAllStudents();

        assertEquals(students1.size(), students2.size());

    }
}