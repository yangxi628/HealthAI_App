package com.example.healthai.Controllers;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthai.Models.UserState;
import com.example.healthai.R;
import com.example.healthai.Views.LoginActivity;
import com.example.healthai.Views.NavigationBar;
import com.example.healthai.Views.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginController {

    private final LoginActivity view;
    private final FirebaseAuth mAuth;

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView signUpText;

    public LoginController(LoginActivity view) {
        this.view = view;
        this.mAuth = FirebaseAuth.getInstance();
        initialize();
    }

    private void initialize() {
        view.setContentView(R.layout.activity_login);
        initializeUiElements();
        loginButton.setOnClickListener(v -> attemptLogin());
        signUpText.setOnClickListener(v -> navigateToRegisterActivity());
    }

    private void initializeUiElements() {
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        loginButton = view.findViewById(R.id.loginButton);
        signUpText = view.findViewById(R.id.signUpTextView);
    }

    private void attemptLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showErrorMessage("Please fill in both email and password fields.");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(view, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        showSuccessMessage("Login successful");

                        if (user != null) {
                            getUserDetailsFromFirestore(user.getUid());
                        }
                    } else {
                        showErrorMessage("Login failed. Please check your credentials.");
                    }
                });
    }

    private void getUserDetailsFromFirestore(String uid) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        DocumentReference userRef = firestore.collection("users").document(uid);
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null && document.exists()) {
                    handleUserDetails(document, uid);
                } else {
                    showErrorMessage("User details not found.");
                }
            } else {
                showErrorMessage("Error fetching user details.");
            }
        });
    }

    private void handleUserDetails(DocumentSnapshot document, String uid) {
        UserState userState = UserState.getInstance();
        userState.setUserStateFromDocument(document, uid);
        navigateToNavigationBar();
    }

    private void navigateToNavigationBar() {
        Intent intent = new Intent(view, NavigationBar.class);
        view.startActivity(intent);
    }

    private void navigateToRegisterActivity() {
        Intent intent = new Intent(view, RegisterActivity.class);
        view.startActivity(intent);
    }

    private void showSuccessMessage(String message) {
        Toast.makeText(view, message, Toast.LENGTH_SHORT).show();
    }

    private void showErrorMessage(String message) {
        Toast.makeText(view, message, Toast.LENGTH_SHORT).show();
    }
}

