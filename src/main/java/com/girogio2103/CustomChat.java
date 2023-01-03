package com.girogio2103;

import com.girogio2103.client.KeyInit;
import com.girogio2103.client.MqttConnection;
import com.girogio2103.config.CustomChatClientConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.eclipse.paho.client.mqttv3.MqttException;


@Mod("custom_chat")
public class CustomChat {

    public CustomChat() throws MqttException {
        KeyInit.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CustomChatClientConfig.SPEC, "custom_chat-client.toml");
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static boolean isCustomChatOpen = false;


    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) throws MqttException {
        if (KeyInit.toggleChat.consumeClick()) {

            if(isCustomChatOpen){
                MqttConnection.unsubscribe(CustomChatClientConfig.MQTT_TOPIC.get());
            }else {
                MqttConnection.subscribe(CustomChatClientConfig.MQTT_TOPIC.get());
            }

            isCustomChatOpen = !isCustomChatOpen;
        }
    }
}
