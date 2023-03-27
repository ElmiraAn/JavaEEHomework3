package com.elmira.aston.homework3.data;

import com.elmira.aston.homework3.model.Student;
import com.elmira.aston.homework3.model.Subject;

import java.util.ArrayList;
import java.util.List;

import static com.elmira.aston.homework3.data.SubjectDataTest.*;
import static com.elmira.aston.homework3.data.UniversityDataTest.*;

public class StudentDataTest {
    public static final Student ADAM = new Student("Adam", Oxford);
    public static final Student ANDY = new Student("Andy", Harvard);
    public static final Student JOE = new Student("Joe", Stanford);
    public static final Student SANDY = new Student("Sandy", Cambridge);
    public static final Student REBEKA = new Student("Rebeka", Oxford);
    public static final Student MAKSIM = new Student("Maksim", Harvard);
    public static final Student CHENDLER = new Student("Chendler", Stanford);
    public static final Student MONIKA = new Student("Monika", Cambridge);


    public static List<Student> students = new ArrayList<>();

    public static List<Subject> subjectsForStudent_1 = new ArrayList<>();

    static {
        students.add(ADAM);
        students.add(ANDY);
        students.add(JOE);
        students.add(SANDY);
        students.add(REBEKA);
        students.add(MAKSIM);
        students.add(CHENDLER);
        students.add(MONIKA);

        subjectsForStudent_1.add(MATHS);
        subjectsForStudent_1.add(PHYSICS);
    }


}
