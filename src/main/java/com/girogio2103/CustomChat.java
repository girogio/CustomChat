package com.girogio2103;

import com.girogio2103.client.KeyInit;
import com.girogio2103.config.CustomChatClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;



@Mod("custom_chat")
public class CustomChat {

    public CustomChat() {
        KeyInit.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CustomChatClientConfig.SPEC, "custom_chat-client.toml");
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static boolean isCustomChatOpen = true;

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (KeyInit.toggleChat.consumeClick()) {
            isCustomChatOpen = !isCustomChatOpen;
            assert Minecraft.getInstance().player != null;
            String out = I18n.get(isCustomChatOpen ? "mod.custom_chat.popup.toggle.activate" : "mod.custom_chat.popup.toggle.deactivate");
            Minecraft.getInstance().player.displayClientMessage(new TextComponent(out), true);
            Minecraft.getInstance().player.playSound(net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK, 1.0F, 1.0F);
        }
    }

}
