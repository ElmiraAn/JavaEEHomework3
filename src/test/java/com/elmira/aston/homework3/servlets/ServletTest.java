package com.elmira.aston.homework3.servlets;

import com.elmira.aston.homework3.service.StudentService;
import com.elmira.aston.homework3.service.SubjectService;
import com.elmira.aston.homework3.service.UniversityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServletTest {


    protected UniversityService universityService;
    protected StudentService studentService;
    protected SubjectService subjectService;
    protected UniversityServlet universityServlet;
    protected StudentServlet studentServlet;
    protected SubjectServlet subjectServlet;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected ObjectMapper mapper;
    protected StringWriter writer;

    @BeforeEach
    public void setUp() throws IOException {
        universityService = mock(UniversityService.class);
        studentService = mock(StudentService.class);
        subjectService = mock(SubjectService.class);
        universityServlet = new UniversityServlet(universityService);
        studentServlet = new StudentServlet(studentService);
        subjectServlet = new SubjectServlet(subjectService);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = new StringWriter();
        mapper = new ObjectMapper();

        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }
}
