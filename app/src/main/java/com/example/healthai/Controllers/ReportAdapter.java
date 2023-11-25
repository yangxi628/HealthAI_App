package com.example.healthai.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthai.Models.AssessmentData;
import com.example.healthai.Models.LungAssessmentData;
import com.example.healthai.R;
import com.google.android.material.imageview.ShapeableImageView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {

    Context context;
    ArrayList<AssessmentData> assessmentArrayList;
    public ReportAdapter(Context context, ArrayList<AssessmentData> assessmentArrayList) {
        this.context = context;
        this.assessmentArrayList = assessmentArrayList;
    }

    @Override

    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(context).inflate(R.layout.square_list_item,parent, false);

            return new ReportViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReportAdapter.ReportViewHolder holder, int position){
        AssessmentData assessmentData = assessmentArrayList.get(position);

        switch (assessmentData.getType()) {
            case "lung":
                holder.listImage.setImageResource(R.drawable.lung);
                holder.title.setText("Lung Cancer Prediction");
                holder.outcome.setText(assessmentData.getResult().equals("1") ?
                        "Bad News, Lung cancer is likely" : "Good News, Lung cancer is unlikely");
                holder.date.setText(assessmentData.getDate());
                break;

            // Add cases for other types (e.g., diabetes, heart)
            case "diabetes":
                holder.listImage.setImageResource(R.drawable.colon);
                holder.title.setText("Diabetes Prediction");
                holder.outcome.setText(assessmentData.getResult().equals("1") ?
                        "Bad News, Diabetes is likely" : "Good News, Diabetes is unlikely");
                holder.date.setText(assessmentData.getDate());

                break;

            case "heart":
                holder.listImage.setImageResource(R.drawable.heart);
                holder.title.setText("Heart Disease Prediction");
                holder.outcome.setText(assessmentData.getResult().equals("1") ?
                        "Bad News, Heart Disease is likely" : "Good News, Heart Disease is unlikely");
                holder.date.setText(assessmentData.getDate());
                break;
        }
    }

    @Override
    public int getItemCount(){
        return assessmentArrayList.size();
    }

    public static class ReportViewHolder extends RecyclerView.ViewHolder{
        TextView title, date, outcome;
        ShapeableImageView listImage;
        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            outcome = itemView.findViewById(R.id.outcome);
            date = itemView.findViewById(R.id.date);
            listImage = itemView.findViewById(R.id.listImage);
        }
    }

}
