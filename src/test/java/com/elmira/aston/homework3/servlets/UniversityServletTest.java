package com.elmira.aston.homework3.servlets;

import com.elmira.aston.homework3.dao.UniversityDAO;
import com.elmira.aston.homework3.model.*;
import com.elmira.aston.homework3.service.UniversityService;

import com.elmira.aston.homework3.service.UniversityServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UniversityServletTest extends ServletTest{

    @Test
    public void allUniversitiesTest() throws ServletException, IOException {
        List<University> allUniversities = Arrays.asList(
                new University(1, "Oxford"),
                new University(2, "Yale")
        );
        when(universityService.getAllUniversities()).thenReturn(allUniversities);
        when(request.getServletPath()).thenReturn("/university/get-all");
        universityServlet.doGet(request, response);
        //Assertions.assertEquals("Oxford|\r\nYale|\r\n", writer.toString());
        Assertions.assertEquals("[{\"id\":1,\"name\":\"Oxford\"}," +
                "{\"id\":2,\"name\":\"Yale\"}]\r\n", writer.toString());
    }

    @Test
    public void addUniversityTest() throws IOException, ServletException {
        University university = new University(1, "Oxford");
        String str_body = mapper.writeValueAsString(university);
        StringReader reader = new StringReader(str_body);

        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(request.getServletPath()).thenReturn("/university/add");

        universityServlet.doPost(request, response);

        verify(universityService).addUniversity(university);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void updateUniversityTest() throws IOException, ServletException {
        University university = new University(1, "Oxford");
        university.setName("Oxford University");
        String srt_body = mapper.writeValueAsString(university);
        StringReader reader = new StringReader(srt_body);

        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(request.getServletPath()).thenReturn("/university/update");
        universityServlet.doPost(request, response);
        verify(universityService).updateUniversity(university);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void deleteUniversityTest() throws IOException, ServletException {
        when(request.getServletPath()).thenReturn("/university/delete");

        when(request.getParameter("university_id")).thenReturn("1");

        universityServlet.doGet(request, response);

        verify(universityService).deleteUniversity(1);

        verify(response).setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @Test
    public void getUniversityTest() throws ServletException, IOException {
        University university = new University(1, "Yale");
        when(universityService.getUniversity(1)).thenReturn(university);
        when(request.getServletPath()).thenReturn("/university/get");
        when(request.getParameter("university_id")).thenReturn("1");

        universityServlet.doGet(request, response);

        verify(universityService).getUniversity(1);

        Assertions.assertEquals("{\"id\":1,\"name\":\"Yale\"}", writer.toString());
    }

    /*@Test
    public void showUniversityWithStudentsTest() throws ServletException, IOException {
        Student s1 = new Student(1, "Bob");
        Student s2 = new Student(2, "Mary");
        Student s3 = new Student(3, "Helen");
        University uni = new University(1, "Princeton", Arrays.asList(s1,s2,s3));

        when(universityService.getUniversityWithStudents(1)).thenReturn(uni);
        when(request.getServletPath()).thenReturn("/university/get-with-students");
        when(request.getParameter("university_id")).thenReturn("1");

        universityServlet.doGet(request, response);
        Assertions.assertEquals("Princeton: \r\nBob, Mary, Helen, ", writer.toString());
    }*/
}