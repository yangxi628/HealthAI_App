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

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

}
