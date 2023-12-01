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
// Constructors, getters, and setters

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}