package com.girogio2103.config;

import net.minecraftforge.common.ForgeConfigSpec;

// import mod config annotation

public class CustomChatClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<String> API_URL;

    static{
        BUILDER.push("general");

        API_URL = BUILDER.comment("The URL of the server to send the messages to")
                .define("API URL", "https://en3hys2kkjxro.x.pipedream.net");

        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}

