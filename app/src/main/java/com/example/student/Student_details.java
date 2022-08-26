package com.example.student;

import java.util.ArrayList;

class Student_details {

    private static Student_details instance;

    public static Student_details getInstance() {
        if (instance == null)
            instance = new Student_details();
        return instance;
    }

    private Student_details() {
    }

    private String Student_id1;

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getIntake() {
        return intake;
    }

    public void setIntake(String intake) {
        this.intake = intake;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDEPT() {
        return DEPT;
    }

    public void setDEPT(String DEPT) {
        this.DEPT = DEPT;
    }

    private String DEPT;
    private String program;
    private String intake;
    private String section;



    public Student_details(String student_id1) {
        Student_id1 = student_id1;
    }
    public String getStudent_id1() {
        return Student_id1;
    }

    public void setStudent_id1(String student_id1) {
        Student_id1 = student_id1;
    }

    public ArrayList<String> getStudentID() {
        return StudentID;
    }

    public void setStudentID(ArrayList<String> studentID) {
        StudentID = studentID;
    }

    private ArrayList<String> StudentID = new ArrayList<>();




}
