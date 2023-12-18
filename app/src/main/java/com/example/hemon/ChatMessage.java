package com.example.hemon;

public class ChatMessage {

    private String message;
    private String sender;
    private long timestamp;  // Use this to order messages

    // Empty constructor needed for Firebase
    public ChatMessage() {}

    public ChatMessage(String message, String sender) {
        this.message = message;
        this.sender = sender;
        this.timestamp = System.currentTimeMillis();
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

