package com.example.healthai.Controllers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthai.Models.AssessmentData;
import com.example.healthai.Models.DiabetesAssessmentData;
import com.example.healthai.Models.HeartAssessmentData;
import com.example.healthai.Models.LungAssessmentData;
import com.example.healthai.R;
import com.example.healthai.Views.MedicalHistoryActivity;
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
    public void onBindViewHolder(ReportAdapter.ReportViewHolder holder, @SuppressLint("RecyclerView") int position){

        AssessmentData currentAssessment = assessmentArrayList.get(position);

        switch (currentAssessment.getType()) {
            case "lung":
                holder.listImage.setImageResource(R.drawable.lung);
                holder.title.setText("Lung Cancer Prediction");
                holder.outcome.setText(currentAssessment.getResult().equals("1") ?
                        "Bad News, Lung cancer is likely" : "Good News, Lung cancer is unlikely");
                holder.date.setText(currentAssessment.getDate());
                break;

            case "diabetes":
                holder.listImage.setImageResource(R.drawable.colon);
                holder.title.setText("Diabetes Prediction");
                holder.outcome.setText(currentAssessment.getResult().equals("1") ?
                        "Bad News, Diabetes is likely" : "Good News, Diabetes is unlikely");
                holder.date.setText(currentAssessment.getDate());
                break;

            case "heart":
                holder.listImage.setImageResource(R.drawable.heart);
                holder.title.setText("Heart Disease Prediction");
                holder.outcome.setText(currentAssessment.getResult().equals("1") ?
                        "Bad News, Heart Disease is likely" : "Good News, Heart Disease is unlikely");
                holder.date.setText(currentAssessment.getDate());
                break;
        }


        holder.listImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = currentAssessment.getType();
                switch (type) {
                    case "lung":
                        Intent lungIntent = new Intent(context, MedicalHistoryActivity.class);
                        lungIntent.putExtra("assessmentType", currentAssessment.getType());
                        lungIntent.putExtra("assessmentDate", currentAssessment.getDate());
                        lungIntent.putExtra("assessmentResult", currentAssessment.getResult());

                        lungIntent.putExtra("assessmentGender", ((LungAssessmentData) currentAssessment).getGENDER());
                        lungIntent.putExtra("assessmentAge", ((LungAssessmentData) currentAssessment).getAGE());
                        lungIntent.putExtra("assessmentSmoking", ((LungAssessmentData) currentAssessment).getSMOKING());
                        lungIntent.putExtra("assessmentYellowFingers", ((LungAssessmentData) currentAssessment).getYELLOW_FINGERS());
                        lungIntent.putExtra("assessmentAnxiety", ((LungAssessmentData) currentAssessment).getANXIETY());
                        lungIntent.putExtra("assessmentPeerPressure", ((LungAssessmentData) currentAssessment).getPEER_PRESSURE());
                        lungIntent.putExtra("assessmentChronicDisease", ((LungAssessmentData) currentAssessment).getCHRONIC_DISEASE());
                        lungIntent.putExtra("assessmentFatigue", ((LungAssessmentData) currentAssessment).getFATIGUE());
                        lungIntent.putExtra("assessmentAllergy", ((LungAssessmentData) currentAssessment).getALLERGY());
                        lungIntent.putExtra("assessmentWheezing", ((LungAssessmentData) currentAssessment).getWHEEZING());
                        lungIntent.putExtra("assessmentAlcoholConsuming", ((LungAssessmentData) currentAssessment).getALCOHOL_CONSUMING());
                        lungIntent.putExtra("assessmentCoughing", ((LungAssessmentData) currentAssessment).getCOUGHING());
                        lungIntent.putExtra("assessmentShortnessOfBreath", ((LungAssessmentData) currentAssessment).getSHORTNESS_OF_BREATH());
                        lungIntent.putExtra("assessmentSwallowingDifficulty", ((LungAssessmentData) currentAssessment).getSWALLOWING_DIFFICULTY());
                        lungIntent.putExtra("assessmentChestPain", ((LungAssessmentData) currentAssessment).getCHEST_PAIN());

                        context.startActivity(lungIntent);
                        break;
                    case "heart":
                        Intent heartIntent = new Intent(context, MedicalHistoryActivity.class);
                        heartIntent.putExtra("assessmentType", currentAssessment.getType());
                        heartIntent.putExtra("assessmentDate", currentAssessment.getDate());
                        heartIntent.putExtra("assessmentResult", currentAssessment.getResult());

                        heartIntent.putExtra("assessmentAge", ((HeartAssessmentData) currentAssessment).getAge());
                        heartIntent.putExtra("assessmentSex", ((HeartAssessmentData) currentAssessment).getSex());
                        heartIntent.putExtra("assessmentChestPainType", ((HeartAssessmentData) currentAssessment).getChestPainType());
                        heartIntent.putExtra("assessmentBP", ((HeartAssessmentData) currentAssessment).getBP());
                        heartIntent.putExtra("assessmentCholesterol", ((HeartAssessmentData) currentAssessment).getCholesterol());
                        heartIntent.putExtra("assessmentFBSOver120", ((HeartAssessmentData) currentAssessment).getFBSOver120());
                        heartIntent.putExtra("assessmentEKGResults", ((HeartAssessmentData) currentAssessment).getEKGResults());
                        heartIntent.putExtra("assessmentMaxHR", ((HeartAssessmentData) currentAssessment).getMaxHR());
                        heartIntent.putExtra("assessmentExerciseAngina", ((HeartAssessmentData) currentAssessment).getExerciseAngina());
                        heartIntent.putExtra("assessmentSTdepression", ((HeartAssessmentData) currentAssessment).getSTdepression());
                        heartIntent.putExtra("assessmentSlopeOfST", ((HeartAssessmentData) currentAssessment).getSlopeOfST());
                        heartIntent.putExtra("assessmentNumberOfVesselsFluro", ((HeartAssessmentData) currentAssessment).getNumberOfVesselsFluro());
                        heartIntent.putExtra("assessmentThallium", ((HeartAssessmentData) currentAssessment).getThallium());

                        context.startActivity(heartIntent);
                        break;
                    case "diabetes":
                        Intent diabetesIntent = new Intent(context, MedicalHistoryActivity.class);
                        diabetesIntent.putExtra("assessmentType", currentAssessment.getType());
                        diabetesIntent.putExtra("assessmentDate", currentAssessment.getDate());
                        diabetesIntent.putExtra("assessmentResult", currentAssessment.getResult());

                        diabetesIntent.putExtra("assessmentPregnancies", ((DiabetesAssessmentData) currentAssessment).getPregnancies());
                        diabetesIntent.putExtra("assessmentGlucose", ((DiabetesAssessmentData) currentAssessment).getGlucose());
                        diabetesIntent.putExtra("assessmentBloodPressure", ((DiabetesAssessmentData) currentAssessment).getBloodPressure());
                        diabetesIntent.putExtra("assessmentSkinThickness", ((DiabetesAssessmentData) currentAssessment).getSkinThickness());
                        diabetesIntent.putExtra("assessmentInsulin", ((DiabetesAssessmentData) currentAssessment).getInsulin());
                        diabetesIntent.putExtra("assessmentBMI", ((DiabetesAssessmentData) currentAssessment).getBMI());
                        diabetesIntent.putExtra("assessmentDiabetesPedigreeFunction", ((DiabetesAssessmentData) currentAssessment).getDiabetesPedigreeFunction());
                        diabetesIntent.putExtra("assessmentAge", ((DiabetesAssessmentData) currentAssessment).getAge());

                        context.startActivity(diabetesIntent);
                        break;
                    default:
                        break;
                }
            }
        });
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
