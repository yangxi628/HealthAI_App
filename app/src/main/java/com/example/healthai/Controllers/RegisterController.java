package com.example.healthai.Controllers;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.healthai.Controllers.Adapters.DoctorAdapter;
import com.example.healthai.Controllers.Adapters.InsuranceAdapter;
import com.example.healthai.Models.Doctor;
import com.example.healthai.Models.Insurance;
import com.example.healthai.Models.UserState;
import com.example.healthai.Models.Users;
import com.example.healthai.R;
import com.example.healthai.Views.LoginActivity;
import com.example.healthai.Views.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RegisterController {
    private Button registerButton;
    private ImageView backButton;
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

    private final RegisterActivity view;

    public RegisterController(RegisterActivity view) {
        this.view = view;
        initialize();
    }


    private void initialize() {
        view.setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        initUiElements();
        setupDoctorSpinner();
        setupInsuranceSpinner();
        setupRadioGroup();

        backButton.setOnClickListener(v -> view.finish());
        registerButton.setOnClickListener(v -> attemptRegister());
    }

    private void initUiElements() {
        registerButton = view.findViewById(R.id.RegisterButton);
        backButton = view.findViewById(R.id.BackButton);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText);
        firstNameEditText = view.findViewById(R.id.firstNameEditText);
        lastNameEditText = view.findViewById(R.id.lastNameEditText);
        spinnerInsurance = view.findViewById(R.id.spinnerInsurance);
        spinnerDoctor = view.findViewById(R.id.spinnerDoctor);
        profileImgEditText = view.findViewById(R.id.editTextProfileImg);
        radioGroup = view.findViewById(R.id.radioGroup);
    }

    private void setupDoctorSpinner() {
        List<Doctor> doctorList = new ArrayList<>();

        firestore.collection("users")
                .whereEqualTo("role", "doctor")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            return;
                        }

                        doctorList.clear();

                        for (QueryDocumentSnapshot document : snapshot) {
                            String documentId = document.getId();
                            String doctorName = document.getString("firstName") + " " + document.getString("lastName");

                            Doctor doctor = new Doctor(documentId, doctorName);
                            doctorList.add(doctor);
                        }

                        Spinner spinnerDoctor = view.findViewById(R.id.spinnerDoctor);
                        DoctorAdapter doctorAdapter = new DoctorAdapter(view.getApplicationContext(), doctorList);
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
            }
        });
    }

    private void setupInsuranceSpinner() {
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

                        InsuranceAdapter insuranceAdapter = new InsuranceAdapter(view.getApplicationContext(), insuranceList);
                        spinnerInsurance.setAdapter(insuranceAdapter);
                    }
                });

        spinnerInsurance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Insurance selectedInsurance = (Insurance) parentView.getItemAtPosition(position);

                if (selectedInsurance != null) {
                    selectedInsuranceId = selectedInsurance.getDocumentId();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }


    private void setupRadioGroup() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = view.findViewById(checkedId);
            handleRadioButtonSelection(radioButton);
        });
    }

    private void handleRadioButtonSelection(RadioButton radioButton) {
        spinnerDoctor.setEnabled(radioButton.getText().toString().equals("Doctor"));
        spinnerDoctor.setBackgroundResource(R.drawable.disabled_edittext_background);
    }

    private void attemptRegister() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        String firstname = firstNameEditText.getText().toString();
        String lastname = lastNameEditText.getText().toString();
        String doctor = selectedDoctorId;
        String insurance = selectedInsuranceId;
        radioGroup = view.findViewById(R.id.radioGroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = view.findViewById(selectedId);
        String role = radioButton.getText().toString().toLowerCase();
        String phone = null;

        if (email.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty()) {
            Toast.makeText(view, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(view, "Invalid email format.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(view, "Password does not match", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = mAuth.getCurrentUser().getUid();
                                List<Users.Timeslot> timeslots = null;
                                UserState userState = UserState.getInstance();
                                if (role.equals("doctor")) {
                                    timeslots = userState.createnewTimeslot();
                                }

                                Users user = new Users(firstname, lastname, email, password, phone, role, timeslots, doctor, insurance);
                                saveUserDetailsToFirestore(uid, user);

                                Intent intent = new Intent(view, LoginActivity.class);
                                view.startActivity(intent);
                            } else {
                                Toast.makeText(view, "Registration failed. Check your credentials.", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(view, "Registration successful!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(view, "Failed to save user details.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
