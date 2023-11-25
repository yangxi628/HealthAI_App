package com.example.healthai.Models;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.google.firebase.Timestamp;

public class TimeslotUtils {

    public static void cleanUpOutdatedAndExcessTimeslots(String doctorId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("users");

        // Fetch the timeslots for the selected doctor
        usersCollection.document(doctorId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Map<String, Object>> timeslots = (List<Map<String, Object>>) documentSnapshot.get("timeslots");

                        if (timeslots != null) {
                            cleanUpTimeslots(timeslots);
                            updateTimeslotsInFirestore(doctorId, timeslots);
                        }
                    }
                })
                .addOnFailureListener(e -> {

                    Log.e("FirestoreError", "Error fetching data from Firestore: " + e.getMessage());
                });
    }

    private static void cleanUpTimeslots(List<Map<String, Object>> timeslots) {
        Calendar currentCalendar = Calendar.getInstance();

        // Remove outdated timeslots (before the current date)
        timeslots.removeIf(timeslot -> {
            Timestamp timestamp = (Timestamp) timeslot.get("time");
            if (timestamp != null) {
                Date date = timestamp.toDate();
                return date.before(currentCalendar.getTime());
            }
            return false;
        });

        // Add new timeslots for the next five days
        for (int i = 0; i < 5; i++) {
            currentCalendar.add(Calendar.DATE, 1);
            Date nextDate = currentCalendar.getTime();
            Map<String, Object> newTimeslot = createTimeslot(nextDate);
            timeslots.add(newTimeslot);
        }
    }

    private static Map<String, Object> createTimeslot(Date date) {
        // Create a new timeslot map with default values
        Map<String, Object> newTimeslot = new HashMap<>();
        newTimeslot.put("status", "available");
        newTimeslot.put("time", new Timestamp(date));
        newTimeslot.put("userId", null);
        return newTimeslot;
    }

    private static void updateTimeslotsInFirestore(String doctorId, List<Map<String, Object>> timeslots) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("users");

        // Update the document in Firestore with the modified timeslots
        usersCollection.document(doctorId).update("timeslots", timeslots)
                .addOnSuccessListener(aVoid -> {

                    Log.d("FirestoreDebug", "Timeslots updated successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreError", "Error updating data in Firestore: " + e.getMessage());
                });
    }
}

