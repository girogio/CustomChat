package com.girogio2103.client;

import com.girogio2103.config.CustomChatClientConfig;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttConnection {

    public static IMqttAsyncClient asyncPublisher;

    private static String clientId;

    public MqttConnection() { }

    public static void init() throws MqttException {
        asyncPublisher = new MqttAsyncClient(CustomChatClientConfig.MQTT_BROKER.get(), clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        asyncPublisher.connect(options).waitForCompletion();
    }

    public static void setClientId(String clientId) {
        MqttConnection.clientId = clientId;
    }
}
