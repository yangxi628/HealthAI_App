package com.example.healthai.Controllers;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthai.Controllers.Helpers.SwitchViewHelper;
import com.example.healthai.Models.UserState;
import com.example.healthai.R;
import com.example.healthai.Views.LoginActivity;
import com.example.healthai.Views.NavigationBar;
import com.example.healthai.Views.ProfileActivity;
import com.example.healthai.Views.RatingActivity;
import com.example.healthai.Views.SubscriptionActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileController {

    private final ProfileActivity view;

    public ProfileController(ProfileActivity view) {
        this.view = view;
        initialize();
    }

    private void initialize() {
        view.setContentView(R.layout.profile);

        ImageView backButton = view.findViewById(R.id.BackButton);
        Button signoutButton = view.findViewById(R.id.signoutButton);
        Button ratingButton = view.findViewById(R.id.RateHealthAI);
        Button subscriptionButton = view.findViewById(R.id.PaySubscription);

        setupUserInfo();

        View.OnClickListener buttonClickListener = this::handleButtonClick;

        backButton.setOnClickListener(buttonClickListener);
        signoutButton.setOnClickListener(buttonClickListener);
        subscriptionButton.setOnClickListener(buttonClickListener);
        ratingButton.setOnClickListener(buttonClickListener);
    }

    private void setupUserInfo() {
        UserState userState = UserState.getInstance();
        String currentUserFullName = userState.getFirstName() + " " + userState.getLastName();
        String currentUserEmail = userState.getEmail();

        TextView usernameTextView = view.findViewById(R.id.CurrentUserNameText);
        TextView emailTextView = view.findViewById(R.id.currentUserEmail);

        usernameTextView.setText(currentUserFullName);
        emailTextView.setText(currentUserEmail);
    }

    private void handleButtonClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.BackButton) {
            SwitchViewHelper.switchToActivity(view, NavigationBar.class);
        } else if (viewId == R.id.signoutButton) {
            FirebaseAuth.getInstance().signOut();
            SwitchViewHelper.switchToActivity(view, LoginActivity.class);
        } else if (viewId == R.id.RateHealthAI) {
            SwitchViewHelper.switchToActivity(view, RatingActivity.class);
        } else if (viewId == R.id.PaySubscription) {
            SwitchViewHelper.switchToActivity(view, SubscriptionActivity.class);
        }
    }

}
