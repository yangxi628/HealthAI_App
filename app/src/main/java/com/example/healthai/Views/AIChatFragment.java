package com.example.healthai.Views;

import android.content.Intent;
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

public class AIChatFragment extends Fragment {
    ImageView profileButton;
    Button openChatbotButton;
    TextView usernameTextView;
    TextView emailTextView;
    public AIChatFragment() {
        // Required empty public constructor
    }

    public static AIChatFragment newInstance(String param1, String param2) {
        AIChatFragment fragment = new AIChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a_i_chat, container, false);
        profileButton = view.findViewById(R.id.profileButton);
        openChatbotButton = view.findViewById(R.id.openChatbotButton);

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
                } else if (v.getId() == R.id.openChatbotButton) {
                    switchToActivity(AiChatActivity.class);
                }
            }
        };
        profileButton.setOnClickListener(buttonClickListener);
        openChatbotButton.setOnClickListener(buttonClickListener);
        return view;
    }
    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        startActivity(intent);
    }
}