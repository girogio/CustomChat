package com.girogio2103;

import com.girogio2103.client.KeyInit;
import com.girogio2103.client.config.CustomChatClientConfig;
import com.girogio2103.client.gui.screens.OptionScreen;
import com.girogio2103.common.MqttConnection;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.eclipse.paho.client.mqttv3.MqttException;


@Mod("custom_chat")
public class CustomChat {

    public static String MOD_ID = "custom_chat";

    public CustomChat() {
        KeyInit.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CustomChatClientConfig.SPEC, "custom_chat-client.toml");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) throws MqttException {
        if (KeyInit.toggleChat.consumeClick()) {
            MqttConnection.toggleSubscription(CustomChatClientConfig.MQTT_TOPIC.get());
        }

        if(KeyInit.toggleIndicator.consumeClick()){
            CustomChatClientConfig.SHOW_HUD_INDICATOR.set(!CustomChatClientConfig.SHOW_HUD_INDICATOR.get());
        }

        if(KeyInit.toggleMenu.consumeClick()){
            OptionScreen.show();
        }

    }
}
