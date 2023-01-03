package com.girogio2103.objects;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.StandardCharsets;

public class Message {

    private final String sender_username;

    private final String message;

    public Message(String sender_username, String message) {
        this.sender_username = sender_username;
        this.message = message;
    }


    public void send(IMqttAsyncClient asyncPublisher, String topic) throws Exception {
        MqttMessage msg = new MqttMessage((this.sender_username + ": " + this.message).getBytes(StandardCharsets.UTF_8));
        msg.setQos(0);
        msg.setRetained(false);
        asyncPublisher.publish(topic, msg);

    }
}
