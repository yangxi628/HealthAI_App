package com.example.healthai.Models;


import java.util.HashMap;
import java.util.Map;

public class MedicalHistory {

    private String bloodPressure;
    private String chestPainType;
    private String cholesterol;
    private String ekgResults;
    private String exerciseAngina;
    private String fbsOver120;
    private String maxHR;
    private String numberOfVesselsFluro;
    private String stDepression;
    private String slopeOfST;
    private String thallium;
    private String age;
    private String date;
    private String patient;
    private String result;
    private String sex;
    private String type;

    // Default constructor required for Firebase
    public MedicalHistory() {
    }

    // Constructor to initialize the object with provided data
    public MedicalHistory(String bloodPressure, String chestPainType, String cholesterol,
                          String ekgResults, String exerciseAngina, String fbsOver120,
                          String maxHR, String numberOfVesselsFluro, String stDepression,
                          String slopeOfST, String thallium, String age, String date,
                          String patient, String result, String sex, String type) {
        this.bloodPressure = bloodPressure;
        this.chestPainType = chestPainType;
        this.cholesterol = cholesterol;
        this.ekgResults = ekgResults;
        this.exerciseAngina = exerciseAngina;
        this.fbsOver120 = fbsOver120;
        this.maxHR = maxHR;
        this.numberOfVesselsFluro = numberOfVesselsFluro;
        this.stDepression = stDepression;
        this.slopeOfST = slopeOfST;
        this.thallium = thallium;
        this.age = age;
        this.date = date;
        this.patient = patient;
        this.result = result;
        this.sex = sex;
        this.type = type;
    }

    // Function to convert the object to a Map for Firestore
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("bloodPressure", bloodPressure);
        map.put("chestPainType", chestPainType);
        map.put("cholesterol", cholesterol);
        map.put("ekgResults", ekgResults);
        map.put("exerciseAngina", exerciseAngina);
        map.put("fbsOver120", fbsOver120);
        map.put("maxHR", maxHR);
        map.put("numberOfVesselsFluro", numberOfVesselsFluro);
        map.put("stDepression", stDepression);
        map.put("slopeOfST", slopeOfST);
        map.put("thallium", thallium);
        map.put("age", age);
        map.put("date", date);
        map.put("patient", patient);
        map.put("result", result);
        map.put("sex", sex);
        map.put("type", type);
        return map;
    }

    

}
