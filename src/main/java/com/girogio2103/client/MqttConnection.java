package com.girogio2103.client;

import com.girogio2103.config.CustomChatClientConfig;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttConnection {

    public static IMqttAsyncClient asyncPublisher;

    public MqttConnection() { }

    public static void init() throws MqttException {
        asyncPublisher = new MqttAsyncClient(CustomChatClientConfig.MQTT_BROKER.get(), CustomChatClientConfig.MQTT_CLIENT_ID.get());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);
        options.setConnectionTimeout(10);
        asyncPublisher.connect(options).waitForCompletion();
    }
}
