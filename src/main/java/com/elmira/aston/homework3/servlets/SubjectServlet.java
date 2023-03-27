package com.elmira.aston.homework3.servlets;

import com.elmira.aston.homework3.model.*;
import com.elmira.aston.homework3.repository.SubjectRepository;
import com.elmira.aston.homework3.service.SubjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

@WebServlet(urlPatterns = {"/subject/add", "/subject/update", "/subject/delete",
        "/subject/get", "/subject/get-all", "/subject/get-with-students"})
public class SubjectServlet extends HttpServlet {
    private SubjectRepository subjectRepository;

    public SubjectServlet() {
    }

    public SubjectServlet(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void init() throws ServletException {
        this.subjectRepository = new SubjectService("mysql");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/subject/add":
                addSubject(request, response);
                break;
            case "/subject/get-with-students":
                showSubjectWithStudents(request, response);
                break;
            case "/subject/delete":
                deleteSubject(request, response);
                break;
            case "/subject/get":
                showSubject(request, response);
                break;
            case "/subject/update":
                updateSubject(request, response);
                break;
            default:
                allSubjects(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void allSubjects(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Subject> subjects = subjectRepository.getAllSubjects();
        request.setAttribute("subjects", subjects);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            for (Subject subject : subjects) {
                pw.println(subject.getName() + "|");
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addSubject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Subject subject = new Subject(request.getParameter("subject_name"));
        subjectRepository.addSubject(subject);
        response.sendRedirect("/Success.jsp");
    }

    private void updateSubject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = getValidId(request);
        String name = request.getParameter("subject_name");
        Subject sub = new Subject(id, name);
        subjectRepository.updateSubject(sub);
        response.sendRedirect("/Success.jsp");
    }

    private void deleteSubject(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = getValidId(request);
        subjectRepository.deleteSubject(id);
        response.sendRedirect("/Success.jsp");
    }

    private void showSubject(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            pw.println(subjectRepository.getSubject(getValidId(request)).getName());
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showSubjectWithStudents(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            Subject category = subjectRepository.getSubjectWithStudent(getValidId(request));
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
    }
}
