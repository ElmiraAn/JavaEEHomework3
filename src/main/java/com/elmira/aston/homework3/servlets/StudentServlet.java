package com.elmira.aston.homework3.servlets;

import com.elmira.aston.homework3.model.*;
import com.elmira.aston.homework3.repository.*;
import com.elmira.aston.homework3.service.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

@WebServlet(urlPatterns = {"/student/add", "/student/update", "/student/delete",
        "/student/get-all", "/student/get", "/student/get-with-uni",
        "/student/get-subjects", "/student/add-subject", "/student/delete-subject"})
public class StudentServlet extends HttpServlet {
    private StudentRepository studentRepository;
    private UniversityRepository universityRepository;
    private SubjectRepository subjectRepository;

    public StudentServlet() {
    }

    public StudentServlet(StudentRepository studentRepository, UniversityRepository universityRepository) {
        this.studentRepository = studentRepository;
        this.universityRepository = universityRepository;
    }

    public StudentServlet(StudentRepository studentRepository, UniversityRepository universityRepository, SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.universityRepository = universityRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void init() throws ServletException {
        this.studentRepository = new StudentService("mysql");
        this.universityRepository = new UniversityService("mysql");
        this.subjectRepository = new SubjectService("mysql");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/student/add":
                addStudent(request, response);
                break;
            case "/student/delete":
                deleteStudent(request, response);
                break;
            case "/student/get":
                showStudent(request, response);
                break;
            case "/student/update":
                updateStudent(request, response);
                break;
            case "/student/get-all":
                showAllStudents(request, response);
                break;
            case "/student/get-subjects":
                getSubjectsForStudent(request, response);
                break;
            case "/student/add-subject":
                addSubjectForStudent(request, response);
                break;
            case "/student/delete-subject":
                deleteSubjectForStudent(request, response);
                break;
            default:
                allStudentsWithUni(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Student student = null;
        String studentName = request.getParameter("student_name");
        int uniId = Integer.parseInt(request.getParameter("university_id"));
        University university = universityRepository.getUniversity(uniId);
        student = new Student(studentName, university);
        studentRepository.addStudent(student, uniId);
        response.sendRedirect("/Success.jsp");
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = getValidId(request);
        int uniId = Integer.parseInt(request.getParameter("university_id"));
        University university = universityRepository.getUniversity(uniId);
        String name = request.getParameter("student_name");
        Student student = new Student(id, name, university);
        studentRepository.updateStudent(student);
        response.sendRedirect("/Success.jsp");
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = getValidId(request);
        studentRepository.deleteStudent(id);
        response.sendRedirect("/Success.jsp");
    }

    private void showStudent(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            pw.println("Student: " + studentRepository.getStudent(getValidId(request)).getName() + "  - University: " +
                    studentRepository.getStudent(getValidId(request)).getUniversity().getName());
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAllStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> students = studentRepository.getAllStudents();

        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            for (Student student : students) {
                pw.print(student.getName() + " | ");
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void allStudentsWithUni(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> students = studentRepository.getAllStudentsWithUniversity();

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
        Student student = studentRepository.getStudent(studentId);
        Subject subject = subjectRepository.getSubject(subjectId);
        studentRepository.addSubjectForStudent(student, subject);
        response.sendRedirect("/Success.jsp");
    }

    private void getSubjectsForStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Subject> subjects = new ArrayList<>();
        int studentId = getValidId(request);
        Student student = studentRepository.getStudent(studentId);
        subjects = studentRepository.getSubjectsForStudent(studentId);
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
        Student student = studentRepository.getStudent(studentId);
        Subject subject = subjectRepository.getSubject(subjectId);
        studentRepository.deleteSubjectForStudent(student, subject);

        response.sendRedirect("/Success.jsp");
    }

    private int getValidId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("student_id"));
        return Integer.parseInt(paramId);
    }

}
