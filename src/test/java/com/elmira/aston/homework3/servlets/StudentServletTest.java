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

public class StudentServletTest extends ServletTest {

    @Test
    public void addStudentTest() throws IOException {
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
    public void updateStudentTest() throws IOException {
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
    public void deleteStudentTest() {
        when(request.getServletPath()).thenReturn("/student/delete");
        when(request.getParameter("student_id")).thenReturn("1");
        studentServlet.doGet(request, response);
        verify(studentService).deleteStudent(1);
        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    public void showStudentTest() {
        Student student = new Student(1, "Adam");
        when(studentService.getStudent(1)).thenReturn(student);
        when(request.getServletPath()).thenReturn("/student/get");
        when(request.getParameter("student_id")).thenReturn("1");
        studentServlet.doGet(request, response);
        verify(studentService).getStudent(1);
        Assertions.assertEquals("{\"id\":1,\"name\":\"Adam\"}", writer.toString());
    }

    @Test
    public void showAllStudentsTest() throws IOException {
        when(request.getServletPath()).thenReturn("/student/get-all");
        studentServlet.doGet(request, response);
        verify(request, times(1)).getServletPath();
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).getWriter();
    }

}