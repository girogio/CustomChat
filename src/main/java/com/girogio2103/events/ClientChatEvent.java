package com.girogio2103.events;

import com.girogio2103.CustomChat;
import com.girogio2103.client.MqttConnection;
import com.girogio2103.objects.Message;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "custom_chat", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public class ClientChatEvent {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onClientChat(net.minecraftforge.client.event.ClientChatEvent event) throws Exception {
        if (CustomChat.isCustomChatOpen) {
            assert Minecraft.getInstance().player != null;
            String playerName = Minecraft.getInstance().player.getName().getString();
            String msg = event.getMessage();

            Message message = new Message(playerName, msg);

            if (msg.startsWith("/")) {
                return;
            } else {
                event.setCanceled(true);
                message.send(MqttConnection.asyncPublisher);
            }
            event.setCanceled(true);
        }
    }
}
