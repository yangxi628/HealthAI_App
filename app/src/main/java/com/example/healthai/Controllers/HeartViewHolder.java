package com.example.healthai.Controllers;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.example.healthai.Models.HeartAssessmentData;
import com.example.healthai.R;

public class HeartViewHolder extends GenericViewHolder<HeartAssessmentData>{
    TextView labelAgeTextView, valueAgeTextView,
            labelSexTextView, valueSexTextView,
            labelChestPainTypeTextView, valueChestPainTypeTextView,
            labelBPTextView, valueBPTextView,
            labelCholesterolTextView, valueCholesterolTextView,
            labelFBSOver120TextView, valueFBSOver120TextView,
            labelEKGResultsTextView, valueEKGResultsTextView,
            labelMaxHRTextView, valueMaxHRTextView,
            labelExerciseAnginaTextView, valueExerciseAnginaTextView,
            labelSTdepressionTextView, valueSTdepressionTextView,
            labelSlopeOfSTTextView, valueSlopeOfSTTextView,
            labelNumberOfVesselsFluroTextView, valueNumberOfVesselsFluroTextView,
            labelThalliumTextView, valueThalliumTextView;


    public HeartViewHolder(@NonNull View itemView) {
        super(itemView);
        labelSexTextView = itemView.findViewById(R.id.labelSexTextView);
        valueSexTextView = itemView.findViewById(R.id.valueSexTextView);

        labelChestPainTypeTextView = itemView.findViewById(R.id.labelChestPainTypeTextView);
        valueChestPainTypeTextView = itemView.findViewById(R.id.valueChestPainTypeTextView);

        labelBPTextView = itemView.findViewById(R.id.labelBPTextView);
        valueBPTextView = itemView.findViewById(R.id.valueBPTextView);

        labelCholesterolTextView = itemView.findViewById(R.id.labelCholesterolTextView);
        valueCholesterolTextView = itemView.findViewById(R.id.valueCholesterolTextView);

        labelFBSOver120TextView = itemView.findViewById(R.id.labelFBSOver120TextView);
        valueFBSOver120TextView = itemView.findViewById(R.id.valueFBSOver120TextView);

        labelEKGResultsTextView = itemView.findViewById(R.id.labelEKGResultsTextView);
        valueEKGResultsTextView = itemView.findViewById(R.id.valueEKGResultsTextView);

        labelMaxHRTextView = itemView.findViewById(R.id.labelMaxHRTextView);
        valueMaxHRTextView = itemView.findViewById(R.id.valueMaxHRTextView);

        labelExerciseAnginaTextView = itemView.findViewById(R.id.labelExerciseAnginaTextView);
        valueExerciseAnginaTextView = itemView.findViewById(R.id.valueExerciseAnginaTextView);

        labelSTdepressionTextView = itemView.findViewById(R.id.labelSTdepressionTextView);
        valueSTdepressionTextView = itemView.findViewById(R.id.valueSTdepressionTextView);

        labelSlopeOfSTTextView = itemView.findViewById(R.id.labelSlopeOfSTTextView);
        valueSlopeOfSTTextView = itemView.findViewById(R.id.valueSlopeOfSTTextView);

        labelNumberOfVesselsFluroTextView = itemView.findViewById(R.id.labelNumberOfVesselsFluroTextView);
        valueNumberOfVesselsFluroTextView = itemView.findViewById(R.id.valueNumberOfVesselsFluroTextView);

        labelThalliumTextView = itemView.findViewById(R.id.labelThalliumTextView);
        valueThalliumTextView = itemView.findViewById(R.id.valueThalliumTextView);
    }

    public void bindData(HeartAssessmentData heartData) {
        labelSexTextView.setText("Sex");
        valueSexTextView.setText(heartData.getSex());

        labelChestPainTypeTextView.setText("Chest Pain Type");
        valueChestPainTypeTextView.setText(heartData.getChestPainType());

        labelBPTextView.setText("Blood Pressure");
        valueBPTextView.setText(heartData.getBP());

        labelCholesterolTextView.setText("Cholesterol Level");
        valueCholesterolTextView.setText(heartData.getCholesterol());

        labelEKGResultsTextView.setText("EKG Result");
        valueEKGResultsTextView.setText(heartData.getEKGResults());

        labelMaxHRTextView.setText("Max Heart Rate");
        valueMaxHRTextView.setText(heartData.getMaxHR());

        labelFBSOver120TextView.setText("FBS Over 120");
        valueFBSOver120TextView.setText(heartData.getFBSOver120());

        labelExerciseAnginaTextView.setText("Exercise-Induced Angina");
        valueExerciseAnginaTextView.setText(heartData.getExerciseAngina());

        labelSTdepressionTextView.setText("ST Depression");
        valueSTdepressionTextView.setText(heartData.getSTdepression());

        labelSlopeOfSTTextView.setText("Slope of ST Segment");
        valueSlopeOfSTTextView.setText(heartData.getSlopeOfST());

        labelNumberOfVesselsFluroTextView.setText("Number of Vessels (Fluoroscopy)");
        valueNumberOfVesselsFluroTextView.setText(heartData.getNumberOfVesselsFluro());

        labelThalliumTextView.setText("Thallium Test Result");
        valueThalliumTextView.setText(heartData.getThallium());

    }


}
