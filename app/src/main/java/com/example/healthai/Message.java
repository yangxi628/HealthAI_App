package com.example.healthai;

public class Message {

    private String message;
    private String sender;

    public Message(String message, String sender){
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
