package com.elmira.aston.homework3.servlets;

import com.elmira.aston.homework3.model.*;

import com.elmira.aston.homework3.service.StudentService;
import com.elmira.aston.homework3.service.SubjectService;
import com.elmira.aston.homework3.service.UniversityService;
import org.junit.jupiter.api.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class StudentServletTest extends ServletTest{

    /*@Test
    public void allStudentsWithUniTest() throws ServletException, IOException {

        /*List<Student> students = Arrays.asList(
                new Student(1, "Mary", new University(1, "Princeton")),
                new Student(2, "Karen", new University(2, "Yale"))
        );
        when(studentService.getAllStudentsWithUniversity()).thenReturn(students);

        when(request.getServletPath()).thenReturn("/student/get-with-uni");
        studentServlet.doGet(request, response);
        verify(studentService).getAllStudentsWithUniversity();
        Assertions.assertEquals("Mary - Университет: Princeton | \r\nKaren - Университет: Yale | \r\n", writer.toString());
    */
       /* List<Student> students = Arrays.asList(
                new Student(1, "Mary", new University(1, "Princeton")),
                new Student(2, "Karen", new University(2, "Yale"))
        );

        when(studentService.getAllStudents()).thenReturn(students);

        when(request.getServletPath()).thenReturn("/books/all");

        bookServlet.doGet(request, response);

        Assertions.assertEquals("[{\"id\":1,\"name\":\"New\"},{\"id\":2,\"name\":\"Up\"}]\r\n", writer.toString());

    }*/

    @Test
    public void addStudentTest() throws IOException, ServletException {
        Student student = new Student(1, "Adam", new University(1, "Yale"));
        String body = mapper.writeValueAsString(student);
        StringReader reader = new StringReader(body);

        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(request.getServletPath()).thenReturn("/student/add");

        studentServlet.doPost(request, response);

        verify(studentService).addStudent(student, 1);
        verify(response).setStatus(HttpServletResponse.SC_OK);


    }

    @Test
    public void updateStudentTest() throws IOException, ServletException {
        Student student = new Student(1, "Adam", new University(1, "Yale"));

        student.setName("Amanda");
        String body = mapper.writeValueAsString(student);
        StringReader reader = new StringReader(body);

        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(request.getServletPath()).thenReturn("/student/update");

        studentServlet.doPost(request, response);

        verify(studentService).updateStudent(student);
        verify(response).setStatus(HttpServletResponse.SC_OK);

    }

    @Test
    public void deleteStudentTest() throws IOException, ServletException {
        when(request.getServletPath()).thenReturn("/student/delete");

        when(request.getParameter("student_id")).thenReturn("1");

        studentServlet.doGet(request, response);

        verify(studentService).deleteStudent(1);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);

    }

    @Test
    public void showStudentTest() throws ServletException, IOException {
        Student student = new Student(1, "Adam");
        when(studentService.getStudent(1)).thenReturn(student);
        when(request.getServletPath()).thenReturn("/student/get");
        when(request.getParameter("student_id")).thenReturn("1");

        studentServlet.doGet(request, response);

        verify(studentService).getStudent(1);

        Assertions.assertEquals("{\"id\":1,\"name\":\"Adam\"}", writer.toString());

    }

    @Test
    public void showAllStudentsTest() throws ServletException, IOException {

       when(request.getServletPath()).thenReturn("/student/get-all");

        studentServlet.doGet(request, response);

        verify(request, times(1)).getServletPath();
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).getWriter();

    }

    /*@Test
    public void addSubjectForStudentTest() throws ServletException, IOException {
        Student student = new Student(1, "Mary", new University(1, "Princeton"));
        Subject subject = new Subject(1, "Maths");

        when(request.getServletPath()).thenReturn("/student/add-subject");
        when(request.getParameter("student_id")).thenReturn("1");
        when(request.getParameter("subject_id")).thenReturn("1");
        when(studentService.getStudent(1)).thenReturn(student);
        when(subjectService.getSubject(1)).thenReturn(subject);

        studentServlet.doGet(request, response);

        verify(studentService).addSubjectForStudent(student, subject);
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

        when(studentService.getStudent(1)).thenReturn(student);
        when(studentService.getSubjectsForStudent(1)).thenReturn(subjects);

        studentServlet.doGet(request, response);

        verify(studentService).getSubjectsForStudent(1);
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
        when(studentService.getStudent(1)).thenReturn(student);
        when(studentService.getSubjectsForStudent(1)).thenReturn(subjects);
        when(subjectService.getSubject(2)).thenReturn(subjects.get(1));
        when(request.getServletPath()).thenReturn("/student/delete-subject");

        studentServlet.doGet(request, response);

        verify(studentService).deleteSubjectForStudent(student, subjects.get(1));
        verify(response).sendRedirect("/Success.jsp");

    }*/

}