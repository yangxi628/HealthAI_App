package com.example.healthai.Views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthai.Controllers.LoginController;
import com.example.healthai.R;

public class LoginActivity extends AppCompatActivity {

    private LoginController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new LoginController(this);
    }
}