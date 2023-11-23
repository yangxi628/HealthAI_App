package com.example.healthai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RatingActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private static final String COLLECTION_NAME = "feedback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating);

        firestore = FirebaseFirestore.getInstance();

        Button homeButton = findViewById(R.id.homeButton);
        Button aiChatButton = findViewById(R.id.aiChatButton);
        Button contactButton = findViewById(R.id.contactButton);
        Button profileButton = findViewById(R.id.profileButton);
        Button ratingButton = findViewById(R.id.buttonRating);
        Button backButton = findViewById(R.id.BackButton);

        SeekBar ratingSeekBar = findViewById(R.id.ratingSeekBar);
        TextView selectedRatingText = findViewById(R.id.selectedRatingText);
        EditText feedbackEditText = findViewById(R.id.feedbackEditText);

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
                } else if (v.getId() == R.id.buttonRating) {
                    // Assuming you have the rating obtained from the SeekBar
                    int rating = ratingSeekBar.getProgress() + 1;

                    // Get the feedback message from the EditText
                    String feedbackMessage = feedbackEditText.getText().toString();

                    // Store the rating, feedback, and user's name in Firestore
                    storeFeedback(rating, feedbackMessage);
                } else if (v.getId() == R.id.BackButton) {
                    // Handle the back button click
                    onBackPressed();
                }
            }
        };

        // Assign the common click listener to all buttons
        homeButton.setOnClickListener(buttonClickListener);
        aiChatButton.setOnClickListener(buttonClickListener);
        contactButton.setOnClickListener(buttonClickListener);
        profileButton.setOnClickListener(buttonClickListener);
        ratingButton.setOnClickListener(buttonClickListener);
        backButton.setOnClickListener(buttonClickListener);

        ratingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Update the TextView with the current progress
                selectedRatingText.setText(String.valueOf(progress + 1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this example
            }
        });
    }

    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    private void storeFeedback(int rating, String message) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (user != null) {
            String userId = user.getUid();

            DocumentReference userDocRef = db.collection("users").document(userId);

            userDocRef.get().addOnSuccessListener(documentSnapshot -> {
                String firstName = documentSnapshot.getString("firstName");
                String lastName = documentSnapshot.getString("lastName");

                // Create a map with the data to be stored
                Map<String, Object> feedbackData = new HashMap<>();
                feedbackData.put("userName", firstName + " " + lastName);
                feedbackData.put("rating", rating);
                feedbackData.put("message", message);

                // Add the feedback to the "feedback" collection in Firestore
                db.collection("feedback")
                        .add(feedbackData)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(this, "Feedback stored successfully", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Error storing feedback: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Error fetching user document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "Error: User is not signed in", Toast.LENGTH_SHORT).show();
        }
    }

    // Override the onBackPressed method to handle back button press
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // You can add additional behavior here if needed
    }
}
