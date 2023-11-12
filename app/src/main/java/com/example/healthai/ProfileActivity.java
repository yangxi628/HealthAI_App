package com.example.healthai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userid = user.getUid(); // Get the UID of the currently signed-in user

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userDocRef = db.collection("users").document(userid); // Reference to the user's document

        userDocRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String firstName = documentSnapshot.getString("firstname");
                String lastName = documentSnapshot.getString("lastname");

                if (firstName != null) {
                    TextView userTextView = findViewById(R.id.name);
                    userTextView.setText(firstName+" "+lastName);

                }
            } else {
                Toast.makeText(ProfileActivity.this, "Profile doesn't exist", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(ProfileActivity.this, "access failed", Toast.LENGTH_SHORT).show();
        });






        Button homeButton = findViewById(R.id.homeButton);
        Button aiChatButton = findViewById(R.id.aiChatButton);
        Button contactButton = findViewById(R.id.contactButton);
        Button profileButton = findViewById(R.id.profileButton);

        Button signoutButton = findViewById(R.id.signoutButton);


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
                } else if (v.getId() == R.id.signoutButton) {

                    FirebaseAuth.getInstance().signOut();
                    switchToActivity(MainActivity.class);
                }
            }
        };

        // Assign the common click listener to all buttons
        homeButton.setOnClickListener(buttonClickListener);
        aiChatButton.setOnClickListener(buttonClickListener);
        contactButton.setOnClickListener(buttonClickListener);
        profileButton.setOnClickListener(buttonClickListener);

        signoutButton.setOnClickListener(buttonClickListener);

    }

    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
    }
// luke