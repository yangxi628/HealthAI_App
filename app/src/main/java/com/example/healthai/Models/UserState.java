package com.example.healthai.Models;

import com.google.firebase.firestore.DocumentSnapshot;

public class UserState {

    private static UserState instance;

    private String userID;
    private String firstName;
    private String lastName;
    private String doctor;
    private String email;
    private String profileImg;
    private String role;

    private UserState() {
        // Private constructor to prevent instantiation outside of the class
        this.userID = "";
        this.firstName = "";
        this.lastName = "";
        this.doctor = "";
        this.email = "";
        this.profileImg = "";
        this.role = "";
    }

    public static synchronized UserState getInstance() {
        if (instance == null) {
            instance = new UserState();
        }
        return instance;
    }

    public void setUserStateFromDocument(DocumentSnapshot document, String uid) {
        if (document.exists()) {
            this.userID = uid;
            this.firstName = document.getString("firstName");
            this.lastName = document.getString("lastName");
            this.doctor = document.getString("doctor");
            this.email = document.getString("email");
            this.profileImg = document.getString("profileImg");
            this.role = document.getString("role");
        }
    }

    // Getter methods for each field
    public String getUserID() {
        return userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getEmail() {
        return email;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public String getRole() {
        return role;
    }

}
