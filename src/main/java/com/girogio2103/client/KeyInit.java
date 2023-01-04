package com.girogio2103.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyInit {

    public static KeyMapping toggleChat;
    public static KeyMapping toggleIndicator;

    public static KeyMapping toggleMenu;

    private KeyInit() {
    }

    public static void init(){
        toggleChat = registerKey("mod.custom_chat.key.toggle", GLFW.GLFW_KEY_C, "key.categories.multiplayer");
        toggleIndicator = registerKey("mod.custom_chat.key.toggle_indicator", GLFW.GLFW_KEY_V, "key.categories.multiplayer");
        toggleMenu = registerKey("mod.custom_chat.key.toggle_menu", GLFW.GLFW_KEY_M, "key.categories.multiplayer");
    }

    private static KeyMapping registerKey(String name, int keyCode, String category) {
        final var key = new KeyMapping(name, InputConstants.Type.KEYSYM, keyCode, category);
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
