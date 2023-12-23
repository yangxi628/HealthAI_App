package com.example.healthai.Controllers;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.healthai.Controllers.Helpers.SwitchViewHelper;
import com.example.healthai.Models.UserState;
import com.example.healthai.R;
import com.example.healthai.Views.AppointmentActivity;
import com.example.healthai.Views.DoctorChatActivity;
import com.example.healthai.Views.ProfileActivity;
import com.example.healthai.Views.SupportFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class SupportController {
    private final SupportFragment fragment;
    private FirebaseFirestore db;
    private ImageView profileButton;
    private Button scheduleButton;
    private Button contactButton;
    private Button contactInsuranceButton;
    private Button chatButton;
    private TextView usernameTextView;
    private TextView emailTextView;
    private ImageView doctorProfileImageView;
    private TextView doctorNameTextView;
    private TextView doctorEmailTextView;
    private String doctorProfileImage;
    private String doctorName;
    private String doctorEmail;
    private String doctorPhone;
    private ImageView insuranceProfileImageView;
    private TextView insuranceNameTextView;
    private TextView insuranceEmailTextView;
    private String insuranceProfileImage;
    private String insuranceName;
    private String insuranceEmail;
    private String insurancePhone;

    public SupportController(SupportFragment fragment) {
        this.fragment = fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support, container, false);
        initializeView(view);
        return view;
    }

    private void initializeView(View view) {
        db = FirebaseFirestore.getInstance();
        initUiElements(view);
        UserState userState = UserState.getInstance();
        String currentUserNameText = "Hello, " + userState.getFirstName();
        String currentUserEmail = userState.getEmail();

        usernameTextView.setText(currentUserNameText);
        emailTextView.setText(currentUserEmail);

        setupDoctorProfile();
        setupInsuranceProfile();
        setButtonClickListeners();
    }

    private void initUiElements(View view) {
        profileButton = view.findViewById(R.id.profileButton);
        chatButton = view.findViewById(R.id.chatButton);
        scheduleButton = view.findViewById(R.id.scheduleButton);
        contactButton = view.findViewById(R.id.contactButton);
        contactInsuranceButton = view.findViewById(R.id.contactInsuranceButton);
        usernameTextView = view.findViewById(R.id.CurrentUserNameText);
        emailTextView = view.findViewById(R.id.currentUserEmail);
        doctorNameTextView = view.findViewById(R.id.doctorName);
        doctorEmailTextView = view.findViewById(R.id.doctorEmail);
        doctorProfileImageView = view.findViewById(R.id.doctorProfileImage);
        insuranceNameTextView = view.findViewById(R.id.insuranceName);
        insuranceEmailTextView = view.findViewById(R.id.insuranceEmail);
        insuranceProfileImageView = view.findViewById(R.id.insuranceProfileImage);
    }

    private void setupDoctorProfile() {
        UserState userState = UserState.getInstance();
        String userDoctorID = userState.getDoctor();


        db.collection("users").document(userDoctorID).addSnapshotListener((document, error) -> {
            if (error != null) {
                return;
            }

            if (document != null && document.exists()) {
                doctorName = document.getString("firstName") + " " + document.getString("lastName");
                doctorProfileImage = document.getString("profileimg");
                doctorEmail = document.getString("email");
                doctorPhone = document.getString("phone");

                doctorNameTextView.setText(doctorName);
                doctorEmailTextView.setText(doctorEmail);

                if (!TextUtils.isEmpty(doctorProfileImage)) {
                    Picasso.get().load(doctorProfileImage).into(doctorProfileImageView);
                } else {
                    Log.d("DoctorError", "Image Not Found");
                }
            } else {
                Log.d("DoctorError", "Doctor Not Found");
            }
        });
    }

    private void setupInsuranceProfile() {
        UserState userState = UserState.getInstance();
        String userInsuranceID = userState.getInsurance();

        db.collection("insuranceCompanies").document(userInsuranceID).addSnapshotListener((document, error) -> {
            if (error != null) {
                return;
            }

            if (document != null && document.exists()) {
                insuranceName = document.getString("name");
                insuranceProfileImage = document.getString("logoimg");
                insuranceEmail = document.getString("email");
                insurancePhone = document.getString("phone");

                insuranceNameTextView.setText(insuranceName);
                insuranceEmailTextView.setText(insuranceEmail);

                if (!TextUtils.isEmpty(insuranceProfileImage)) {
                    Picasso.get().load(insuranceProfileImage).into(insuranceProfileImageView);
                } else {
                    Log.d("InsuranceError", "Image Not Found");
                }
            } else {
                Log.d("InsuranceError", "Insurance Not Found");
            }
        });
    }

    private void setButtonClickListeners() {
        View.OnClickListener buttonClickListener = v -> {
            if (v.getId() == R.id.profileButton) {
                SwitchViewHelper.switchToActivity(fragment.getContext(), ProfileActivity.class);
            } else if (v.getId() == R.id.scheduleButton) {
                SwitchViewHelper.switchToActivity(fragment.getContext(), AppointmentActivity.class);
            } else if (v.getId() == R.id.contactButton) {
                contactDoctor();
            } else if (v.getId() == R.id.contactInsuranceButton) {
                contactInsurance();
            } else if (v.getId() == R.id.chatButton) {
                SwitchViewHelper.switchToActivity(fragment.getContext(), DoctorChatActivity.class);
            }
        };

        profileButton.setOnClickListener(buttonClickListener);
        scheduleButton.setOnClickListener(buttonClickListener);
        contactButton.setOnClickListener(buttonClickListener);
        chatButton.setOnClickListener(buttonClickListener);
        contactInsuranceButton.setOnClickListener(buttonClickListener);
    }


    private void contactDoctor() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + doctorPhone));
        fragment.getContext().startActivity(intent);
    }


    private void contactInsurance() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + insurancePhone));
        fragment.getContext().startActivity(intent);
    }
}

