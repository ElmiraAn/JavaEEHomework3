package com.elmira.aston.homework3.servlets;

import com.elmira.aston.homework3.model.*;
import com.elmira.aston.homework3.service.SubjectService;
import com.elmira.aston.homework3.service.SubjectServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/subject/add", "/subject/update", "/subject/delete",
        "/subject/get", "/subject/get-all"/*, "/subject/get-with-students"*/})
public class SubjectServlet extends HttpServlet {
    private SubjectService subjectService;
    private ObjectMapper mapper;

    public SubjectServlet() {
        this.subjectService = new SubjectServiceImpl();
        this.mapper = new ObjectMapper();
    }

    public SubjectServlet(SubjectService subjectService) {
        this.subjectService = subjectService;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {
        String action = request.getServletPath();

        switch (action) {
            /*case "/subject/get-with-students":
                showSubjectWithStudents(request, response);
                break;*/
            case "/subject/delete":
                deleteSubject(request, response);
                break;
            case "/subject/get":
                getSubject(request, response);
                break;
            case "/subject/get-all":
            default:
                allSubjects(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getServletPath();
        switch (action) {
            case "/subject/add":
                addSubject(request, response);
                break;
            case "/subject/update":
                updateSubject(request, response);
                break;
            /*case "/categories/book/add":
                addBookToCategory(request, response);
                break;
            case "/categories/book/delete":
                deleteBookFromCategory(request, response);
                break;*/
        }
    }

    private void allSubjects(HttpServletRequest request, HttpServletResponse response)  {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            List<Subject> subjects = subjectService.getAllSubjects();
            String str = mapper.writeValueAsString(subjects);
            PrintWriter pw = response.getWriter();
            pw.println(str);
            pw.close();
            /*PrintWriter pw = response.getWriter();
            for (Subject subject : subjects) {
                pw.println(subject.getName() + "|");
            }
            pw.close();*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addSubject(HttpServletRequest request, HttpServletResponse response) {
        /*Subject subject = new Subject(request.getParameter("subject_name"));
        subjectService.addSubject(subject);
        response.sendRedirect("/Success.jsp");*/

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Subject subject = mapper.readValue(body, Subject.class);
            subjectService.addSubject(subject);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void updateSubject(HttpServletRequest request, HttpServletResponse response) {
        /*int id = getValidId(request);
        String name = request.getParameter("subject_name");
        Subject sub = new Subject(id, name);
        subjectService.updateSubject(sub);
        response.sendRedirect("/Success.jsp");*/

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String str = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Subject subject = mapper.readValue(str, Subject.class);
            subjectService.updateSubject(subject);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void deleteSubject(HttpServletRequest request, HttpServletResponse response) {
        /*int id = getValidId(request);
        subjectService.deleteSubject(id);
        response.sendRedirect("/Success.jsp");*/

        try {
            request.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("subject_id"));
            subjectService.deleteSubject(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void getSubject(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            int id = Integer.parseInt(request.getParameter("subject_id"));
            String str = mapper.writeValueAsString(subjectService.getSubject(id));
            PrintWriter pw = response.getWriter();
            pw.print(str);
            pw.close();

            /*PrintWriter pw = response.getWriter();
            pw.println(subjectService.getSubject(getValidId(request)).getName());
            pw.close();*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*private void showSubjectWithStudents(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            Subject category = subjectService.getSubjectWithStudent(getValidId(request));
            List<Student> students = category.getStudents();
            pw.println(category.getName() + ": ");
            for (Student student : students) {
                pw.print(student.getName() + ", ");
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getValidId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("subject_id"));
        return Integer.parseInt(paramId);
    }*/
}
