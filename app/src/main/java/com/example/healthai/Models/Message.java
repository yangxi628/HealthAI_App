package com.example.healthai.Models;

import com.google.firebase.Timestamp;

import java.util.Date;

public class Message {
    public static String SENT_BY_ME = "me";
    public static String SENT_BY_BOT = "bot";

    private String message;
    private String sentBy;
    private Timestamp created;

    public Message(String message, String sentBy, Timestamp created) {
        this.message = message;
        this.sentBy = sentBy;
        this.created = created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
