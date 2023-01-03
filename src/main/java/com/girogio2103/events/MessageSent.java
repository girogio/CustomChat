package com.girogio2103.events;

import com.girogio2103.CustomChat;
import com.girogio2103.client.MqttConnection;
import com.girogio2103.objects.Message;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "custom_chat", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public class MessageSent {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientChat(ClientChatEvent event) throws Exception {

        if (CustomChat.isCustomChatOpen) {

            assert Minecraft.getInstance().player != null;
            String playerName = Minecraft.getInstance().player.getName().getString();
            String msg = event.getMessage();

            Message message = new Message(playerName, msg);

            if (msg.startsWith("/")) {
                return;
            } else {
                event.setCanceled(true);
                MqttConnection.setClientId(playerName);
                message.send(MqttConnection.asyncPublisher, "chat/general");
            }
            event.setCanceled(true);
        }
    }
}
