package com.example.healthai.Controllers.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthai.Controllers.Holders.DiabetesViewHolder;
import com.example.healthai.Controllers.Holders.GenericViewHolder;
import com.example.healthai.Controllers.Holders.HeartViewHolder;
import com.example.healthai.Controllers.Holders.LungViewHolder;
import com.example.healthai.Models.Assessment.AssessmentData;
import com.example.healthai.Models.Assessment.DiabetesAssessmentData;
import com.example.healthai.Models.Assessment.HeartAssessmentData;
import com.example.healthai.Models.Assessment.LungAssessmentData;
import com.example.healthai.R;

import java.util.List;

public class MedicalHistoryAdapter extends  RecyclerView.Adapter<GenericViewHolder> {
    private List<AssessmentData> dataList;
    private Context context;

    public MedicalHistoryAdapter(Context context, List<AssessmentData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        String assessmentType = dataList.get(viewType).getType();
        switch (assessmentType) {
            case "lung":
                view = LayoutInflater.from(context).inflate(R.layout.lung_medical_item, parent, false);
                return new LungViewHolder(view);

            case "heart":
                view = LayoutInflater.from(context).inflate(R.layout.heart_medical_item, parent, false);
                return new HeartViewHolder(view);

            case "diabetes":
                view = LayoutInflater.from(context).inflate(R.layout.diabetes_medical_item, parent, false);
                return new DiabetesViewHolder(view);

            default:
                throw new IllegalArgumentException("Invalid assessment type: " + assessmentType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull GenericViewHolder holder, int position) {
        AssessmentData data = dataList.get(position);

        if (data.getType().equals("lung")) {
            LungViewHolder lungViewHolder = (LungViewHolder) holder;
            LungAssessmentData lungData = (LungAssessmentData) data;
            lungViewHolder.bindData(lungData);

        } else if (data.getType().equals("heart")) {
            HeartViewHolder heartViewHolder = (HeartViewHolder) holder;
            HeartAssessmentData heartData = (HeartAssessmentData) data;
            heartViewHolder.bindData(heartData);

        } else if (data.getType().equals("diabetes")) {
            DiabetesViewHolder diabetesViewHolder = (DiabetesViewHolder) holder;
            DiabetesAssessmentData diabetesData = (DiabetesAssessmentData) data;
            diabetesViewHolder.bindData(diabetesData);
        }
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }


}