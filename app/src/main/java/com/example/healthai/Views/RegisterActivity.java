package com.example.healthai.Views;

import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthai.Controllers.DoctorAdapter;
import com.example.healthai.Controllers.InsuranceAdapter;
import com.example.healthai.Models.AssessmentData;
import com.example.healthai.Models.Doctor;
import com.example.healthai.Models.Insurance;
import com.example.healthai.Models.Users;
import com.example.healthai.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.Timestamp;
import com.example.healthai.Models.UserState;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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
    private Spinner spinnerDoctor;
    private Spinner spinnerInsurance;
    private EditText profileImgEditText;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private RadioGroup radioGroup;
    private String selectedDoctorId;
    private String selectedInsuranceId;


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
        spinnerInsurance = findViewById(R.id.spinnerInsurance);


        spinnerDoctor = findViewById(R.id.spinnerDoctor);
        profileImgEditText = findViewById(R.id.editTextProfileImg);
        radioGroup = findViewById(R.id.radioGroup);

        List<Doctor> doctorList = new ArrayList<>();

        firestore.collection("users")
                .whereEqualTo("role", "doctor")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            // Handle errors if any
                            return;
                        }

                        doctorList.clear(); // Clear the list before adding new data

                        for (QueryDocumentSnapshot document : snapshot) {
                            String documentId = document.getId();
                            String doctorName = document.getString("firstName") + " " + document.getString("lastName");

                            Doctor doctor = new Doctor(documentId, doctorName);
                            doctorList.add(doctor);
                        }

                        // Set up the Spinner with the doctor names using the custom adapter
                        Spinner spinnerDoctor = findViewById(R.id.spinnerDoctor);
                        DoctorAdapter doctorAdapter = new DoctorAdapter(getApplicationContext(), doctorList);
                        spinnerDoctor.setAdapter(doctorAdapter);
                    }
                });

        spinnerDoctor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                Doctor selectedDoctor = (Doctor) parentView.getItemAtPosition(position);

                if (selectedDoctor != null) {
                     selectedDoctorId = selectedDoctor.getDocumentId();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle nothing selected if needed
            }
        });


        List<Insurance> insuranceList = new ArrayList<>();

        firestore.collection("insuranceCompanies")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            return;
                        }

                        insuranceList.clear();

                        for (QueryDocumentSnapshot document : snapshot) {
                            String documentId = document.getId();
                            String insuranceName = document.getString("name");

                            Insurance insurance = new Insurance(documentId, insuranceName);
                            insuranceList.add(insurance);
                        }

                        InsuranceAdapter insuranceAdapter = new InsuranceAdapter(getApplicationContext(), insuranceList);
                        spinnerInsurance.setAdapter(insuranceAdapter);
                    }
                });

        spinnerInsurance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Retrieve the selected Doctor object from the adapter
                Insurance selectedInsurance = (Insurance) parentView.getItemAtPosition(position);

                if (selectedInsurance != null) {
                    selectedInsuranceId = selectedInsurance.getDocumentId();

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle nothing selected if needed
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                RadioButton radioButton = findViewById(checkedId);

                // Disable or enable the doctorEditText based on the selected radio button
                if (radioButton.getText().toString().equals("Doctor")) {
                    spinnerDoctor.setEnabled(false);
                    spinnerDoctor.setBackgroundResource(R.drawable.disabled_edittext_background);
                } else {
                    spinnerDoctor.setEnabled(true);
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
        String doctor = selectedDoctorId;
        String insurance = selectedInsuranceId;
        String profileImg = profileImgEditText.getText().toString();
        radioGroup = findViewById(R.id.radioGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        String role = radioButton.getText().toString().toLowerCase();
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
                                List<Users.Timeslot> timeslots = null;
                                UserState userState = UserState.getInstance();
                                if (role.equals("doctor")){
                                    timeslots = userState.createnewTimeslot();

                                }
                                Users user = new Users(firstname, lastname, email, password, phone, role, timeslots, doctor,insurance);
                                saveUserDetailsToFirestore(uid, user);
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
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