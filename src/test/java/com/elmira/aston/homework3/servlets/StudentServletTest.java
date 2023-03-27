package com.elmira.aston.homework3.servlets;

import com.elmira.aston.homework3.model.*;
import com.elmira.aston.homework3.repository.*;

import org.junit.jupiter.api.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class StudentServletTest {

    private StudentRepository studentRepository;
    private UniversityRepository universityRepository;
    private SubjectRepository subjectRepository;
    private StudentServlet studentServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter writer;

    @BeforeEach
    public void setUp() throws IOException {
        universityRepository = mock(UniversityRepository.class);
        subjectRepository = mock(SubjectRepository.class);
        studentRepository = mock(StudentRepository.class);
        studentServlet = new StudentServlet(studentRepository, universityRepository, subjectRepository);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Test
    public void allStudentsWithUniTest() throws ServletException, IOException {

        List<Student> students = Arrays.asList(
                new Student(1, "Mary", new University(1, "Princeton")),
                new Student(2, "Karen", new University(2, "Yale"))
        );
        when(studentRepository.getAllStudentsWithUniversity()).thenReturn(students);

        when(request.getServletPath()).thenReturn("/student/get-with-uni");
        studentServlet.doGet(request, response);
        verify(studentRepository).getAllStudentsWithUniversity();
        Assertions.assertEquals("Mary - Университет: Princeton | \r\nKaren - Университет: Yale | \r\n", writer.toString());
    }

    @Test
    public void addStudentTest() throws IOException, ServletException {
        University uni = new University(1, "Princeton");

        when(universityRepository.getUniversity(1)).thenReturn(uni);
        when(request.getParameter("student_name")).thenReturn("Mary");
        when(request.getParameter("university_id")).thenReturn("1");
        when(request.getServletPath()).thenReturn("/student/add");

        studentServlet.doGet(request, response);
        verify(universityRepository).getUniversity(1);
        verify(studentRepository).addStudent(new Student("Mary"),1);
        verify(response).sendRedirect("/Success.jsp");
    }

    @Test
    public void updateStudentTest() throws IOException, ServletException {
        when(request.getServletPath()).thenReturn("/student/update");
        when(request.getParameter("student_id")).thenReturn("1");
        when(request.getParameter("student_name")).thenReturn("Peter");
        when(request.getParameter("university_id")).thenReturn("1");

        studentServlet.doGet(request, response);

        verify(studentRepository).updateStudent(new Student(1, "Peter", new University(1, "Princeton")));
        verify(response).sendRedirect("/Success.jsp");
    }

    @Test
    public void deleteStudentTest() throws IOException, ServletException {
        when(request.getParameter("student_id")).thenReturn("2");
        when(request.getServletPath()).thenReturn("/student/delete");

        studentServlet.doGet(request, response);

        verify(studentRepository).deleteStudent(2);
        verify(response).sendRedirect("/Success.jsp");
    }

    @Test
    public void showStudentTest() throws ServletException, IOException {
        University uni = new University(1, "Princeton");
        Student student = new Student(1, "Mary", uni);

        when(studentRepository.getStudent(1)).thenReturn(student);
        when(request.getServletPath()).thenReturn("/student/get");
        when(request.getParameter("student_id")).thenReturn("1");

        studentServlet.doGet(request, response);
        //verify(studentRepository, times(2));
        Assertions.assertEquals("Student: Mary  - University: Princeton\r\n", writer.toString());
    }

    @Test
    public void showAllStudentsTest() throws ServletException, IOException {

        Student s1 = new Student(1, "Bob");
        Student s2 = new Student(2, "Mary");
        Student s3 = new Student(3, "Helen");

        List<Student> students = Arrays.asList(s1, s2, s3);
        when(studentRepository.getAllStudents()).thenReturn(students);
        when(request.getServletPath()).thenReturn("/student/get-all");
        studentServlet.doGet(request, response);
        assertEquals("Bob | Mary | Helen | ", writer.toString());
    }

    @Test
    public void addSubjectForStudentTest() throws ServletException, IOException {
        Student student = new Student(1, "Mary", new University(1, "Princeton"));
        Subject subject = new Subject(1, "Maths");

        when(request.getServletPath()).thenReturn("/student/add-subject");
        when(request.getParameter("student_id")).thenReturn("1");
        when(request.getParameter("subject_id")).thenReturn("1");
        when(studentRepository.getStudent(1)).thenReturn(student);
        when(subjectRepository.getSubject(1)).thenReturn(subject);

        studentServlet.doGet(request, response);

        verify(studentRepository).addSubjectForStudent(student, subject);
        verify(response).sendRedirect("/Success.jsp");
    }

    @Test
    public void getSubjectsForStudentTest() throws ServletException, IOException {
        List<Subject> subjects = Arrays.asList(
                new Subject(1, "Maths"),
                new Subject(2, "History")
        );
        Student student = new Student(1, "Mary", new University(1, "Princeton"), subjects);

        when(request.getServletPath()).thenReturn("/student/get-subjects");
        when(request.getParameter("student_id")).thenReturn("1");

        when(studentRepository.getStudent(1)).thenReturn(student);
        when(studentRepository.getSubjectsForStudent(1)).thenReturn(subjects);

        studentServlet.doGet(request, response);

        verify(studentRepository).getSubjectsForStudent(1);
        Assertions.assertEquals("Mary: \r\nMaths, History, ", writer.toString());
    }

    @Test
    public void deleteCategoryForBookTest() throws ServletException, IOException {
        List<Subject> subjects = Arrays.asList(
                new Subject(1, "Maths"),
                new Subject(2, "History"));

        Student student = new Student(1, "Mary", new University(1, "Princeton"), subjects);
        when(request.getParameter("student_id")).thenReturn("1");
        when(request.getParameter("subject_id")).thenReturn("2");
        when(studentRepository.getStudent(1)).thenReturn(student);
        when(studentRepository.getSubjectsForStudent(1)).thenReturn(subjects);
        when(subjectRepository.getSubject(2)).thenReturn(subjects.get(1));
        when(request.getServletPath()).thenReturn("/student/delete-subject");

        studentServlet.doGet(request, response);

        verify(studentRepository).deleteSubjectForStudent(student, subjects.get(1));
        verify(response).sendRedirect("/Success.jsp");

    }

}