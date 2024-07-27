package com.codexnovas.e_ndore.employeeSide;

public class ChatMessage {
    private String sender_id;
    private String message;
    private com.google.firebase.Timestamp timestamp;

    public ChatMessage() {}

    public ChatMessage(String sender_id, String message, com.google.firebase.Timestamp timestamp) {
        this.sender_id = sender_id;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public com.google.firebase.Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(com.google.firebase.Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
