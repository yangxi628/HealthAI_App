package com.example.healthai.Views;

import android.os.Bundle;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.healthai.Controllers.NavigationBarController;
import com.example.healthai.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationBar extends AppCompatActivity {
    private FrameLayout frameLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationBarController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);

        frameLayout = findViewById(R.id.frame_layout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        controller = new NavigationBarController(this);
    }
    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }
    public FrameLayout getFrameLayout() {
        return frameLayout;
    }
}