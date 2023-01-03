package com.girogio2103.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyInit {

    public static KeyMapping toggleChat;
    public static KeyMapping toggleConn;

    private KeyInit() {
    }

    public static void init(){
        toggleChat = registerKey("mod.custom_chat.key.toggle", GLFW.GLFW_KEY_C, "key.categories.multiplayer");
        toggleConn = registerKey("mod.custom_chat.key.toggle_conn", GLFW.GLFW_KEY_V, "key.categories.multiplayer");
    }

    private static KeyMapping registerKey(String name, int keyCode, String category) {
        final var key = new KeyMapping(name, InputConstants.Type.KEYSYM, keyCode, category);
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
