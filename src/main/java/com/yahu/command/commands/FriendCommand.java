package com.yahu.command.commands;

import com.yahu.friend.Friend;
import com.yahu.friend.FriendManager;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.UUID;

public class FriendCommand {
    public static int add(String name) {
        FriendManager.getInstance().add(name, null, "", Color.WHITE);
        sendMessage("Added friend: " + name);
        return 1;
    }

    public static int remove(String name) {
        FriendManager.getInstance().remove(name);
        sendMessage("Removed friend: " + name);
        return 1;
    }

    public static int list() {
        sendMessage("Friends:");
        for (Friend friend : FriendManager.getInstance().getAll()) {
            sendMessage("  - " + friend.getDisplayName() + (friend.getAlias().isEmpty() ? "" : " (" + friend.getAlias() + ")"));
        }
        return 1;
    }

    public static int color(String name, String colorStr) {
        Friend friend = FriendManager.getInstance().get(name);
        if (friend == null) {
            sendMessage("Friend not found: " + name);
            return 0;
        }
        try {
            int color = Integer.parseInt(colorStr.replace("#", ""), 16);
            if ((color & 0xFF000000) == 0) color |= 0xFF000000;
            // Need to create new friend with updated color since Friend is immutable
            FriendManager.getInstance().remove(name);
            FriendManager.getInstance().add(friend.getName(), friend.getUuid(), friend.getAlias(), new Color(color));
            sendMessage("Set color for " + name);
        } catch (NumberFormatException e) {
            sendMessage("Invalid color format. Use hex (e.g. FF0000 or #FF0000)");
        }
        return 1;
    }

    private static void sendMessage(String message) {
        net.minecraft.client.Minecraft.getInstance().gui.getChat().addMessage(Component.literal("§7[§bYahu§7] §r" + message));
    }
}