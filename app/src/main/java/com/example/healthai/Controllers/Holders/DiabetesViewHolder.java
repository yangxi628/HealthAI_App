package com.example.healthai.Controllers.Holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.healthai.Models.Assessment.DiabetesAssessmentData;
import com.example.healthai.R;

public class DiabetesViewHolder extends GenericViewHolder<DiabetesAssessmentData> {

    TextView labelPregnanciesTextView, valuePregnanciesTextView,
            labelGlucoseTextView, valueGlucoseTextView,
            labelBloodPressureTextView, valueBloodPressureTextView,
            labelSkinThicknessTextView, valueSkinThicknessTextView,
            labelInsulinTextView, valueInsulinTextView,
            labelBMITextView, valueBMITextView,
            labeldiabetesPedigreeFunctionTextView, valuediabetesPedigreeFunctionTextView,
            labelAgeTextView, valueAgeTextView;

    public DiabetesViewHolder(@NonNull View itemView) {
        super(itemView);
        labelPregnanciesTextView = itemView.findViewById(R.id.labelPregnanciesTextView);
        valuePregnanciesTextView = itemView.findViewById(R.id.valuePregnanciesTextView);

        labelGlucoseTextView = itemView.findViewById(R.id.labelGlucoseTextView);
        valueGlucoseTextView = itemView.findViewById(R.id.valueGlucoseTextView);

        labelBloodPressureTextView = itemView.findViewById(R.id.labelBloodPressureTextView);
        valueBloodPressureTextView = itemView.findViewById(R.id.valueBloodPressureTextView);

        labelSkinThicknessTextView = itemView.findViewById(R.id.labelSkinThicknessTextView);
        valueSkinThicknessTextView = itemView.findViewById(R.id.valueSkinThicknessTextView);

        labelInsulinTextView = itemView.findViewById(R.id.labelInsulinTextView);
        valueInsulinTextView = itemView.findViewById(R.id.valueInsulinTextView);

        labelBMITextView = itemView.findViewById(R.id.labelBMITextView);
        valueBMITextView = itemView.findViewById(R.id.valueBMITextView);

        labeldiabetesPedigreeFunctionTextView = itemView.findViewById(R.id.labeldiabetesPedigreeFunctionTextView);
        valuediabetesPedigreeFunctionTextView = itemView.findViewById(R.id.valuediabetesPedigreeFunctionTextView);

        labelAgeTextView = itemView.findViewById(R.id.labelAgeTextView);
        valueAgeTextView = itemView.findViewById(R.id.valueAgeTextView);
    }

    public void bindData(DiabetesAssessmentData diabetesData) {
        labelAgeTextView.setText("Age");
        valueAgeTextView.setText(diabetesData.getAge());

        labelPregnanciesTextView.setText("Number of Pregnancies");
        valuePregnanciesTextView.setText(diabetesData.getPregnancies());

        labelGlucoseTextView.setText("Glucose Level");
        valueGlucoseTextView.setText(diabetesData.getGlucose());

        labelBloodPressureTextView.setText("Blood Pressure");
        valueBloodPressureTextView.setText(diabetesData.getBloodPressure());

        labelSkinThicknessTextView.setText("Skin Thickness");
        valueSkinThicknessTextView.setText(diabetesData.getSkinThickness());

        labelInsulinTextView.setText("Insulin Level");
        valueInsulinTextView.setText(diabetesData.getInsulin());

        labelBMITextView.setText("Body Mass Index (BMI)");
        valueBMITextView.setText(diabetesData.getBMI());

        labeldiabetesPedigreeFunctionTextView.setText("Diabetes Pedigree Function");
        valuediabetesPedigreeFunctionTextView.setText(diabetesData.getDiabetesPedigreeFunction());
    }


}
