package com.example.healthai.Controllers.Holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.healthai.Models.Assessment.LungAssessmentData;
import com.example.healthai.R;

public class LungViewHolder extends GenericViewHolder<LungAssessmentData> {

    TextView labelGenderTextView, valueGenderTextView,
            labelAgeTextView, valueAgeTextView,
            labelSMOKINGTextView, valueSMOKINGTextView,
            labelYELLOW_FINGERSTextView, valueYELLOW_FINGERSTextView,
            labelANXIETYTextView, valueANXIETYTextView,
            labelPEER_PRESSURETextView, valuePEER_PRESSURETextView,
            labelCHRONIC_DISEASETextView, valueCHRONIC_DISEASETextView,
            labelFATIGUETextView, valueFATIGUETextView,
            labelALLERGYTextView, valueALLERGYTextView,
            labelWHEEZINGTextView, valueWHEEZINGTextView,
            labelALCOHOL_CONSUMINGTextView, valueALCOHOL_CONSUMINGTextView,
            labelCOUGHINGTextView, valueCOUGHINGTextView,
            labelSHORTNESS_OF_BREATHTextView, valueSHORTNESS_OF_BREATHTextView,
            labelSWALLOWING_DIFFICULTYTextView, valueSWALLOWING_DIFFICULTYTextView,
            labelCHEST_PAINTextView, valueCHEST_PAINTextView;

    public LungViewHolder(@NonNull View itemView) {
        super(itemView);
        labelGenderTextView = itemView.findViewById(R.id.labelGenderTextView);
        valueGenderTextView = itemView.findViewById(R.id.valueGenderTextView);

        labelAgeTextView = itemView.findViewById(R.id.labelAgeTextView);
        valueAgeTextView = itemView.findViewById(R.id.valueAgeTextView);

        labelSMOKINGTextView = itemView.findViewById(R.id.labelSMOKINGTextView);
        valueSMOKINGTextView = itemView.findViewById(R.id.valueSMOKINGTextView);

        labelYELLOW_FINGERSTextView = itemView.findViewById(R.id.labelYELLOW_FINGERSTextView);
        valueYELLOW_FINGERSTextView = itemView.findViewById(R.id.valueYELLOW_FINGERSTextView);

        labelANXIETYTextView = itemView.findViewById(R.id.labelANXIETYTextView);
        valueANXIETYTextView = itemView.findViewById(R.id.valueANXIETYTextView);

        labelPEER_PRESSURETextView = itemView.findViewById(R.id.labelPEER_PRESSURETextView);
        valuePEER_PRESSURETextView = itemView.findViewById(R.id.valuePEER_PRESSURETextView);

        labelCHRONIC_DISEASETextView = itemView.findViewById(R.id.labelCHRONIC_DISEASETextView);
        valueCHRONIC_DISEASETextView = itemView.findViewById(R.id.valueCHRONIC_DISEASETextView);

        labelFATIGUETextView = itemView.findViewById(R.id.labelFATIGUETextView);
        valueFATIGUETextView = itemView.findViewById(R.id.valueFATIGUETextView);

        labelALLERGYTextView = itemView.findViewById(R.id.labelALLERGYTextView);
        valueALLERGYTextView = itemView.findViewById(R.id.valueALLERGYTextView);

        labelWHEEZINGTextView = itemView.findViewById(R.id.labelWHEEZINGTextView);
        valueWHEEZINGTextView = itemView.findViewById(R.id.valueWHEEZINGTextView);

        labelALCOHOL_CONSUMINGTextView = itemView.findViewById(R.id.labelALCOHOL_CONSUMINGTextView);
        valueALCOHOL_CONSUMINGTextView = itemView.findViewById(R.id.valueALCOHOL_CONSUMINGTextView);

        labelCOUGHINGTextView = itemView.findViewById(R.id.labelCOUGHINGTextView);
        valueCOUGHINGTextView = itemView.findViewById(R.id.valueCOUGHINGTextView);

        labelSHORTNESS_OF_BREATHTextView = itemView.findViewById(R.id.labelSHORTNESS_OF_BREATHTextView);
        valueSHORTNESS_OF_BREATHTextView = itemView.findViewById(R.id.valueSHORTNESS_OF_BREATHTextView);

        labelSWALLOWING_DIFFICULTYTextView = itemView.findViewById(R.id.labelSWALLOWING_DIFFICULTYTextView);
        valueSWALLOWING_DIFFICULTYTextView = itemView.findViewById(R.id.valueSWALLOWING_DIFFICULTYTextView);

        labelCHEST_PAINTextView = itemView.findViewById(R.id.labelCHEST_PAINTextView);
        valueCHEST_PAINTextView = itemView.findViewById(R.id.valueCHEST_PAINTextView);


    }

    public void bindData(LungAssessmentData lungData) {
        labelGenderTextView.setText("Gender");
        valueGenderTextView.setText(lungData.getGENDER());

        labelAgeTextView.setText("Age");
        valueAgeTextView.setText(lungData.getAGE());

        labelSMOKINGTextView.setText("Patient Smokes");
        valueSMOKINGTextView.setText(lungData.getSMOKING());

        labelYELLOW_FINGERSTextView.setText("Patient has Yellow Fingers");
        valueYELLOW_FINGERSTextView.setText(lungData.getYELLOW_FINGERS());

        labelANXIETYTextView.setText("Patient has Anxiety");
        valueANXIETYTextView.setText(lungData.getANXIETY());

        labelPEER_PRESSURETextView.setText("Patient experiences Peer Pressure");
        valuePEER_PRESSURETextView.setText(lungData.getPEER_PRESSURE());

        labelCHRONIC_DISEASETextView.setText("Patient has Chronic Disease");
        valueCHRONIC_DISEASETextView.setText(lungData.getCHRONIC_DISEASE());

        labelFATIGUETextView.setText("Patient experiences Fatigue");
        valueFATIGUETextView.setText(lungData.getFATIGUE());

        labelALLERGYTextView.setText("Patient has Allergies");
        valueALLERGYTextView.setText(lungData.getALLERGY());

        labelWHEEZINGTextView.setText("Patient experiences Wheezing");
        valueWHEEZINGTextView.setText(lungData.getWHEEZING());

        labelALCOHOL_CONSUMINGTextView.setText("Patient Consumes Alcohol");
        valueALCOHOL_CONSUMINGTextView.setText(lungData.getALCOHOL_CONSUMING());

        labelCOUGHINGTextView.setText("Patient has Coughing");
        valueCOUGHINGTextView.setText(lungData.getCOUGHING());

        labelSHORTNESS_OF_BREATHTextView.setText("Patient experiences Shortness of Breath");
        valueSHORTNESS_OF_BREATHTextView.setText(lungData.getSHORTNESS_OF_BREATH());

        labelSWALLOWING_DIFFICULTYTextView.setText("Patient has Swallowing Difficulty");
        valueSWALLOWING_DIFFICULTYTextView.setText(lungData.getSWALLOWING_DIFFICULTY());

        labelCHEST_PAINTextView.setText("Patient experiences Chest Pain");
        valueCHEST_PAINTextView.setText(lungData.getCHEST_PAIN());
    }
}

