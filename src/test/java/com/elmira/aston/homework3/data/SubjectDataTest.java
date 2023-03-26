package com.elmira.aston.homework3.data;

import com.elmira.aston.homework3.model.Student;
import com.elmira.aston.homework3.model.Subject;

import java.util.ArrayList;
import java.util.List;

import static com.elmira.aston.homework3.data.StudentDataTest.*;

public class SubjectDataTest {

    public static final Subject MATHS = new Subject("Maths");
    public static final Subject HISTORY = new Subject("History");
    public static final Subject BIOLOGY = new Subject("Biology");
    public static final Subject ART = new Subject("Art");
    public static final Subject ENERGY = new Subject("Energy");
    public static final Subject CHEMISTRY = new Subject("Chemistry");
    public static final Subject PHYSICS = new Subject("Physics");
    public static final Subject ASTRONOMY = new Subject("Astronomy");
    public static List<Subject> expected_subjects = new ArrayList<>();
    public static List<Student> subjectsFotStudent = new ArrayList<>();

    static {
        expected_subjects.add(MATHS);
        expected_subjects.add(HISTORY);
        expected_subjects.add(BIOLOGY);
        expected_subjects.add(ART);
        expected_subjects.add(ENERGY);
        expected_subjects.add(CHEMISTRY);
        expected_subjects.add(PHYSICS);
        expected_subjects.add(ASTRONOMY);


        subjectsFotStudent.add(ADAM);
        subjectsFotStudent.add(JOE);
        subjectsFotStudent.add(MONIKA);
    }
}
