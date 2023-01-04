package com.girogio2103.client.gui.screens;

import com.girogio2103.client.config.CustomChatClientConfig;
import com.girogio2103.common.MqttConnection;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public class OptionScreen extends Screen {

    private EditBox topicEditBox;
    private EditBox brokerEditBox;

    private Button saveButton;


    public OptionScreen(Component title) {
        super(title);
    }

    public static void show() {
        Minecraft.getInstance().setScreen(new OptionScreen(new TextComponent(I18n.get("options.title"))));
    }

    @Override
    protected void init() {
        super.init();

        brokerEditBox = new EditBox(this.font, this.width / 2 - 100, this.height / 2 - 60, 200, 20, new TextComponent("Broker"));
        brokerEditBox.setValue(CustomChatClientConfig.MQTT_BROKER.get());
        this.addRenderableWidget(brokerEditBox);

        topicEditBox = new EditBox(this.font, this.width / 2 - 100, this.height / 2 - 20, 200, 20, new TextComponent("Topic"));
        topicEditBox.setValue(CustomChatClientConfig.MQTT_TOPIC.get());
        this.addRenderableWidget(topicEditBox);


        saveButton = new Button(this.width / 2 - 100, this.height / 2 + 10, 200, 20, new TextComponent(I18n.get("structure_block.mode.save")), (button) -> {

            CustomChatClientConfig.MQTT_BROKER.set(brokerEditBox.getValue());
            CustomChatClientConfig.MQTT_TOPIC.set(topicEditBox.getValue());

            MqttConnection.toggleConnection();

        });


        this.addRenderableWidget(saveButton);

        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 2 + 35, 200, 20, new TextComponent("Cancel"), (button) -> this.onClose()));

    }

    @Override
    public void tick() {
        super.tick();

        if (this.topicEditBox.isFocused()) {
            this.topicEditBox.tick();
        }

        if (this.brokerEditBox.isFocused()) {
            this.brokerEditBox.tick();
        }

        String disconnect = I18n.get("mod.custom_chat.gui.disconnect");
        String connect = I18n.get("mod.custom_chat.gui.connect");

        saveButton.setMessage(new TextComponent(!MqttConnection.isConnected() ? disconnect : connect));

        String urlRegex = "^(ws|tcp)://[a-zA-Z0-9]+(.[a-zA-Z0-9]+)*(:[0-9]{1,5})?$";
        if (this.brokerEditBox.getValue().matches(urlRegex)) {
            brokerEditBox.setTextColor(0xFFFFFF);
            saveButton.active = true;
        } else {
            saveButton.setMessage(new TextComponent(I18n.get("mod.custom_chat.gui.invalid_url")));
            brokerEditBox.setTextColor(0xFF5555);
            saveButton.active = false;
        }

        if (this.brokerEditBox.getValue().isEmpty()) {
            this.brokerEditBox.setSuggestion(CustomChatClientConfig.MQTT_BROKER.get());
        } else {
            this.brokerEditBox.setSuggestion("");
        }

        if (this.topicEditBox.getValue().isEmpty()) {
            this.topicEditBox.setSuggestion(CustomChatClientConfig.MQTT_TOPIC.get());
        } else {
            this.topicEditBox.setSuggestion("");
        }
    }

    @Override
    public void render(@NotNull PoseStack pose, int mouseX, int mouseY, float partialTick) {
        // Background is typically rendered first
        this.renderBackground(pose);

        // Render things here before widgets (background textures)

        // Then the widgets if this is a direct child of the Screen
        super.render(pose, mouseX, mouseY, partialTick);

        // Render things after widgets (tooltips)
        drawString(pose, this.font, "Broker", this.width / 2 - 100, this.height / 2 - 70, 0xFFFFFF);
        drawString(pose, this.font, "Topic", this.width / 2 - 100, this.height / 2 - 30, 0xFFFFFF);
    }

    @Override
    public void onClose() {
        // Stop any handlers here

        // Call last in case it interferes with the override
        super.onClose();
    }

    @Override
    public void removed() {
        // Reset initial states here
        assert this.minecraft != null;
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);

        // Call last in case it interferes with the override
        super.removed();
    }
}
