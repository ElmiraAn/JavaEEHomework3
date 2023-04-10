package com.elmira.aston.homework3.servlets;

import com.elmira.aston.homework3.model.*;
import com.elmira.aston.homework3.service.UniversityService;
import com.elmira.aston.homework3.service.UniversityServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/university/add", "/university/update", "/university/delete",
        "/university/get-all", "/university/get"/*, "/university/get-with-students"*/})

public class UniversityServlet extends HttpServlet {
    private UniversityService universityService;
    private ObjectMapper mapper;

    public UniversityServlet() {
        this.universityService = new UniversityServiceImpl();
        this.mapper = new ObjectMapper();
    }

    public UniversityServlet(UniversityService universityService) {
        this.universityService = universityService;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/university/delete":
                deleteUniversity(request, response);
                break;
            case "/university/get":
                getUniversity(request, response);
                break;
            /*case "/university/get-with-students":
                showUniversityWithStudents(request, response);
                break;*/
            case "/university/get-all":
            default:
                allUniversities(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {
        String action = request.getServletPath();

        switch (action) {
            case "/university/add":
                addUniversity(request, response);
                break;
            case "/university/update":
                updateUniversity(request, response);
                break;
        }
    }

    private void allUniversities(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            List<University> allUniversities = universityService.getAllUniversities();
            String str = mapper.writeValueAsString(allUniversities);
            PrintWriter pw = response.getWriter();
            pw.println(str);
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addUniversity(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            University university = mapper.readValue(body, University.class);
            universityService.addUniversity(university);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void updateUniversity(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            University university = mapper.readValue(body, University.class);
            universityService.updateUniversity(university);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void deleteUniversity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("university_id"));
        universityService.deleteUniversity(id);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    private void getUniversity(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            int id = Integer.parseInt(request.getParameter("university_id"));
            String str = mapper.writeValueAsString(universityService.getUniversity(id));

            PrintWriter pw = response.getWriter();
            pw.print(str);
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*private void showUniversityWithStudents(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            University uni = universityService.getUniversityWithStudents(getValidId(request));
            List<Student> students = uni.getStudents();
            pw.println(uni.getName() + ": ");
            for (Student student : students) {
                pw.print(student.getName() + ", ");
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

    /*private int getValidId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("university_id"));
        return Integer.parseInt(paramId);
    }*/

}
