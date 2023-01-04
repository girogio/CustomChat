package com.girogio2103.common;

import com.girogio2103.client.config.CustomChatClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import org.eclipse.paho.client.mqttv3.*;

import java.lang.reflect.InaccessibleObjectException;

public class MqttConnection {

    private static final MqttConnectOptions connOpts = new MqttConnectOptions();

    public static MqttAsyncClient asyncPublisher = null;

    private static boolean subscribed = false;


    public MqttConnection() {
    }

    public static void toggleConnection() {
        if (isConnected()) {
            disconnect();
        } else {
            connect();
        }
    }

    public static void sendMessage(String message, String topic) {
        if (isConnected() && isSubscribed()) {
            try {
                MqttMessage mqttMessage = new MqttMessage();
                mqttMessage.setPayload(message.getBytes());
                mqttMessage.setQos(2);
                mqttMessage.setRetained(false);
                asyncPublisher.publish(topic, mqttMessage);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }


    public static void toggleSubscription(String topic) throws MqttException {
        if (isConnected()) {
            if (subscribed) {
                unsubscribe(topic);
            } else {
                subscribe(topic);
            }
        }
    }

    public static boolean isConnected() {
        if (asyncPublisher != null) {
            return asyncPublisher.isConnected();
        } else {
            return false;
        }
    }

    public static boolean isSubscribed() {
        return subscribed;
    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                asyncPublisher.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public static void connect() {

        connOpts.setAutomaticReconnect(true);
        connOpts.setKeepAliveInterval(1000);
        connOpts.setCleanSession(true);
        connOpts.setConnectionTimeout(10);

        assert Minecraft.getInstance().player != null;
        String uuid = Minecraft.getInstance().player.getStringUUID();

        try {
            asyncPublisher = new MqttAsyncClient(CustomChatClientConfig.MQTT_BROKER.get(), uuid);
            asyncPublisher.connect(connOpts, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    try {
                        subscribe(CustomChatClientConfig.MQTT_TOPIC.get());
                    } catch (MqttException ignored) {

                    }
                    assert Minecraft.getInstance().player != null;
                    Minecraft.getInstance().player.playNotifySound(SoundEvents.UI_TOAST_IN, SoundSource.MASTER, 0.5F, 1F);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    System.out.println("Failed to connect to MQTT broker");
                }
            });
        } catch (MqttException | InaccessibleObjectException e) {
            e.printStackTrace();
        }
    }

    public static void subscribe(String topic) throws MqttException {
        if (asyncPublisher != null) {
            if (asyncPublisher.isConnected()) {
                asyncPublisher.subscribe(topic, 2, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        if (CustomChatClientConfig.NOTIFICATIONS.get()) {
                            if (Minecraft.getInstance().player != null) {
                                Minecraft.getInstance().player.displayClientMessage(new TextComponent("Subscribed to " + topic), true);
                            }
                        }
                        subscribed = true;
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        if (CustomChatClientConfig.NOTIFICATIONS.get()) {
                            if (Minecraft.getInstance().player != null) {
                                Minecraft.getInstance().player.displayClientMessage(new TextComponent("Failed to subscribe to " + topic), true);
                            }
                        }
                    }
                }).waitForCompletion();

                asyncPublisher.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {
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
                        Minecraft.getInstance().player.sendMessage(new TextComponent(CustomChatClientConfig.PLAYER_PREFIX.get() + message.toString()), Minecraft.getInstance().player.getUUID());
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {

                    }
                });
            }
        } else {
            connect();
        }

    }

    public static void unsubscribe(String topic) throws MqttException {
        if (asyncPublisher.isConnected()) {
            asyncPublisher.unsubscribe(topic, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    if (CustomChatClientConfig.NOTIFICATIONS.get()) {
                        if (Minecraft.getInstance().player != null) {
                            Minecraft.getInstance().player.displayClientMessage(new TextComponent("Unsubscribed from " + topic), true);
                        }
                    }
                    subscribed = false;
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    if (CustomChatClientConfig.NOTIFICATIONS.get()) {
                        if (Minecraft.getInstance().player != null) {
                            Minecraft.getInstance().player.displayClientMessage(new TextComponent("Failed to unsubscribe from " + topic), true);
                        }
                    }
                }

            }).waitForCompletion();

        }
    }
}
