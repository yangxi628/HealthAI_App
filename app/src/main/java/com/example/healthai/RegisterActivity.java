package com.example.healthai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;

    private EditText confirmPasswordEditText;
    private Button RegisterButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        RegisterButton = findViewById(R.id.RegisterButton);

        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    private void attemptRegister() {
        String email = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if(!password.equals(confirmPassword)){
            Toast.makeText(RegisterActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
        }

        else{

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Register successful
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                // Redirect to the Register screen (RegisterActivity)
                                Intent intent = new Intent(RegisterActivity.this, RegisterActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Handle the case where the user is null
                            }
                        } else {
                            // Register failed
                            // Display an error message to the user
                            Toast.makeText(RegisterActivity.this, "Register failed. Check your credentials.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }
}
