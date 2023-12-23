package com.example.healthai.Models.Assessment;

import com.example.healthai.Models.Assessment.AssessmentData;

public class LungAssessmentData implements AssessmentData {
    String GENDER, AGE, SMOKING,YELLOW_FINGERS,ANXIETY,PEER_PRESSURE,CHRONIC_DISEASE,FATIGUE,ALLERGY,WHEEZING,ALCOHOL_CONSUMING,COUGHING,SHORTNESS_OF_BREATH,SWALLOWING_DIFFICULTY,CHEST_PAIN,date,result;

    public LungAssessmentData() {}
    public LungAssessmentData(String GENDER, String AGE, String SMOKING, String YELLOW_FINGERS, String ANXIETY, String PEER_PRESSURE, String CHRONIC_DISEASE, String FATIGUE, String ALLERGY, String WHEEZING, String ALCOHOL_CONSUMING, String COUGHING, String SHORTNESS_OF_BREATH, String SWALLOWING_DIFFICULTY, String CHEST_PAIN,String date,String result)
    {
        this.GENDER = GENDER;
        this.AGE = AGE;
        this.SMOKING = SMOKING;
        this.YELLOW_FINGERS = YELLOW_FINGERS;
        this.ANXIETY = ANXIETY;
        this.PEER_PRESSURE = PEER_PRESSURE;
        this.CHRONIC_DISEASE = CHRONIC_DISEASE;
        this.FATIGUE = FATIGUE;
        this.ALLERGY = ALLERGY;
        this.WHEEZING = WHEEZING;
        this.ALCOHOL_CONSUMING = ALCOHOL_CONSUMING;
        this.COUGHING = COUGHING;
        this.SHORTNESS_OF_BREATH = SHORTNESS_OF_BREATH;
        this.SWALLOWING_DIFFICULTY = SWALLOWING_DIFFICULTY;
        this.CHEST_PAIN = CHEST_PAIN;
        this.date = date;
        this.result = result;
    }

    @Override
    public String getType() {
        return "lung";
    }

    public String getGENDER() {
        return GENDER;
    }

    public String getAGE() {
        return AGE;
    }

    public String getSMOKING() {
        return SMOKING;
    }

    public String getYELLOW_FINGERS() {
        return YELLOW_FINGERS;
    }

    public String getANXIETY() {
        return ANXIETY;
    }

    public String getPEER_PRESSURE() {
        return PEER_PRESSURE;
    }

    public String getCHRONIC_DISEASE() {
        return CHRONIC_DISEASE;
    }

    public String getFATIGUE() {
        return FATIGUE;
    }

    public String getALLERGY() {
        return ALLERGY;
    }

    public String getWHEEZING() {
        return WHEEZING;
    }

    public String getALCOHOL_CONSUMING() {
        return ALCOHOL_CONSUMING;
    }

    public String getCOUGHING() {
        return COUGHING;
    }

    public String getSHORTNESS_OF_BREATH() {
        return SHORTNESS_OF_BREATH;
    }

    public String getSWALLOWING_DIFFICULTY() {
        return SWALLOWING_DIFFICULTY;
    }

    public String getCHEST_PAIN() {
        return CHEST_PAIN;
    }

    public String getDate() {
        return date;
    }

    public String getResult() {
        return result;
    }

}
