package com.elmira.aston.homework3.servlets;

import com.elmira.aston.homework3.model.*;
import com.elmira.aston.homework3.repository.UniversityRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import static org.mockito.Mockito.*;

public class UniversityServletTest {

    private UniversityRepository universityRepository;
    private UniversityServlet universityServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter writer ;
    @BeforeEach
    public void setUp() throws IOException {
        universityRepository = mock(UniversityRepository.class);
        universityServlet = new UniversityServlet(universityRepository);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Test
    public void allUniversitiesTest() throws ServletException, IOException {
        List<University> allUniversities = Arrays.asList(
                new University(1, "Oxford"),
                new University(2, "Yale")
        );
        when(universityRepository.getAllUniversities()).thenReturn(allUniversities);
        when(request.getServletPath()).thenReturn("/university/get-all");
        universityServlet.doGet(request, response);
        Assertions.assertEquals("Oxford|\r\nYale|\r\n", writer.toString());
    }

    @Test
    public void addUniversityTest() throws IOException, ServletException {
        when(request.getParameter("university_name")).thenReturn("Harvard");
        when(request.getServletPath()).thenReturn("/university/add");

        universityServlet.doGet(request, response);
        verify(universityRepository).addUniversity(new University("Harvard"));
        verify(response).sendRedirect("/Success.jsp");
    }

    @Test
    public void updateUniversityTest() throws IOException, ServletException {
        when(request.getServletPath()).thenReturn("/university/update");
        when(request.getParameter("university_name")).thenReturn("Princeton");
        when(request.getParameter("university_id")).thenReturn("2");

        universityServlet.doGet(request, response);

        verify(universityRepository).updateUniversity(new University(2, "Princeton"));
        verify(response).sendRedirect("/Success.jsp");
    }

    @Test
    public void deleteUniversityTest() throws IOException, ServletException {
        when(request.getParameter("university_id")).thenReturn("2");
        when(request.getServletPath()).thenReturn("/university/delete");

        universityServlet.doGet(request, response);

        verify(universityRepository).deleteUniversity(2);
        verify(response).sendRedirect("/Success.jsp");
    }

    @Test
    public void showUniversityTest() throws ServletException, IOException {
        University uni = new University(1, "Princeton");

        when(universityRepository.getUniversity(1)).thenReturn(uni);
        when(request.getServletPath()).thenReturn("/university/get");
        when(request.getParameter("university_id")).thenReturn("1");
        universityServlet.doGet(request, response);
        Assertions.assertEquals("Princeton\r\n", writer.toString());
    }

    @Test
    public void showUniversityWithStudentsTest() throws ServletException, IOException {
        Student s1 = new Student(1, "Bob");
        Student s2 = new Student(2, "Mary");
        Student s3 = new Student(3, "Helen");
        University uni = new University(1, "Princeton", Arrays.asList(s1,s2,s3));

        when(universityRepository.getUniversityWithStudents(1)).thenReturn(uni);
        when(request.getServletPath()).thenReturn("/university/get-with-students");
        when(request.getParameter("university_id")).thenReturn("1");

        universityServlet.doGet(request, response);
        Assertions.assertEquals("Princeton: \r\nBob, Mary, Helen, ", writer.toString());
    }
}