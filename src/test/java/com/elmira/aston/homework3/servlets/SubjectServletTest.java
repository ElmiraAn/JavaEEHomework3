package com.elmira.aston.homework3.servlets;

import com.elmira.aston.homework3.model.*;
import com.elmira.aston.homework3.service.SubjectService;
import org.junit.jupiter.api.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SubjectServletTest extends ServletTest{

    @Test
    public void allSubjectsTest() {
        List<Subject> subjects = Arrays.asList(
                new Subject(1, "Maths"),
                new Subject(2, "History")
        );
        when(subjectService.getAllSubjects()).thenReturn(subjects);

        when(request.getServletPath()).thenReturn("/subject/get-all");

        subjectServlet.doGet(request, response);

        Assertions.assertEquals("[{\"id\":1,\"name\":\"Maths\"}," +
                "{\"id\":2,\"name\":\"History\"}]\r\n", writer.toString());
    }

    @Test
    public void addSubjectTest() throws IOException {
        Subject subject = new Subject("English");

        String body = mapper.writeValueAsString(subject);
        StringReader reader = new StringReader(body);

        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(request.getServletPath()).thenReturn("/subject/add");

        subjectServlet.doPost(request, response);

        verify(subjectService).addSubject(subject);
        verify(response).setStatus(HttpServletResponse.SC_OK);

    }
    @Test
    public void updateSubjectTest() throws IOException {
        Subject updatedSubject = new Subject(1, "Maths");
        updatedSubject.setName("Higher Maths");
        String body = mapper.writeValueAsString(updatedSubject);
        StringReader reader = new StringReader(body);

        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(request.getServletPath()).thenReturn("/subject/update");


        subjectServlet.doPost(request, response);

        verify(subjectService).updateSubject(updatedSubject);

        verify(response).setStatus(HttpServletResponse.SC_OK);

    }

    @Test
    public void deleteSubjectTest() {
        when(request.getServletPath()).thenReturn("/subject/delete");

        when(request.getParameter("subject_id")).thenReturn("1");

        subjectServlet.doGet(request, response);

        verify(subjectService).deleteSubject(1);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);

    }
    @Test
    public void showSubjectTest() {
        Subject subject = new Subject(1, "Maths");
        when(subjectService.getSubject(1)).thenReturn(subject);
        when(request.getServletPath()).thenReturn("/subject/get");
        when(request.getParameter("subject_id")).thenReturn("1");

        subjectServlet.doGet(request, response);

        verify(subjectService).getSubject(1);

        Assertions.assertEquals("{\"id\":1,\"name\":\"Maths\"}", writer.toString());

    }
}