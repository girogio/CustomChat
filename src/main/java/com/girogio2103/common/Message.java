package com.girogio2103.common;

import com.girogio2103.client.config.CustomChatClientConfig;
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


    public void send(IMqttAsyncClient asyncPublisher) throws Exception {
        if(asyncPublisher.isConnected()) {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload((sender_username + ": " + message).getBytes(StandardCharsets.UTF_8));
            mqttMessage.setQos(2);
            mqttMessage.setRetained(false);
            asyncPublisher.publish(CustomChatClientConfig.MQTT_TOPIC.get(), mqttMessage);
        }
    }
}
