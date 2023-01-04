package com.girogio2103.client.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CustomChatClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<String> MQTT_BROKER;
    public static final ForgeConfigSpec.ConfigValue<String> MQTT_TOPIC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> PINGS;

    public static final ForgeConfigSpec.ConfigValue<String> PLAYER_PREFIX;

    public static final ForgeConfigSpec.ConfigValue<Boolean> ADD_MESSAGE_TO_HISTORY;

    public static final ForgeConfigSpec.ConfigValue<Boolean> NOTIFICATIONS;

    public static final ForgeConfigSpec.ConfigValue<Boolean> SHOW_HUD_INDICATOR;

    static{
        BUILDER.push("general");

        MQTT_BROKER = BUILDER.comment("The URL of the MQTT broker to connect to")
                .define("MQTT Broker", "ws://broker.hivemq.com:8000");

        MQTT_TOPIC = BUILDER.comment("The topic to subscribe to")
                .define("MQTT Topic", "chat/general");

        BUILDER.pop();

        BUILDER.push("chat");

        PINGS = BUILDER.comment("Enable pings (if message contains @Player123, Player123 will get a sound notification)")
                .define("Pings", true);

        ADD_MESSAGE_TO_HISTORY = BUILDER.comment("Add message to history (pressing up arrow will show previous messages)")
                .define("Add message to history", true);

        PLAYER_PREFIX = BUILDER.comment("Username prefix (only shows in your client)")
                .define("Player prefix", "");

        BUILDER.pop();

        BUILDER.push("hud");

        SHOW_HUD_INDICATOR = BUILDER.comment("Show a small indicator in the top left corner of the screen to show if the chat is open or not")
                .define("Show HUD Indicator", true);

        BUILDER.pop();

        BUILDER.push("debug");

        NOTIFICATIONS = BUILDER.comment("Show notifications when subscribing or unsubscribing to a topic")
                .define("Notifications", false);

        SPEC = BUILDER.build();
    }
}

