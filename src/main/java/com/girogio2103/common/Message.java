package com.girogio2103.common;

public class Message {

    private final String sender_username;

    private final String message;

    public Message(String sender_username, String message) {
        this.sender_username = sender_username;
        this.message = message;
    }

    public void send(String topic) {
        String payload = "";
        payload += sender_username;
        payload += this.message;
        MqttConnection.sendMessage(payload, topic);
    }
}
