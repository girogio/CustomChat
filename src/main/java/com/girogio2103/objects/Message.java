package com.girogio2103.objects;

public class Message {

    private String sender_username;

    private String sender_UUID;

    private String message;

    public Message(String sender_username, String sender_UUID, String message) {
        this.sender_username = sender_username;
        this.sender_UUID = sender_UUID;
        this.message = message;
    }

    public String getSender_username() {
        return sender_username;
    }

    public void setSender_username(String sender_username) {
        this.sender_username = sender_username;
    }

    public String getSender_UUID() {
        return sender_UUID;
    }

    public void setSender_UUID(String sender_UUID) {
        this.sender_UUID = sender_UUID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
