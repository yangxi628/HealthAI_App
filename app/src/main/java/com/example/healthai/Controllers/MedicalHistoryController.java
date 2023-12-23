package com.example.healthai.Controllers;

import static com.example.healthai.Controllers.Helpers.SwitchViewHelper.switchToActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthai.Controllers.Adapters.MedicalHistoryAdapter;
import com.example.healthai.Models.Assessment.AssessmentData;
import com.example.healthai.Models.Assessment.DiabetesAssessmentData;
import com.example.healthai.Models.Assessment.HeartAssessmentData;
import com.example.healthai.Models.Assessment.LungAssessmentData;
import com.example.healthai.R;
import com.example.healthai.Views.MedicalHistoryActivity;
import com.example.healthai.Views.NavigationBar;

import java.util.ArrayList;
import java.util.List;


public class MedicalHistoryController {
    private RecyclerView recyclerView;
    private MedicalHistoryAdapter adapter;
    private List<AssessmentData> dataList;
    private final MedicalHistoryActivity view;

    public MedicalHistoryController(MedicalHistoryActivity view) {
        this.view = view;
        initialize();
    }

    private void initialize() {
        view.setContentView(R.layout.activity_medical_history);


        recyclerView = view.findViewById(R.id.recyclerView);
        dataList = new ArrayList<>();

        TextView title = view.findViewById(R.id.detailName);
        TextView outcomeText = view.findViewById(R.id.outcome);
        TextView dateText = view.findViewById(R.id.dateText);
        ImageView detailImage = view.findViewById(R.id.detailImage);

        ImageView backButton = view.findViewById(R.id.BackButton);

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.BackButton) {
                    switchToActivity(view,NavigationBar.class);
                }
            }
        };
        backButton.setOnClickListener(buttonClickListener);

        Bundle extras = view.getIntent().getExtras();
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

        adapter = new MedicalHistoryAdapter(view, dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view));
        recyclerView.setAdapter(adapter);

    }
}

