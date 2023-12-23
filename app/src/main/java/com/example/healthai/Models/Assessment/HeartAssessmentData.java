package com.example.healthai.Models.Assessment;

import com.example.healthai.Models.Assessment.AssessmentData;

public class HeartAssessmentData implements AssessmentData {
    String age, sex, ChestPainType,BP,Cholesterol,FBSOver120,EKGResults,MaxHR,ExerciseAngina,STdepression,SlopeOfST,NumberOfVesselsFluro,Thallium,date,result;

    public HeartAssessmentData() {
    }
    public HeartAssessmentData(String age, String sex, String chestPainType, String BP, String cholesterol, String FBSOver120, String EKGResults, String maxHR, String exerciseAngina, String STdepression, String slopeOfST, String numberOfVesselsFluro, String thallium, String date, String result) {
        this.age = age;
        this.sex = sex;
        this.ChestPainType = chestPainType;
        this.BP = BP;
        this.Cholesterol = cholesterol;
        this.FBSOver120 = FBSOver120;
        this.EKGResults = EKGResults;
        this.MaxHR = maxHR;
        this.ExerciseAngina = exerciseAngina;
        this.STdepression = STdepression;
        this.SlopeOfST = slopeOfST;
        this.NumberOfVesselsFluro = numberOfVesselsFluro;
        this.Thallium = thallium;
        this.date = date;
        this.result = result;
    }

    @Override
    public String getType() {
        return "heart";
    }

    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public String getChestPainType() {
        return ChestPainType;
    }

    public String getBP() {
        return BP;
    }

    public String getCholesterol() {
        return Cholesterol;
    }

    public String getFBSOver120() {
        return FBSOver120;
    }

    public String getEKGResults() {
        return EKGResults;
    }

    public String getMaxHR() {
        return MaxHR;
    }

    public String getExerciseAngina() {
        return ExerciseAngina;
    }

    public String getSTdepression() {
        return STdepression;
    }

    public String getSlopeOfST() {
        return SlopeOfST;
    }

    public String getNumberOfVesselsFluro() {
        return NumberOfVesselsFluro;
    }

    public String getThallium() {
        return Thallium;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public String getResult() {
        return result;
    }
}
