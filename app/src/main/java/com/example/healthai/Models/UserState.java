package com.example.healthai.Models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserState {

    private static UserState instance;

    private String userID;
    private String firstName;
    private String lastName;
    private String doctor;
    private String email;
    private String profileImg;
    private String role;
    private List<Users.Timeslot> timeslot;  // Added timeslot attribute

    private UserState() {
        // Private constructor to prevent instantiation outside of the class
        this.userID = "";
        this.firstName = "";
        this.lastName = "";
        this.doctor = "";
        this.email = "";
        this.profileImg = "";
        this.role = "";
        this.timeslot = null;  // Initializing timeslot to null
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

            // Extract timeslots from document
            List<Users.Timeslot> timeslots = (List<Users.Timeslot>) document.get("timeslots");
            this.timeslot = timeslots;
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

    // Getter and setter for timeslot
    public List<Users.Timeslot> getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(List<Users.Timeslot> timeslot) {
        this.timeslot = timeslot;
    }

    // Function to get the current user's timeslot from Firebase
    public List<Users.Timeslot> createnewTimeslot() {
        List<Users.Timeslot> timeslots = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            // Get the current timestamp
            Timestamp nextDayTimestamp = Timestamp.now();

            // Convert Timestamp to Date
            Date nextDayDate = nextDayTimestamp.toDate();

            // Use Calendar to add a day to the date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nextDayDate);
            calendar.add(Calendar.DAY_OF_MONTH, i);

            for (int j = 9; j <= 16; j++) {
                // Set the current hour
                calendar.set(Calendar.HOUR_OF_DAY, j);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);

                // Get the Date object representing the current time
                Date currentTime = calendar.getTime();

                // Create a new Timestamp object
                Timestamp currentTimestamp = new Timestamp(currentTime);


                // Create the Timeslot object
                timeslots.add(new Users.Timeslot(null, "available", currentTimestamp));
            }
        }
        return timeslots;
    }


}