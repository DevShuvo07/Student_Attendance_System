package com.example.student;

import java.util.ArrayList;
import java.util.List;

public class VariableClass {

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    public String OTP;


    private static VariableClass instance;

    public static VariableClass getInstance() {
        if (instance == null)
            instance = new VariableClass();
        return instance;
    }

    private VariableClass() {
    }

    // Variable data Teacher details......

    public String getTd_name() {
        return td_name;
    }

    public void setTd_name(String td_name) {
        this.td_name = td_name;
    }

    private String td_name;

    public String getTd_code() {
        return td_code;
    }

    public void setTd_code(String td_code) {
        this.td_code = td_code;
    }

    public String getTd_email() {
        return td_email;
    }

    public void setTd_email(String td_email) {
        this.td_email = td_email;
    }

    public String getTd_room() {
        return td_room;
    }

    public void setTd_room(String td_room) {
        this.td_room = td_room;
    }

    private String td_code;
    private String td_email;
    private String td_room;

    private String user_Name;

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getUser_Pass() {
        return user_Pass;
    }

    public void setUser_Pass(String user_Pass) {
        this.user_Pass = user_Pass;
    }

    private String user_Pass;
    private int atten_total;


    public int getAtten_total() {
        return atten_total;
    }

    public void setAtten_total(int atten_total) {
        this.atten_total = atten_total;
    }

    // student detail ID for AD-HOC....


    public List<String> getStudent_id_add_hoc_list() {
        return student_id_add_hoc_list;
    }

    public void setStudent_id_add_hoc_list(List<String> student_id_add_hoc_list) {
        this.student_id_add_hoc_list = student_id_add_hoc_list;
    }

    private List<String> student_id_add_hoc_list;

    public String getStudent_Id() {
        return student_Id;
    }

    public void setStudent_Id(String student_Id) {
        this.student_Id = student_Id;
    }

    private String student_Id;

    public List<String> getStudentId() {
        return StudentId;
    }

    public void setStudentId(List<String> studentId) {
        StudentId = studentId;
    }

    private List<String> StudentId = new ArrayList<String>();


    // getter and setter for student Name & ID details in View_student....

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    private String student_id;

    public String getStd_name() {
        return Std_name;
    }

    public void setStd_name(String std_name) {
        Std_name = std_name;
    }

    private String Std_name;

    public List<String> getSTD_id() {
        return STD_id;
    }

    public void setSTD_id(List<String> STD_id) {
        this.STD_id = STD_id;
    }

    public List<String> getSTD_name() {
        return STD_name;
    }

    public void setSTD_name(List<String> STD_name) {
        this.STD_name = STD_name;
    }

    public List<String> getSTD_ATTEND() {
        return STD_ATTEND;
    }

    public void setSTD_ATTEND(List<String> STD_ATTEND) {
        this.STD_ATTEND = STD_ATTEND;
    }

    private List<String> STD_id = new ArrayList<String>();
    private List<String> STD_name = new ArrayList<String>();
    private  List<String> STD_ATTEND = new ArrayList<>();

    public String getS_no() {
        return s_no;
    }

    public void setS_no(String s_no) {
        this.s_no = s_no;
    }

    public String getS_name() {
        return S_name;
    }

    public void setS_name(String s_name) {
        S_name = s_name;
    }

    private  String s_no, S_name;

    public String getImagestring() {
        return Imagestring;
    }

    public void setImagestring(String imagestring) {
        Imagestring = imagestring;
    }

    private String Imagestring;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    private String domain;


    public static final String URL = "http://192.168.0.20/android_project/login.php";
    public static final String URL1 = "http://192.168.0.20/android_project/All.php";
    public static String getURL1() {
        return URL1;
    }
    public static String getURL() {
        return URL;
    }


}
