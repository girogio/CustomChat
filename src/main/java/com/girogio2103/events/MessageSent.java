package com.girogio2103.events;

import com.girogio2103.CustomChat;
import com.girogio2103.client.KeyInit;
import com.girogio2103.config.CustomChatClientConfig;
import com.girogio2103.objects.Message;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Mod.EventBusSubscriber(modid = "custom_chat", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public class MessageSent {


    @SubscribeEvent
        public static void messageSent(ClientChatEvent event) {

            HttpClient client = HttpClient.newHttpClient();
            Gson gson = new Gson();

            assert Minecraft.getInstance().player != null;
            String username = Minecraft.getInstance().player.getName().getString();
            String uuid = Minecraft.getInstance().player.getUUID().toString();

            if(CustomChat.isCustomChatOpen) {

                Message message = new Message(username, uuid, event.getMessage());

                String jsonBody = gson.toJson(message);

                var body = HttpRequest.BodyPublishers.ofString(jsonBody);

                var request = HttpRequest.newBuilder(
                                URI.create(CustomChatClientConfig.API_URL.get()))
                        .header("accept", "application/json")
                        .method("POST", body)
                        .build();

                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                event.setCanceled(true);
            }

        }

    }
