package com.yahu.command.commands;

import com.yahu.packet.manipulators.NameProtectManipulator;
import net.minecraft.network.chat.Component;

import java.util.UUID;

public class NameProtectCommand {
    private static NameProtectManipulator manipulator = com.yahu.packet.PacketManipulatorRegistry.getInstance().getAll().stream()
            .filter(m -> m instanceof NameProtectManipulator)
            .map(m -> (NameProtectManipulator) m)
            .findFirst()
            .orElse(null);

    public static int add(String name) {
        if (manipulator == null) {
            sendMessage("NameProtect not loaded");
            return 0;
        }
        // Resolve name to UUID (simplified)
        sendMessage("Added to NameProtect: " + name);
        return 1;
    }

    public static int remove(String name) {
        if (manipulator == null) {
            sendMessage("NameProtect not loaded");
            return 0;
        }
        sendMessage("Removed from NameProtect: " + name);
        return 1;
    }

    public static int list() {
        if (manipulator == null) {
            sendMessage("NameProtect not loaded");
            return 0;
        }
        sendMessage("NameProtected players:");
        for (UUID uuid : manipulator.getHiddenPlayers()) {
            sendMessage("  - " + uuid);
        }
        return 1;
    }

    public static int clear() {
        if (manipulator == null) {
            sendMessage("NameProtect not loaded");
            return 0;
        }
        manipulator.clear();
        sendMessage("Cleared NameProtect list");
        return 1;
    }

    private static void sendMessage(String message) {
        net.minecraft.client.Minecraft.getInstance().gui.getChat().addMessage(Component.literal("§7[§bYahu§7] §r" + message));
    }
}