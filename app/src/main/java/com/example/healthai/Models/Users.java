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

    public Users() {
        // Default constructor required for Firebase
    }

    public Users(String firstname, String lastname, String email, String password, String phone, String role, List<Timeslot> timeslots) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.password = password;
        this.phone = phone;
        this.timeslots = timeslots;
    }

    public List<Timeslot> getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(List<Timeslot> timeslots) {
        this.timeslots = timeslots;
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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Timestamp getTime() {
            return time;
        }

        public void setTime(Timestamp time) {
            this.time = time;
        }
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
