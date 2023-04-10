package com.elmira.aston.homework3.servlets;

import com.elmira.aston.homework3.model.*;
import com.elmira.aston.homework3.service.StudentService;
import com.elmira.aston.homework3.service.StudentServiceImpl;
import com.elmira.aston.homework3.service.UniversityService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/student/add", "/student/update", "/student/delete",
        "/student/get-all", "/student/get"/*, "/student/get-with-uni",
        "/student/get-subjects", "/student/add-subject", "/student/delete-subject"*/})
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
           /* case "/student/get-subjects":
                getSubjectsForStudent(request, response);
                break;
            case "/student/add-subject":
                addSubjectForStudent(request, response);
                break;
            case "/student/delete-subject":
                deleteSubjectForStudent(request, response);
                break;
            default:
                allStudentsWithUni(request, response);*/
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    /*private void allStudentsWithUni(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> students = studentService.getAllStudentsWithUniversity();

        request.setAttribute("students", students);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            for (Student student : students) {
                pw.println(student.getName() + " - Университет: " + student.getUniversity().getName() + " | ");
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addSubjectForStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("student_id"));
        int subjectId = Integer.parseInt(request.getParameter("subject_id"));
        Student student = studentService.getStudent(studentId);
        Subject subject = subjectService.getSubject(subjectId);
        studentService.addSubjectForStudent(student, subject);
        response.sendRedirect("/Success.jsp");
    }

    private void getSubjectsForStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Subject> subjects = new ArrayList<>();
        int studentId = getValidId(request);
        Student student = studentService.getStudent(studentId);
        subjects = studentService.getSubjectsForStudent(studentId);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            pw.println(student.getName() + ": ");
            for (Subject subject : subjects) {
                pw.print(subject.getName() + ", ");
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteSubjectForStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("student_id"));
        int subjectId = Integer.parseInt(request.getParameter("subject_id"));
        Student student = studentService.getStudent(studentId);
        Subject subject = subjectService.getSubject(subjectId);
        studentService.deleteSubjectForStudent(student, subject);

        response.sendRedirect("/Success.jsp");
    }

    private int getValidId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("student_id"));
        return Integer.parseInt(paramId);
    }*/

}
