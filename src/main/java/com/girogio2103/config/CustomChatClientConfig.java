package com.girogio2103.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CustomChatClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<String> MQTT_BROKER;

    static{
        BUILDER.push("general");

        MQTT_BROKER = BUILDER.comment("The URL of the MQTT broker to connect to")
                .define("MQTT Broker", "ws://broker.hivemq.com:8000");

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}

