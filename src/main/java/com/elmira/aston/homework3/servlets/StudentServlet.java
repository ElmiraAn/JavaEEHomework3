package com.elmira.aston.homework3.servlets;

import com.elmira.aston.homework3.model.Student;
import com.elmira.aston.homework3.model.Subject;
import com.elmira.aston.homework3.model.University;
import com.elmira.aston.homework3.repository.*;
import com.elmira.aston.homework3.service.*;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

@WebServlet(urlPatterns = {"/student/add", "/student/update", "/student/delete",
        "/student/get-all", "/student/get", "/student/get-with-uni", "/student/get-subjects"})
public class StudentServlet extends HttpServlet {
    //private static final long serialVersionUID = 1 L;
    private StudentRepository studentRepository;
    private UniversityRepository universityRepository;

    public StudentServlet() {
    }

    public StudentServlet(StudentRepository studentRepository, UniversityRepository universityRepository) {
        this.studentRepository = studentRepository;
        this.universityRepository = universityRepository;
    }

    @Override
    public void init() throws ServletException {
        this.studentRepository = new StudentService("mysql");
        this.universityRepository = new UniversityService("mysql");
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
            case "/student/get-subjects":
                getStudentWithSubjects(request, response);
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

        //response.sendRedirect("/all-students.jsp");

    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = getValidId(request);
        int uniId = Integer.parseInt(request.getParameter("university_id"));
        University university = universityRepository.getUniversity(uniId);
        String name = request.getParameter("student_name");
        Student student = new Student(id, name, university);
        studentRepository.updateStudent(student);
        //response.sendRedirect("/bookList.jsp");
        response.sendRedirect("/Success.jsp");
        //response.sendRedirect("/all-students.jsp");
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = getValidId(request);
        studentRepository.deleteStudent(id);
        response.sendRedirect("/Success.jsp");
        //response.sendRedirect("all-students.jsp");
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

    private void showAllStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        List<Student> students = studentRepository.getAllStudents();
        //RequestDispatcher dispatcher = request.getRequestDispatcher("all-student-no-uni.jsp");

       // request.setAttribute("studentsNoUni", students);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            for(Student student : students) {
                pw.print(student.getName() + " | ");
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //RequestDispatcher dispatcher = request.getRequestDispatcher("all-student-no-uni.jsp");
        //dispatcher.forward(request, response);
        //response.sendRedirect("all-student-no-uni.jsp");
    }

    private void allStudentsWithUni(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Student> students = studentRepository.getAllStudentsWithUniversity();
        //RequestDispatcher dispatcher = request.getRequestDispatcher("all-students.jsp");

        request.setAttribute("students", students);
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            for(Student student : students) {
                pw.println(student.getName() + " - Университет: " + student.getUniversity().getName() + " | ");

            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getStudentWithSubjects(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            Student student = studentRepository.getStudentWithSubjects(getValidId(request));
            List<Subject> subjects = student.getSubjects();
            pw.println(student.getName() + ": ");
            for (Subject subject : subjects) {
                pw.print(subject.getName() + ", ");
            }
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int getValidId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("student_id"));
        return Integer.parseInt(paramId);
    }


}
