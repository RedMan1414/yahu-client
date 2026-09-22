package com.yahu.command.commands;

import net.minecraft.network.chat.Component;

public class BypassCommand {
    public static int execute(String profile) {
        sendMessage("Loaded bypass profile: " + profile);
        // Load bypass profile logic
        return 1;
    }

    private static void sendMessage(String message) {
        net.minecraft.client.Minecraft.getInstance().gui.getChat().addMessage(Component.literal("§7[§bYahu§7] §r" + message));
    }
}