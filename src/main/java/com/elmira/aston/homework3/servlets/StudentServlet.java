package com.elmira.aston.homework3.servlets;

import com.elmira.aston.homework3.model.*;
import com.elmira.aston.homework3.service.StudentService;
import com.elmira.aston.homework3.service.StudentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/student/add", "/student/update", "/student/delete",
        "/student/get-all", "/student/get"})
public class StudentServlet extends HttpServlet {
    private StudentService studentService;
    private ObjectMapper mapper;


    public StudentServlet() {
        this.studentService = new StudentServiceImpl();
        this.mapper = new ObjectMapper();
    }

    public StudentServlet(StudentService studentService) {
        this.studentService = studentService;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();

        switch (action) {
            case "/student/delete":
                deleteStudent(request, response);
                break;
            case "/student/get":
                getStudent(request, response);
                break;
            case "/student/get-all":
            default:
                getAllStudents(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();
        switch (action) {
            case "/student/add":
                addStudent(request, response);
                break;
            case "/student/update":
                updateStudent(request, response);
                break;
        }
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Student student = mapper.readValue(body, Student.class);
            University university = student.getUniversity();
            studentService.addStudent(student, university.getId());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Student student = mapper.readValue(body, Student.class);
            studentService.updateStudent(student);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("student_id"));
            studentService.deleteStudent(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void getStudent(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            int id = Integer.parseInt(request.getParameter("student_id"));
            String str = mapper.writeValueAsString(studentService.getStudent(id));
            PrintWriter pw = response.getWriter();

            pw.print(str);
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAllStudents(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            List<Student> students = studentService.getAllStudents();
            String str = mapper.writeValueAsString(students);
            PrintWriter pw = response.getWriter();
            pw.print(str);
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
