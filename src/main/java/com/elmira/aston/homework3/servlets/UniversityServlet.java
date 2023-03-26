package com.elmira.aston.homework3.servlets;

import com.elmira.aston.homework3.model.Student;
import com.elmira.aston.homework3.model.University;
import com.elmira.aston.homework3.repository.UniversityRepository;
import com.elmira.aston.homework3.service.UniversityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

@WebServlet(urlPatterns = {"/university/add", "/university/update", "/university/delete",
        "/university/get-all", "/university/get", "/university/get-with-students"})

public class UniversityServlet extends HttpServlet {

    private UniversityRepository universityRepository;

    public UniversityServlet() {
    }

    public UniversityServlet(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Override
    public void init() throws ServletException {
        this.universityRepository = new UniversityService("mysql");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/university/add":
                addUniversity(request, response);
                break;
            case "/university/delete":
                deleteUniversity(request, response);
                break;
            case "/university/get":
                showUniversity(request, response);
                break;
            case "/university/get-with-students":
                showUniversityWithStudents(request, response);
                break;
            case "/university/update":
                updateUniversity(request, response);
                break;
            default:
                allUniversities(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void allUniversities(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<University> allUniversities = universityRepository.getAllUniversities();
        request.setAttribute("allUni", allUniversities);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            for (University university : allUniversities) {
                pw.println(university.getName() + "|");
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addUniversity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        University newUniversity = new University(request.getParameter("university_name"));
        universityRepository.addUniversity(newUniversity);
        response.sendRedirect("/Success.jsp");
    }

    private void updateUniversity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = getValidId(request);
        String name = request.getParameter("university_name");
        University uni = new University(id, name);
        universityRepository.updateUniversity(uni);
        response.sendRedirect("/Success.jsp");
    }

    private void deleteUniversity(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = getValidId(request);
        universityRepository.deleteUniversity(id);
        response.sendRedirect("/Success.jsp");
    }

    private void showUniversity(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            pw.println(universityRepository.getUniversity(getValidId(request)).getName());
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showUniversityWithStudents(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            University uni = universityRepository.getUniversityWithStudents(getValidId(request));
            List<Student> students = uni.getStudents();
            pw.println(uni.getName() + ": ");
            for (Student student : students) {
                pw.print(student.getName() + ", ");
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getValidId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("university_id"));
        return Integer.parseInt(paramId);
    }


}
