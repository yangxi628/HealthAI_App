package com.example.healthai.Models;

public class Insurance {
    private String documentId;
    private String insuranceName;

    public Insurance(String documentId, String insuranceName) {
        this.documentId = documentId;
        this.insuranceName = insuranceName;
    }

    public String getInsuranceName() {
        return insuranceName;
    }
    public String getDocumentId() {
        return documentId;
    }
}