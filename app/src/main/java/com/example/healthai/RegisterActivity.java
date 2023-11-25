package com.example.healthai;

import android.util.Log;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthai.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private Button RegisterButton;
    private ImageView BackButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText firstNameEditText;

    private EditText lastNameEditText;
    private EditText doctorEditText;
    private EditText profileImgEditText;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        RegisterButton = findViewById(R.id.RegisterButton);
        BackButton = findViewById(R.id.BackButton);

        /* User Input */
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        doctorEditText = findViewById(R.id.editTextDoctor);
        profileImgEditText = findViewById(R.id.editTextProfileImg);
        radioGroup = findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                RadioButton radioButton = findViewById(checkedId);

                // Disable or enable the doctorEditText based on the selected radio button
                if (radioButton.getText().toString().equals("Doctor")) {
                    doctorEditText.setEnabled(false);
                    doctorEditText.setBackgroundResource(R.drawable.disabled_edittext_background);
                } else {
                    doctorEditText.setEnabled(true);
                }
            }
        });

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
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        String firstname = firstNameEditText.getText().toString();
        String lastname = lastNameEditText.getText().toString();
        String doctor = doctorEditText.getText().toString();
        String profileImg = profileImgEditText.getText().toString();
        radioGroup = findViewById(R.id.radioGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        String role = radioButton.getText().toString();
        String phone = null;

        if (email.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(RegisterActivity.this, "Invalid email format.", Toast.LENGTH_SHORT).show();
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
                                String uid = mAuth.getCurrentUser().getUid();
                                // Create an empty list for timeslots initially
                                List<Users.Timeslot> timeslots = new ArrayList<>();
                                for (int i = 0; i < 5; i++) {
                                    // Get the current timestamp
                                    Timestamp nextDayTimestamp = Timestamp.now();

                                    // Convert Timestamp to Date
                                    Date nextDayDate = nextDayTimestamp.toDate();

                                    // Use Calendar to add a day to the date
                                    Calendar calendar = Calendar.getInstance();
                                    calendar.setTime(nextDayDate);
                                    calendar.add(Calendar.DAY_OF_MONTH, i);

                                    for (int j = 9; j <= 16; j++) {
                                        // Set the current hour
                                        calendar.set(Calendar.HOUR_OF_DAY, j);
                                        calendar.set(Calendar.MINUTE, 0);
                                        calendar.set(Calendar.SECOND, 0);

                                        // Get the Date object representing the current time
                                        Date currentTime = calendar.getTime();

                                        // Create a new Timestamp object
                                        Timestamp currentTimestamp = new Timestamp(currentTime);

                                        // Format the date and time as needed
                                        String formattedTime = currentTimestamp.toDate().toString(); // Modify the formatting as needed

                                        // Create the Timeslot object
                                        timeslots.add(new Users.Timeslot(null, "available", currentTimestamp));
                                    }
                                }

                                Users user = new Users(firstname, lastname, email, password, phone, role.toLowerCase(), timeslots);
                                saveUserDetailsToFirestore(uid, user);
                            } else {
                                // Registration failed
                                Toast.makeText(RegisterActivity.this, "Registration failed. Check your credentials.", Toast.LENGTH_SHORT).show();
                                Log.e("RegistrationError", "Registration failed: " + task.getException());
                            }
                        }
                    });
        }
    }

    private void saveUserDetailsToFirestore(String uid, Users user) {
        firestore.collection("users").document(uid)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to save user details.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
