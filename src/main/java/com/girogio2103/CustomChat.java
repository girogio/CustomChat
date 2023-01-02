package com.girogio2103;

import com.girogio2103.client.KeyInit;
import com.girogio2103.config.CustomChatClientConfig;
import com.girogio2103.objects.Message;
import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;



@Mod("CustomChat")
public class CustomChat {

    HttpClient client = HttpClient.newHttpClient();
    Gson gson = new Gson();

    boolean isCustomChatOpen = true;

    public CustomChat() {

        KeyInit.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CustomChatClientConfig.SPEC, "dickspchat-client.toml");
        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (KeyInit.toggleChat.consumeClick()) {
            isCustomChatOpen = !isCustomChatOpen;
            assert Minecraft.getInstance().player != null;
            String out = I18n.get(isCustomChatOpen ? "mod.dicksp_chat.popup.toggle.activate" : "mod.dicksp_chat.popup.toggle.deactivate");
            Minecraft.getInstance().player.displayClientMessage(new TextComponent(out), true);
            Minecraft.getInstance().player.playSound(net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK, 1.0F, 1.0F);
        }
    }

        @SubscribeEvent
        @OnlyIn(Dist.CLIENT)
        public void onServerChat(ServerChatEvent event) {

        String instanceUUID = Minecraft.getInstance().getUser().getUuid();
        String eventUUID = event.getPlayer().getUUID().toString();

            if(instanceUUID.equalsIgnoreCase(eventUUID) && isCustomChatOpen) {

                Message message = new Message(event.getUsername(), eventUUID, event.getMessage());

                String jsonBody = gson.toJson(message);

                var body = HttpRequest.BodyPublishers.ofString(jsonBody);

                var request = HttpRequest.newBuilder(
                        URI.create(CustomChatClientConfig.API_URL.get()))
                        .header("accept", "application/json")
                        .method("POST", body)
                        .build();

                try {
                    client.send(request, HttpResponse.BodyHandlers.ofString());
                } catch (IOException | InterruptedException e) {
                    System.out.println("IOException");
                }
                event.setCanceled(true);
            }
     }


}
