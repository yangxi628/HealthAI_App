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
import com.example.healthai.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


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
    private  RadioGroup radioGroup;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        RegisterButton = findViewById(R.id.RegisterButton);
        BackButton = findViewById(R.id.BackButton);

        /*User Input*/
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



        if (email.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty()) {
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
                                String uid = mAuth.getCurrentUser().getUid();
                                saveUserDetailsToFirestore(uid,firstname,lastname,email,doctor,profileImg,role);
                            } else {
                                // Registration failed
                                Toast.makeText(RegisterActivity.this, "Registration failed. Check your credentials.", Toast.LENGTH_SHORT).show();
                                Log.e("RegistrationError", "Registration failed: " + task.getException());
                            }
                        }
                    });
        }


    }

    private void saveUserDetailsToFirestore(String uid, String firstname, String lastname, String email, String doctor, String profileImg,String role) {
        User user = new User(firstname, lastname, email, doctor, profileImg, role.toLowerCase());

        firestore.collection("users").document(uid)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Registration successful!",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to save user details.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}