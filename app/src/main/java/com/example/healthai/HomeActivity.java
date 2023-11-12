package com.example.healthai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        Button homeButton = findViewById(R.id.homeButton);
        Button aiChatButton = findViewById(R.id.aiChatButton);
        Button contactButton = findViewById(R.id.contactButton);
        Button profileButton = findViewById(R.id.profileButton);

        Button subscriptionButton = findViewById(R.id.subscriptionButton);

        Button appointmentButton = findViewById(R.id.appointmentButton); // not shown if doctor
        Button historyButton = findViewById(R.id.historyButton); // not shown if doctor

        Button ratingButton = findViewById(R.id.ratingButton);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid(); // Get the UID of the currently signed-in user

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userDocRef = db.collection("users").document(userid); // Reference to the user's document

        userDocRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String role = documentSnapshot.getString("role");

                if (role != null){
                    role = role.toLowerCase();
                }

                if (role != "patient") {
                    appointmentButton.setEnabled(false);
                    historyButton.setEnabled(false);

                }
            } else {
                Toast.makeText(HomeActivity.this, "Profile doesn't exist", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(HomeActivity.this, "access failed", Toast.LENGTH_SHORT).show();
        });

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.homeButton) {
                    switchToActivity(HomeActivity.class);
                } else if (v.getId() == R.id.aiChatButton) {
                    switchToActivity(AiChatActivity.class);
                } else if (v.getId() == R.id.contactButton) {
                    switchToActivity(ContactActivity.class);
                } else if (v.getId() == R.id.profileButton) {
                    switchToActivity(ProfileActivity.class);
                } else if (v.getId() == R.id.subscriptionButton) {
                    switchToActivity(SubscriptionActivity.class);
                } else if (v.getId() == R.id.appointmentButton) {
                    switchToActivity(AppointmentActivity.class);
                } else if (v.getId() == R.id.historyButton) {
                    switchToActivity(HistoryActivity.class);
                } else if (v.getId() == R.id.ratingButton) {
                    switchToActivity(RatingActivity.class);
                }
            }
        };

        // Assign the common click listener to all buttons
        homeButton.setOnClickListener(buttonClickListener);
        aiChatButton.setOnClickListener(buttonClickListener);
        contactButton.setOnClickListener(buttonClickListener);
        profileButton.setOnClickListener(buttonClickListener);
        subscriptionButton.setOnClickListener(buttonClickListener);
        appointmentButton.setOnClickListener(buttonClickListener);
        historyButton.setOnClickListener(buttonClickListener);
        ratingButton.setOnClickListener(buttonClickListener);
    }

    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
