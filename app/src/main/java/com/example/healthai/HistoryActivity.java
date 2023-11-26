package com.example.healthai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthai.Models.MedicalHistory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    private EditText editTextBP, editTextChestPainType, editTextCholesterol,
            editTextEKGResults, editTextExerciseAngina, editTextFBSOver120,
            editTextMaxHR, editTextNumberOfVesselsFluro, editTextSTDepression,
            editTextSlopeOfST, editTextThallium, editTextAge, editTextDate,
            editTextPatient, editTextResult, editTextSex, editTextType;

    private Button buttonAddMedicalHistory,homeButton,aiChatButton,contactButton;
    private ImageView BackButton;

    // Reference to Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference medicalHistoryCollection = db.collection("reports");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        // Initialize Views
        editTextBP = findViewById(R.id.editTextBP);
        editTextChestPainType = findViewById(R.id.editTextChestPainType);
        editTextCholesterol = findViewById(R.id.editTextCholesterol);
        editTextEKGResults = findViewById(R.id.editTextEKGResults);
        editTextExerciseAngina = findViewById(R.id.editTextExerciseAngina);
        editTextFBSOver120 = findViewById(R.id.editTextFBSOver120);
        editTextMaxHR = findViewById(R.id.editTextMaxHR);
        editTextNumberOfVesselsFluro = findViewById(R.id.editTextNumberOfVesselsFluro);
        editTextSTDepression = findViewById(R.id.editTextSTDepression);
        editTextSlopeOfST = findViewById(R.id.editTextSlopeOfST);
        editTextThallium = findViewById(R.id.editTextThallium);
        editTextAge = findViewById(R.id.editTextAge);
        editTextResult = findViewById(R.id.editTextResult);
        editTextSex = findViewById(R.id.editTextSex);
        editTextType = findViewById(R.id.editTextType);
        homeButton = findViewById(R.id.homeButton);
        aiChatButton = findViewById(R.id.aiChatButton);
        contactButton = findViewById(R.id.contactButton);
        BackButton = findViewById(R.id.BackButton);
        buttonAddMedicalHistory = findViewById(R.id.buttonAddMedicalHistory);


        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.homeButton) {
                    switchToActivity(HomeActivity.class);
                } else if (v.getId() == R.id.aiChatButton) {
                    switchToActivity(AiChatActivity.class);
                } else if (v.getId() == R.id.contactButton) {
                    switchToActivity(ContactActivity.class);
                } else if (v.getId() == R.id.BackButton) {
                    finish();
                } else if (v.getId() == R.id.buttonAddMedicalHistory) {
                    addMedicalHistory();
                    finish();
                }
            }
        };
        // Set click listener for Add Medical History Button
        homeButton.setOnClickListener(buttonClickListener);
        aiChatButton.setOnClickListener(buttonClickListener);
        contactButton.setOnClickListener(buttonClickListener);
        BackButton.setOnClickListener(buttonClickListener);
        buttonAddMedicalHistory.setOnClickListener(buttonClickListener);
    }

    private void addMedicalHistory() {
        // Get values from EditText fields
        String bp = editTextBP.getText().toString();
        String chestPainType = editTextChestPainType.getText().toString();
        String cholesterol = editTextCholesterol.getText().toString();
        String ekgResults = editTextEKGResults.getText().toString();
        String exerciseAngina = editTextExerciseAngina.getText().toString();
        String fbsOver120 = editTextFBSOver120.getText().toString();
        String maxHR = editTextMaxHR.getText().toString();
        String numberOfVesselsFluro = editTextNumberOfVesselsFluro.getText().toString();
        String stDepression = editTextSTDepression.getText().toString();
        String slopeOfST = editTextSlopeOfST.getText().toString();
        String thallium = editTextThallium.getText().toString();
        String age = editTextAge.getText().toString();

        // Set the date to the current date
        String date = getCurrentDate();

        // Get the current user's UID
        String patient = getCurrentUserUid();

        String result = editTextResult.getText().toString();
        String sex = editTextSex.getText().toString();
        String type = editTextType.getText().toString();

        // Create a new MedicalHistory object
        MedicalHistory medicalHistory = new MedicalHistory(bp, chestPainType, cholesterol,
                ekgResults, exerciseAngina, fbsOver120, maxHR, numberOfVesselsFluro,
                stDepression, slopeOfST, thallium, age, date, patient, result, sex, type);

        // Convert MedicalHistory object to a Map
        Map<String, Object> medicalHistoryMap = medicalHistory.toMap();

        // Add the medical history to Firestore
        medicalHistoryCollection.add(medicalHistoryMap)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(HistoryActivity.this, "Medical history added successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(HistoryActivity.this, "Error adding medical history", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    private String getCurrentUserUid() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getUid();
        } else {
            // Handle the case where the user is not logged in
            return "";
        }
    }
    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
