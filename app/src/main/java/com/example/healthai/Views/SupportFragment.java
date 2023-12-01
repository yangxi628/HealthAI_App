package com.example.healthai.Views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.example.healthai.Models.UserState;
import com.example.healthai.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class SupportFragment extends Fragment {
    FirebaseFirestore db;
    ImageView profileButton;
    Button scheduleButton;
    Button contactButton;
    Button contactInsuranceButton;
    Button chatButton;
    TextView usernameTextView;
    TextView emailTextView;
    ImageView doctorProfileImageView;
    TextView doctorNameTextView;
    TextView doctorEmailTextView;
    String doctorProfileImage;
    String doctorName;
    String doctorEmail;
    String doctorPhone;
    ImageView insuranceProfileImageView;
    TextView insuranceNameTextView;
    TextView insuranceEmailTextView;

    String insuranceProfileImage;
    String insuranceName;
    String insuranceEmail;
    String insurancePhone;

    public SupportFragment() {
        // Required empty public constructor
    }

    public static SupportFragment newInstance(String param1, String param2) {
        SupportFragment fragment = new SupportFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = FirebaseFirestore.getInstance();
        View view = inflater.inflate(R.layout.fragment_support, container, false);
        profileButton = view.findViewById(R.id.profileButton);
        chatButton = view.findViewById(R.id.chatButton);

        scheduleButton = view.findViewById(R.id.scheduleButton);contactButton = view.findViewById(R.id.contactButton);
        contactInsuranceButton = view.findViewById(R.id.contactInsuranceButton);
        usernameTextView = view.findViewById(R.id.CurrentUserNameText);
        emailTextView = view.findViewById(R.id.currentUserEmail);
        doctorNameTextView = view.findViewById(R.id.doctorName);
        doctorEmailTextView = view.findViewById(R.id.doctorEmail);
        doctorProfileImageView = view.findViewById(R.id.doctorProfileImage);


        insuranceNameTextView = view.findViewById(R.id.insuranceName);
        insuranceEmailTextView = view.findViewById(R.id.insuranceEmail);
        insuranceProfileImageView = view.findViewById(R.id.insuranceProfileImage);

        UserState userState = UserState.getInstance();
        String currentUserNameText = "Hello, " + userState.getFirstName();
        String currentUserEmail = userState.getEmail();
        String userDoctorID = userState.getDoctor();
        String userInsuranceID = userState.getInsurance();

        usernameTextView.setText(currentUserNameText);
        emailTextView.setText(currentUserEmail);

        db.collection("users")
                .document(userDoctorID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            // Handle errors if any
                            return;
                        }

                        if (document.exists()) {
                            // The document exists, and you can access its data
                            doctorName = document.getString("firstName") + " " + document.getString("lastName");
                            doctorProfileImage = document.getString("profileimg");
                            doctorEmail = document.getString("email");
                            doctorPhone = document.getString("phone");

                            doctorNameTextView.setText(doctorName);
                            doctorEmailTextView.setText(doctorEmail);

                            if (doctorProfileImage != null && !doctorProfileImage.isEmpty()) {
                                Picasso.get().load(doctorProfileImage).into(doctorProfileImageView);
                            } else {
                                Log.d("DoctorError", "Image Not Found");

                            }
                        } else {

                            Log.d("DoctorError", "Doctor Not Found");

                        }
                    }
                });

        db.collection("insuranceCompanies")
                .document(userInsuranceID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            // Handle errors if any
                            return;
                        }

                        if (document.exists()) {
                            insuranceName = document.getString("name");
                            insuranceProfileImage = document.getString("logoimg");
                            insuranceEmail = document.getString("email");
                            insurancePhone = document.getString("phone");

                            insuranceNameTextView.setText(insuranceName);
                            insuranceEmailTextView.setText(insuranceEmail);

                            if (insuranceProfileImage != null && !insuranceProfileImage.isEmpty()) {
                                Picasso.get().load(insuranceProfileImage).into(insuranceProfileImageView);
                            } else {
                                Log.d("InsuranceError", "Image Not Found");

                            }
                        } else {

                            Log.d("InsuranceError", "Insurance Not Found");

                        }
                    }
                });

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.profileButton) {
                    switchToActivity(ProfileActivity.class);
                } else if (v.getId() == R.id.scheduleButton) {
                    switchToActivity(AppointmentActivity.class);
                }
                else if (v.getId() == R.id.contactButton) {
                    Intent insuranceIntent = new Intent(Intent.ACTION_DIAL);
                    insuranceIntent.setData(Uri.parse("tel:" + doctorPhone));
                    startActivity(insuranceIntent);
                }
                else if (v.getId() == R.id.contactInsuranceButton) {
                    Intent insuranceIntent = new Intent(Intent.ACTION_DIAL);
                    insuranceIntent.setData(Uri.parse("tel:" + insurancePhone));
                    startActivity(insuranceIntent);
                }
                else if (v.getId() == R.id.chatButton) {
                    switchToActivity(DoctorChatActivity.class);

                }
            }
        };
        profileButton.setOnClickListener(buttonClickListener);
        scheduleButton.setOnClickListener(buttonClickListener);
        contactButton.setOnClickListener(buttonClickListener);
        chatButton.setOnClickListener(buttonClickListener);
        contactInsuranceButton.setOnClickListener(buttonClickListener);
        return view;
    }
    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

}