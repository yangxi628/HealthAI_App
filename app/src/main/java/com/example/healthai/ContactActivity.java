package com.example.healthai;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);

        Button homeButton = findViewById(R.id.homeButton);
        Button aiChatButton = findViewById(R.id.aiChatButton);
        Button contactButton = findViewById(R.id.contactButton);
        Button profileButton = findViewById(R.id.profileButton);

        Button gpContactButton = findViewById(R.id.gpContactButton);
        Button insuranceContactButton = findViewById(R.id.insuranceContactButton);


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