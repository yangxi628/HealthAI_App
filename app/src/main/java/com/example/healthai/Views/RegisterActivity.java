package com.example.healthai.Views;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthai.Controllers.RegisterController;


public class RegisterActivity extends AppCompatActivity {
    private RegisterController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new RegisterController(this);
    }

}