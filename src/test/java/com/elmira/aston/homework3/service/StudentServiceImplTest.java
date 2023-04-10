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

    //SubjectService subjectService;

    @BeforeEach
    void setUp() {
        universityDAO=mock(UniversityDAO.class);
        studentDAO = mock(StudentDAO.class);
        studentService = new StudentServiceImpl(studentDAO);
    }

    @Test
    public void getStudent() {
        /*setUp();
        Student student = studentService.getStudent(1);
        Assert.assertEquals(ADAM.getName(), student.getName());*/
        Student student1 = new Student(1, "Adam");
        when(studentDAO.getStudentById(1)).thenReturn(student1);
        Student student2 = studentService.getStudent(1);
        verify(studentDAO).getStudentById(1);
        assertEquals(student1.getName(),student2.getName());
    }

    @Test
    public void addStudent() {
        /*setUp();
        Student student = new Student("Amy");
        studentService.addStudent(student, Cambridge.getId());
        Assert.assertEquals("Amy", studentService.getStudent(9).getName());
    */
        Student newStudent = new Student(1, "Elena", new University(1, "Oxford"));
        studentService.addStudent(newStudent, 1);
        verify(studentDAO).addStudent(newStudent,1);
    }

    @Test
    public void deleteStudent() {
        /*setUp();
        studentService.deleteStudent(8);
        Assert.assertEquals(students.size() - 1, studentService.getAllStudents().size());
    */
        Student student = new Student(1, "Book");
        studentService.deleteStudent(1);
        verify(studentDAO).deleteStudent(1);

    }

    @Test
    public void updateStudent() {
        /*setUp();
        Student student = new Student(1, "Alex", Harvard);
        studentService.updateStudent(student);
        Assert.assertEquals("Alex", studentService.getStudent(1).getName());
        Assert.assertEquals(Harvard.getName(), studentService.getStudent(1).getUniversity().getName());
    */
        Student updatedStudent = new Student(1, "Adam", new University(1, "Oxford"));
        updatedStudent.setName("Amanda");

        studentService.updateStudent(updatedStudent);
        verify(studentDAO).updateStudent(updatedStudent);
    }

    @Test
    public void getAllStudents() {
        /*setUp();
        List<Student> allStudents = studentService.getAllStudents();
        Assert.assertEquals(students.size(), allStudents.size());
    */
        List<Student> students1 = Arrays.asList(
                new Student(1, "Alex"),
                new Student(2, "Ivan")
        );

        when(studentDAO.getAllStudents()).thenReturn(students1);

        List<Student> students2 = studentService.getAllStudents();

        verify(studentDAO).getAllStudents();

        assertEquals(students1.size(), students2.size());

    }

    /*@Test
    public void getAllStudentsWithUniversity() {
        setUp();
        List<Student> allStudentsWithUniversity = studentService.getAllStudentsWithUniversity();
        Student student = allStudentsWithUniversity.get(1);
        Assert.assertEquals(Harvard.getName(), student.getUniversity().getName());
        Assert.assertEquals(students.size(), allStudentsWithUniversity.size());
    }

    @Test
    public void addSubjectForStudent() {
        setUp();
        Student student = studentService.getStudent(1);
        Subject subject = subjectService.getSubject(2);
        studentService.addSubjectForStudent(student, subject);
        Assert.assertEquals(3, studentService.getSubjectsForStudent(1).size());
    }

    @Test
    public void getSubjectsForStudent() {
        setUp();
        List<Subject> subjects = studentService.getSubjectsForStudent(1);
        Assert.assertEquals(2, subjects.size());
        Assert.assertEquals(MATHS, subjects.get(0));
        Assert.assertEquals(PHYSICS, subjects.get(1));
    }

    @Test
    public void deleteCategoryForBook() {
        setUp();
        List<Subject> subjects = studentService.getSubjectsForStudent(1);
        Subject subject = subjectService.getSubject(1);
        Student student = studentService.getStudent(1);
        studentService.deleteSubjectForStudent(student, subject);
        Assert.assertEquals(1, studentService.getSubjectsForStudent(1).size());
    }*/
}