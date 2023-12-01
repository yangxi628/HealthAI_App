package com.example.healthai.Views;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthai.Models.UserState;
import com.example.healthai.Models.TimeslotUtils;
import com.example.healthai.R;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class AppointmentActivity extends AppCompatActivity {

    private TextView doctorNameText;
    private TextView dateEditText;
    private TextView timeEditText;
    private Button scheduleButton;
    private String doctorid;
    private ImageView BackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment);

        // Initialize UI elements
        doctorNameText = findViewById(R.id.doctorNameText);
        dateEditText = findViewById(R.id.dateEditText);
        timeEditText = findViewById(R.id.timeEditText);
        scheduleButton = findViewById(R.id.scheduleButton);
        BackButton = findViewById(R.id.BackButton);

        // Fetch and display doctor's name
        fetchAndDisplayDoctorName();
        ;       scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = dateEditText.getText().toString();
                String time = timeEditText.getText().toString();
                scheduleAppointment(date,time);
            }
        });

        // Set up click listeners for dateEditText and timeEditText
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
                timeEditText.setText(null);
            }
        });

        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if a date is selected before showing the time picker
                if (!TextUtils.isEmpty(dateEditText.getText().toString())) {
                    showAvailableTimeSlots(dateEditText.getText().toString());
                } else {
                    Toast.makeText(AppointmentActivity.this, "Please select a date first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.BackButton) {
                    finish();
                }
                else if (v.getId() == R.id.scheduleButton) {
                    String date = dateEditText.getText().toString();
                    String time = timeEditText.getText().toString();
                    scheduleAppointment(date,time);
                }
            }
        };

        BackButton.setOnClickListener(buttonClickListener);
        scheduleButton.setOnClickListener(buttonClickListener);
    }

    private void fetchAndDisplayDoctorName() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid(); // Get the UID of the currently signed-in user
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference doctorsCollection = db.collection("users");
        doctorsCollection.document(userid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        doctorid = documentSnapshot.getString("doctor");
                        TimeslotUtils.cleanUpOutdatedAndExcessTimeslots(doctorid);
                        doctorsCollection.document(doctorid).get()
                                .addOnSuccessListener(documentSnapshots -> {
                                    if (documentSnapshots.exists()) {
                                        String doctorfirstName = documentSnapshots.getString("firstName");
                                        String doctorlastName = documentSnapshots.getString("lastName");
                                        doctorNameText.setText(doctorfirstName+" "+doctorlastName);

                                    }
                                })
                                .addOnFailureListener(e -> {
                                    // Handle the failure to fetch doctor's name
                                    Log.e("FirestoreError", "Error fetching doctor's name: " + e.getMessage());
                                });

                    }
                })
                .addOnFailureListener(e -> {
                    // Handle the failure to fetch doctor's name
                    Log.e("FirestoreError", "Error fetching doctor's name: " + e.getMessage());
                });
    }

    private void showDatePickerDialog() {
        // Get the current date
        Calendar currentDate = Calendar.getInstance();

        // Create a new instance of DatePickerDialog with a date range of the next five days
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Format the selected date and set it to dateEditText
                        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                        dateEditText.setText(selectedDate);
                    }
                },
                currentDate.get(Calendar.YEAR),
                currentDate.get(Calendar.MONTH),
                currentDate.get(Calendar.DAY_OF_MONTH));

        // Set the minimum date to the current date
        currentDate.add(Calendar.DATE, 1);
        datePickerDialog.getDatePicker().setMinDate(currentDate.getTimeInMillis());

        // Set the maximum date to five days in the future
        currentDate.add(Calendar.DATE, 4);
        datePickerDialog.getDatePicker().setMaxDate(currentDate.getTimeInMillis());

        // Show the date picker dialog
        datePickerDialog.show();
    }


    private void showAvailableTimeSlots(String selectedDate) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("users");

        usersCollection.document(doctorid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    List<String> availableTimeSlots = new ArrayList<>();

                    if (documentSnapshot.exists()) {
                        // Retrieve the "timeslots" field as a List<Map<String, Object>>
                        List<Map<String, Object>> timeslots = (List<Map<String, Object>>) documentSnapshot.get("timeslots");
                        Log.d("FirestoreDebug", "Timeslots for doctor: " + timeslots);
                        if (timeslots != null) {
                            for (Map<String, Object> timeslot : timeslots) {
                                // Extract status, time, and userId from the map
                                String status = (String) timeslot.get("status");
                                Timestamp timestamp = (Timestamp) timeslot.get("time");

                                if (timestamp != null) {
                                    Date date = timestamp.toDate();
                                    String formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);

                                    // Check if the timeslot date matches the selected date and the status is available
                                    if (formattedDate.equals(selectedDate) && "available".equals(status)) {
                                        // Add the available time slot to the list
                                        availableTimeSlots.add(new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(date));
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(AppointmentActivity.this, "Timeslots didn't exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AppointmentActivity.this, "Doctor document not found", Toast.LENGTH_SHORT).show();
                    }

                    // Display available time slots to the user
                    if (!availableTimeSlots.isEmpty()) {
                        String[] timeSlotsArray = availableTimeSlots.toArray(new String[0]);

                        AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentActivity.this);
                        builder.setTitle("Choose a time slot")
                                .setItems(timeSlotsArray, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Set the selected time slot to the timeEditText
                                        timeEditText.setText(timeSlotsArray[which]);
                                    }
                                })
                                .show();
                    } else {
                        Toast.makeText(AppointmentActivity.this, "No available time slots for selected date", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle the failure to fetch data from Firestore
                    Log.e("FirestoreError", "Error fetching data from Firestore: " + e.getMessage());
                });
    }

    private void scheduleAppointment(String selectedDate, String selectedTime) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersCollection = db.collection("users");

        // Fetch the timeslots for the selected doctor on the selected date
        usersCollection.document(doctorid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Retrieve the "timeslots" field as a List<Map<String, Object>>
                        List<Map<String, Object>> timeslots = (List<Map<String, Object>>) documentSnapshot.get("timeslots");

                        if (timeslots != null) {
                            for (Map<String, Object> timeslot : timeslots) {
                                // Extract time, status, and userId from the map
                                Timestamp timestamp = (Timestamp) timeslot.get("time");
                                String status = (String) timeslot.get("status");

                                if (timestamp != null) {
                                    Date date = timestamp.toDate();
                                    String formattedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date);
                                    String formattedTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(date);

                                    // Check if the timeslot date and time match the selected date and time
                                    if (formattedDate.equals(selectedDate) && formattedTime.equals(selectedTime)) {
                                        // Update the timeslot with the user information and set status to "unavailable"
                                        timeslot.put("userId", getUserId());
                                        timeslot.put("status", "unavailable");

                                        // Update the document in Firestore
                                        usersCollection.document(doctorid).update("timeslots", timeslots)
                                                .addOnSuccessListener(aVoid -> {
                                                    // Successful update
                                                    Toast.makeText(AppointmentActivity.this, "Appointment scheduled successfully", Toast.LENGTH_SHORT).show();
                                                })
                                                .addOnFailureListener(e -> {
                                                    // Handle the failure to update data in Firestore
                                                    Log.e("FirestoreError", "Error updating data in Firestore: " + e.getMessage());
                                                });
                                        return; // Stop further iteration once the timeslot is found
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(AppointmentActivity.this, "Timeslots didn't exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AppointmentActivity.this, "Doctor document not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle the failure to fetch data from Firestore
                    Log.e("FirestoreError", "Error fetching data from Firestore: " + e.getMessage());
                });
    }

    private String getUserId() {
        UserState userState = UserState.getInstance();
        String currentUserNameText = userState.getFirstName() + " " + userState.getLastName();
        return currentUserNameText;
    }

}