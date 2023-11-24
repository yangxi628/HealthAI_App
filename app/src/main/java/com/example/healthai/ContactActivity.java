package com.example.healthai;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;



public class ContactActivity extends AppCompatActivity {
    private ImageView BackButton;
    private Button homeButton;
    private Button aiChatButton;
    private Button contactButton;
    private Button profileButton;
    private  Button gpContactButton;
    private  Button insuranceContactButton;





    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);

        homeButton = findViewById(R.id.homeButton);
        aiChatButton = findViewById(R.id.aiChatButton);
        contactButton = findViewById(R.id.contactButton);
        profileButton = findViewById(R.id.profileButton);
        BackButton = findViewById(R.id.BackButton);
        gpContactButton = findViewById(R.id.gpContactButton);
        insuranceContactButton = findViewById(R.id.insuranceContactButton);

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

                if (!role.equals("patient")) {
                    gpContactButton.setEnabled(false);
                }

            } else {
                Toast.makeText(ContactActivity.this, "Profile doesn't exist", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(ContactActivity.this, "access failed", Toast.LENGTH_SHORT).show();
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Finish the activity to navigate back
            }
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
                } else if (v.getId() == R.id.gpContactButton) {
                    switchToActivity(ProfileActivity.class);
                    Intent gpIntent = new Intent(Intent.ACTION_DIAL);
                    gpIntent.setData(Uri.parse("tel:0123456789"));
                    startActivity(gpIntent);
                } else if (v.getId() == R.id.insuranceContactButton) {
                    Intent insuranceIntent = new Intent(Intent.ACTION_DIAL);
                    insuranceIntent.setData(Uri.parse("tel:0987654321"));
                    startActivity(insuranceIntent);
                }
            }
        };

// Assign the common click listener to all buttons
        homeButton.setOnClickListener(buttonClickListener);
        aiChatButton.setOnClickListener(buttonClickListener);
        contactButton.setOnClickListener(buttonClickListener);
        profileButton.setOnClickListener(buttonClickListener);

        gpContactButton.setOnClickListener(buttonClickListener);
        insuranceContactButton.setOnClickListener(buttonClickListener);


    }

    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
// luke