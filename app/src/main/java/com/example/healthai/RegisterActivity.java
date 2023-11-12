package com.example.healthai;

import android.util.Log;
import android.widget.Button;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.FirebaseFirestore;



import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;




public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button RegisterButton;
    private Button BackButton;  // Initialize BackButton

    private EditText firstNameEditText;

    private EditText lastNameEditText;
    private EditText phoneNumEditText;

    private Spinner spinnerRoles;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        RegisterButton = findViewById(R.id.RegisterButton);

        BackButton = findViewById(R.id.BackButton);//remove

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        phoneNumEditText = findViewById(R.id.editTextPhone);

        spinnerRoles = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.role_select, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerRoles.setAdapter(adapter);


        // Initialize Firebase Authentication
        mAuth = FirebaseAuth.getInstance();

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Finish the activity to navigate back
            }
        });

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
        String firstname = firstNameEditText.getText().toString();
        String lastname = lastNameEditText.getText().toString();
        String phone = phoneNumEditText.getText().toString();

        String role = spinnerRoles.getSelectedItem().toString().toLowerCase();

        if (email.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || phone.isEmpty() || role.equals("select role")) {
            // Show an error message when either email or password is empty
            Toast.makeText(RegisterActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                Users newUser = new Users(firstname,lastname,email,password,phone,role);
                                db.collection("users").add(newUser)
                                        .addOnSuccessListener(documentReference -> {
                                            // DocumentSnapshot added with ID
                                            Log.d("TAG", "Log in successful" + documentReference.getId());
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.w("TAG", "Error adding document", e);
                                        });
                                Toast.makeText(RegisterActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                // Registration failed
                                Toast.makeText(RegisterActivity.this, "Registration failed. Check your credentials.", Toast.LENGTH_SHORT).show();
                                Log.e("RegistrationError", "Registration failed: " + task.getException());
                            }
                        }
                    });

        }
    }
}
// luke