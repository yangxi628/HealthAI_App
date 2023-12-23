package com.example.healthai.Views;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthai.Controllers.ProfileController;

public class ProfileActivity extends AppCompatActivity {
    private ProfileController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new ProfileController(this);
    }

}
