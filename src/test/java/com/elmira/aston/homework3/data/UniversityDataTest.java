package com.elmira.aston.homework3.data;

import com.elmira.aston.homework3.model.University;

import java.util.ArrayList;
import java.util.List;

public class UniversityDataTest {
    public static final University Oxford = new University(1, "Oxford");
    public static final University Harvard = new University(2, "Harvard");
    public static final University Stanford = new University(3, "Stanford");
    public static final University Cambridge = new University(4, "Cambridge");
    public static final University Yale = new University(5, "Yale");

    public static final List<University> UNIVERSITIES = new ArrayList<>();

    static {
        UNIVERSITIES.add(Oxford);
        UNIVERSITIES.add(Harvard);
        UNIVERSITIES.add(Stanford);
        UNIVERSITIES.add(Cambridge);
    }


}
