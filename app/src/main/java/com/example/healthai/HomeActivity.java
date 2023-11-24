package com.example.healthai;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthai.Models.UserState;


public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        Button homeButton = findViewById(R.id.homeButton);
        Button aiChatButton = findViewById(R.id.aiChatButton);
        Button contactButton = findViewById(R.id.contactButton);
        ImageView profileButton = findViewById(R.id.profileButton);

        Button subscriptionButton = findViewById(R.id.subscriptionButton);
        Button appointmentButton = findViewById(R.id.appointmentButton); // not shown if doctor
        Button historyButton = findViewById(R.id.historyButton); // not shown if doctor

        Button ratingButton = findViewById(R.id.ratingButton);

        UserState userState = UserState.getInstance();
        String CurrentUserNameText = userState.getFirstName() + " " + userState.getLastName();
        String currentUserEmail = userState.getEmail();

        // Find the TextViews by their IDs
        TextView usernameTextView = findViewById(R.id.CurrentUserNameText);
        TextView emailTextView = findViewById(R.id.currentUserEmail);

        // Set the text for the TextViews
        usernameTextView.setText(CurrentUserNameText);
        emailTextView.setText(currentUserEmail);

        String role =  userState.getRole(); // Get the UID of the currently signed-in user
        if (!role.toLowerCase().equals("patient")) {
            appointmentButton.setEnabled(false);
            historyButton.setEnabled(false);
        }

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
