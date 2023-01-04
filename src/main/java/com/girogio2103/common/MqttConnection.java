package com.girogio2103.common;

import com.girogio2103.client.config.CustomChatClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.eclipse.paho.client.mqttv3.*;

public class MqttConnection {

    private static final MqttConnectOptions connOpts = new MqttConnectOptions();

    public static MqttAsyncClient asyncPublisher = null;


    public MqttConnection() {
    }

    public static void init() throws MqttException {

        connOpts.setAutomaticReconnect(true);
        connOpts.setKeepAliveInterval(1000);
        connOpts.setCleanSession(true);
        connOpts.setConnectionTimeout(10);

        assert Minecraft.getInstance().player != null;
        String uuid = Minecraft.getInstance().player.getStringUUID();

        asyncPublisher = new MqttAsyncClient(CustomChatClientConfig.MQTT_BROKER.get(), uuid);
        asyncPublisher.connect(connOpts, null, new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                System.out.println("Connected to MQTT broker");
                try {
                    subscribe(CustomChatClientConfig.MQTT_TOPIC.get());
                } catch (MqttException ignored) {

                }
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                System.out.println("Failed to connect to MQTT broker");
            }
        });
    }

    public static void subscribe(String topic) throws MqttException {
        if (asyncPublisher != null) {
            if (asyncPublisher.isConnected()) {
                asyncPublisher.subscribe(topic, 2, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        if (Minecraft.getInstance().player != null) {
                            Minecraft.getInstance().player.displayClientMessage(new TextComponent("Subscribed to " + topic), true);
                        }
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        if (Minecraft.getInstance().player != null) {
                            Minecraft.getInstance().player.displayClientMessage(new TextComponent("Failed to subscribe to " + topic), true);
                        }
                    }
                }).waitForCompletion();

                asyncPublisher.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {
                        assert Minecraft.getInstance().player != null;
                        Minecraft.getInstance().player.sendMessage(new TextComponent("Connection lost"), Minecraft.getInstance().player.getUUID());
                    }

                    @Override
                    public void messageArrived(String topic, MqttMessage message) {

                        assert Minecraft.getInstance().player != null;

                        if (CustomChatClientConfig.PINGS.get()) {
                            System.out.println(Minecraft.getInstance().player.getName().getString());
                            if (message.toString().contains("@" + Minecraft.getInstance().player.getName().getString())) {
                                Minecraft.getInstance().player.playNotifySound(SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.AMBIENT, 0.1F, 1.0F);
                            }
                        }
                        Minecraft.getInstance().player.sendMessage(new TextComponent(message.toString()), Minecraft.getInstance().player.getUUID());
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {

                    }
                });
            }
        } else {
            init();
        }

    }

    public static void unsubscribe(String topic) throws MqttException {
        if (asyncPublisher.isConnected()) {
            asyncPublisher.unsubscribe(topic, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    if (Minecraft.getInstance().player != null) {
                        Minecraft.getInstance().player.displayClientMessage(new TextComponent("Unsubscribed from " + topic), true);
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    assert Minecraft.getInstance().player != null;
                    Minecraft.getInstance().player.displayClientMessage(new TextComponent("Failed to unsubscribe from " + topic), true);
                }
            }).waitForCompletion();

        } else {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().player.displayClientMessage(new TextComponent("Failed to unsubscribe from " + topic), true);
        }
    }
}
