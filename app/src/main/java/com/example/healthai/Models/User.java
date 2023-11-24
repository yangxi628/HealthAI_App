package com.example.healthai.Models;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String doctor;
    private String profileImg;
    private String role;

    public User(String firstName, String lastName, String email, String doctor, String profileImg, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.doctor = doctor;
        this.profileImg = profileImg;
        this.role = role;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
