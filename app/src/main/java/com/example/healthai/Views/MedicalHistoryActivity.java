package com.example.healthai.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthai.Controllers.MedicalHistoryAdapter;
import com.example.healthai.Models.AssessmentData;
import com.example.healthai.Models.DiabetesAssessmentData;
import com.example.healthai.Models.HeartAssessmentData;
import com.example.healthai.Models.LungAssessmentData;
import com.example.healthai.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MedicalHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MedicalHistoryAdapter adapter;
    private List<AssessmentData> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        recyclerView = findViewById(R.id.recyclerView);
        dataList = new ArrayList<>();

        TextView title = findViewById(R.id.detailName);
        TextView outcomeText = findViewById(R.id.outcome);
        TextView dateText = findViewById(R.id.dateText);
        ImageView detailImage = findViewById(R.id.detailImage);

        ImageView backButton = findViewById(R.id.BackButton);

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.BackButton) {
                    switchToActivity(NavigationActivity.class);
                }
            }
        };
        backButton.setOnClickListener(buttonClickListener);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String assessmentType = extras.getString("assessmentType");

            switch (assessmentType) {
                case "lung":
                    title.setText("Lung cancer Risk Assessment");
                    dateText.setText(extras.getString("assessmentDate"));
                    detailImage.setImageResource(R.drawable.lung);
                    if (outcomeText != null) {
                        outcomeText.setText(extras.getString("assessmentResult").equals("1") ? "lung Cancer Likely" : "lung Cancer Unlikely");
                    }
                    LungAssessmentData lungData = new LungAssessmentData(
                            extras.getString("assessmentGender"),
                            extras.getString("assessmentAge"),
                            extras.getString("assessmentSmoking"),
                            extras.getString("assessmentYellowFingers"),
                            extras.getString("assessmentAnxiety"),
                            extras.getString("assessmentPeerPressure"),
                            extras.getString("assessmentChronicDisease"),
                            extras.getString("assessmentFatigue"),
                            extras.getString("assessmentAllergy"),
                            extras.getString("assessmentWheezing"),
                            extras.getString("assessmentAlcoholConsuming"),
                            extras.getString("assessmentCoughing"),
                            extras.getString("assessmentShortnessOfBreath"),
                            extras.getString("assessmentSwallowingDifficulty"),
                            extras.getString("assessmentChestPain"),
                            extras.getString("assessmentDate"),
                            extras.getString("assessmentResult")
                    );
                    dataList.add(lungData);
                    break;

                case "heart":
                    title.setText("heart Disease Risk Assessment");
                    dateText.setText(extras.getString("assessmentDate"));
                    if (outcomeText != null) {
                        outcomeText.setText(extras.getString("assessmentResult").equals("1") ? "Heart Disease Likely" : "Heart Disease Unlikely");
                    }

                    detailImage.setImageResource(R.drawable.heart);

                    HeartAssessmentData heartData = new HeartAssessmentData(
                            extras.getString("assessmentAge"),
                            extras.getString("assessmentSex"),
                            extras.getString("assessmentChestPainType"),
                            extras.getString("assessmentBP"),
                            extras.getString("assessmentCholesterol"),
                            extras.getString("assessmentFBSOver120"),
                            extras.getString("assessmentEKGResults"),
                            extras.getString("assessmentMaxHR"),
                            extras.getString("assessmentExerciseAngina"),
                            extras.getString("assessmentSTdepression"),
                            extras.getString("assessmentSlopeOfST"),
                            extras.getString("assessmentNumberOfVesselsFluro"),
                            extras.getString("assessmentThallium"),
                            extras.getString("assessmentDate"),
                            extras.getString("assessmentResult")
                    );
                    dataList.add(heartData);
                    break;

                case "diabetes":
                    title.setText("Diabetes Risk Assessment");
                    dateText.setText(extras.getString("assessmentDate"));
                    if (outcomeText != null) {
                        outcomeText.setText(extras.getString("assessmentResult").equals("1") ? "Diabetes Likely" : "Diabetes Unlikely");
                    }
                    detailImage.setImageResource(R.drawable.colon);

                    DiabetesAssessmentData diabetesData = new DiabetesAssessmentData(
                            extras.getString("assessmentPregnancies"),
                            extras.getString("assessmentGlucose"),
                            extras.getString("assessmentBloodPressure"),
                            extras.getString("assessmentSkinThickness"),
                            extras.getString("assessmentInsulin"),
                            extras.getString("assessmentBMI"),
                            extras.getString("assessmentDiabetesPedigreeFunction"),
                            extras.getString("assessmentAge"),
                            extras.getString("assessmentDate"),
                            extras.getString("assessmentResult")
                    );
                    dataList.add(diabetesData);
                    break;
            }
        }

        adapter = new MedicalHistoryAdapter(this, dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void switchToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
