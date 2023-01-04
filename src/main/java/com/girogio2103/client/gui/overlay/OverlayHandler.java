package com.girogio2103.client.gui.overlay;

import com.girogio2103.client.config.CustomChatClientConfig;
import com.girogio2103.client.gui.overlay.components.ConnStatusIndicator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = "custom_chat", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class OverlayHandler {

    static ConnStatusIndicator connStatusIndicator = new ConnStatusIndicator();

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onRenderGameOverlay(final RenderGameOverlayEvent event) {
        if (CustomChatClientConfig.SHOW_HUD_INDICATOR.get()) {
            connStatusIndicator.render(event.getMatrixStack(), 2, 2);
        }
    }
}