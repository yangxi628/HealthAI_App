package com.example.healthai.Models;

import com.google.firebase.Timestamp;

import java.util.List;

public class Users {
    private String firstname;
    private String lastname;
    private String email;
    private String role;
    private String password;
    private String phone;
    private List<Timeslot> timeslots;
    private String doctor;

    private String insurance;

    public Users() {}

    public Users(String firstname, String lastname, String email, String password, String phone, String role, List<Timeslot> timeslots, String doctor,String insurance) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.password = password;
        this.phone = phone;
        this.timeslots = timeslots;
        this.doctor = doctor;
        this.insurance = insurance;
    }

    public static class Timeslot {
        private String userId;
        private String status;
        private Timestamp time;

        public Timeslot(String userId, String status, Timestamp time) {
            this.userId = userId;
            this.status = status;
            this.time = time;
        }
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}