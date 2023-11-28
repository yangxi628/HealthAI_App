package com.example.healthai.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.healthai.Models.UserState;
import com.example.healthai.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        ImageView backButton = findViewById(R.id.BackButton);
        Button signoutButton = findViewById(R.id.signoutButton);
        Button ratingButton = findViewById(R.id.RateHealthAI);
        Button subscriptionButton = findViewById(R.id.PaySubscription);
        // Access UserState in another part of the app
        UserState userState = UserState.getInstance();
        String currentUserNameText = userState.getFirstName() + " " + userState.getLastName();
        String currentUserEmail = userState.getEmail();

        // Find the TextViews by their IDs
        TextView usernameTextView = findViewById(R.id.CurrentUserNameText);
        TextView emailTextView = findViewById(R.id.currentUserEmail);

        // Set the text for the TextViews
        usernameTextView.setText(currentUserNameText);
        emailTextView.setText(currentUserEmail);

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.BackButton) {
                    switchToActivity(NavigationActivity.class);
                } else if (v.getId() == R.id.signoutButton) {
                    FirebaseAuth.getInstance().signOut();
                    switchToActivity(MainActivity.class);
                }
                else if (v.getId() == R.id.RateHealthAI) {
                    FirebaseAuth.getInstance().signOut();
                    switchToActivity(RatingActivity.class);
                }
                else if (v.getId() == R.id.PaySubscription) {
                    FirebaseAuth.getInstance().signOut();
                    switchToActivity(SubscriptionActivity.class);
                }
            }
        };

        // Set the click listeners for the buttons
        backButton.setOnClickListener(buttonClickListener);
        signoutButton.setOnClickListener(buttonClickListener);
        subscriptionButton.setOnClickListener(buttonClickListener);
        ratingButton.setOnClickListener(buttonClickListener);
    }

    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
