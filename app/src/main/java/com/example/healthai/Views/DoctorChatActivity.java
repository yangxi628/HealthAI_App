package com.example.healthai.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.healthai.Controllers.DoctorChatController;


public class DoctorChatActivity extends AppCompatActivity {
    private DoctorChatController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new DoctorChatController(this);

    }
}