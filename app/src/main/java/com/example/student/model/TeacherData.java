package com.example.student.model;

public class TeacherData {

    String name;
    String userName;
    String fCode;
    String email;
    String room;
    String password;
    String OTP;

    public TeacherData(String name, String userName, String fCode, String email, String room, String password, String OTP) {
        this.name = name;
        this.userName = userName;
        this.fCode = fCode;
        this.email = email;
        this.room = room;
        this.password = password;
        this.OTP = OTP;
    }

    public TeacherData() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getfCode() {
        return fCode;
    }

    public void setfCode(String fCode) {
        this.fCode = fCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
}
