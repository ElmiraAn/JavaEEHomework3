package com.elmira.aston.homework3.servlets;

import com.elmira.aston.homework3.model.*;
import com.elmira.aston.homework3.repository.*;
import org.junit.jupiter.api.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SubjectServletTest {

    private SubjectService subjectService;
    private SubjectServlet subjectServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter writer ;

    @BeforeEach
    public void setUp() throws IOException {
        subjectService = mock(SubjectService.class);
        subjectServlet = new SubjectServlet(subjectService);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Test
    public void allSubjectsTest() throws ServletException, IOException {

        List<Subject> subjects = Arrays.asList(
                new Subject(1, "Maths"),
                new Subject(2, "History")
        );
        when(subjectService.getAllSubjects()).thenReturn(subjects);
        when(request.getServletPath()).thenReturn("/subject/get-all");
        subjectServlet.doGet(request, response);
        verify(subjectService).getAllSubjects();
        assertEquals("Maths|\r\nHistory|\r\n", writer.toString());
    }

    @Test
    public void addSubjectTest() throws ServletException, IOException {

        when(request.getParameter("subject_name")).thenReturn("Art");
        when(request.getServletPath()).thenReturn("/subject/add");

        subjectServlet.doGet(request, response);
        verify(subjectService).addSubject(new Subject("Art"));
        verify(response).sendRedirect("/Success.jsp");
    }
    @Test
    public void updateSubjectTest() throws ServletException, IOException {

        when(request.getParameter("subject_id")).thenReturn("1");
        when(request.getParameter("subject_name")).thenReturn("History");
        when(request.getServletPath()).thenReturn("/subject/update");

        subjectServlet.doGet(request, response);

        verify(subjectService).updateSubject(new Subject(1, "History"));
        verify(response).sendRedirect("/Success.jsp");
    }

    @Test
    public void deleteSubjectTest() throws ServletException, IOException {

        when(request.getParameter("subject_id")).thenReturn("1");
        when(request.getServletPath()).thenReturn("/subject/delete");

        subjectServlet.doGet(request, response);

        verify(subjectService).deleteSubject(1);
        verify(response).sendRedirect("/Success.jsp");
    }
    @Test
    public void showSubjectTest() throws ServletException, IOException {

        Subject subject = new Subject(1, "Maths");
        when(subjectService.getSubject(1)).thenReturn(subject);
        when(request.getParameter("subject_id")).thenReturn("1");
        when(request.getServletPath()).thenReturn("/subject/get");

        subjectServlet.doGet(request, response);

        verify(subjectService).getSubject(1);
        assertEquals("Maths\r\n", writer.toString());
    }
    @Test
    public void showSubjectWithStudentsTest() throws ServletException, IOException {

        Student s1 = new Student(1, "Bob");
        Student s2 = new Student(2, "Mary");
        Subject subject = new Subject(1, "Maths", Arrays.asList(s1,s2));

        when(request.getParameter("subject_id")).thenReturn("1");
        when(subjectService.getSubjectWithStudent(1)).thenReturn(subject);
        when(request.getServletPath()).thenReturn("/subject/get-with-students");
        subjectServlet.doGet(request, response);

        verify(subjectService).getSubjectWithStudent(1);
        assertEquals("Maths: \r\nBob, Mary, ", writer.toString());

    }

}