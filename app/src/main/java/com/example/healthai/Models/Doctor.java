package com.example.healthai.Models;

public class Doctor {
    private String documentId;
    private String doctorName;

    public Doctor(String documentId, String doctorName) {
        this.documentId = documentId;
        this.doctorName = doctorName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getDocumentId() {
        return documentId;
    }
}