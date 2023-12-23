package com.example.healthai.Views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.healthai.Controllers.MedicalHistoryController;

public class MedicalHistoryActivity extends AppCompatActivity {
    private MedicalHistoryController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new MedicalHistoryController(this);
    }

}
