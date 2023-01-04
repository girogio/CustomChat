package com.girogio2103.client.gui.overlay.components;

import com.girogio2103.CustomChat;
import com.girogio2103.common.MqttConnection;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;

public class ConnStatusIndicator {
    private static final ResourceLocation subscribedIndicator = new ResourceLocation(CustomChat.MOD_ID,"textures/hud/indicator_subscribed.png");

    private static final ResourceLocation disconnectedIndicator = new ResourceLocation(CustomChat.MOD_ID,"textures/hud/indicator_disconnected.png");

    private static final ResourceLocation unsubscribedIndicator = new ResourceLocation(CustomChat.MOD_ID,"textures/hud/indicator_unsubscribed.png");

    public void render(PoseStack matrixStack, int x, int y){
        matrixStack.pushPose();
        if(!MqttConnection.isConnected()){
            RenderSystem.setShaderTexture(0, disconnectedIndicator);
        } else {
            RenderSystem.setShaderTexture(0, MqttConnection.isSubscribed ? subscribedIndicator : unsubscribedIndicator);
        }
        GuiComponent.blit(matrixStack, x, y, 0, 0, 20, 18, 20, 18);
        matrixStack.popPose();
    }

}
