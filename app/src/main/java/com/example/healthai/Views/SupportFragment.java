package com.example.healthai.Views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthai.Models.UserState;
import com.example.healthai.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SupportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SupportFragment extends Fragment {

    ImageView profileButton;
    Button scheduleButton;
    Button contactButton;
    Button contactInsuranceButton;
    TextView usernameTextView;
    TextView emailTextView;

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
        View view = inflater.inflate(R.layout.fragment_support, container, false);
        profileButton = view.findViewById(R.id.profileButton);
         scheduleButton = view.findViewById(R.id.scheduleButton);
         contactButton = view.findViewById(R.id.contactButton);
        contactInsuranceButton = view.findViewById(R.id.contactInsuranceButton);
        usernameTextView = view.findViewById(R.id.CurrentUserNameText);
        emailTextView = view.findViewById(R.id.currentUserEmail);

        UserState userState = UserState.getInstance();
        String currentUserNameText = "Hello, " + userState.getFirstName();
        String currentUserEmail = userState.getEmail();

        usernameTextView.setText(currentUserNameText);
        emailTextView.setText(currentUserEmail);
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
                    insuranceIntent.setData(Uri.parse("tel:0987654321"));
                    startActivity(insuranceIntent);
                }
                else if (v.getId() == R.id.contactInsuranceButton) {
                    Intent insuranceIntent = new Intent(Intent.ACTION_DIAL);
                    insuranceIntent.setData(Uri.parse("tel:0987654321"));
                    startActivity(insuranceIntent);
                }
            }
        };
        profileButton.setOnClickListener(buttonClickListener);
        scheduleButton.setOnClickListener(buttonClickListener);
        contactButton.setOnClickListener(buttonClickListener);
        contactInsuranceButton.setOnClickListener(buttonClickListener);
        return view;
    }
    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }

}