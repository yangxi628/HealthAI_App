package com.example.healthai.Models;

public class User {

    private String firstname;
    private String lastname;
    private String email;
    private String doctor;
    private String profileImg;
    private String role;

    public User(String firstname, String lastname, String email, String doctor, String profileImg, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.doctor = doctor;
        this.profileImg = profileImg;
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
