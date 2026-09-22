package com.yahu.command.commands;

import com.yahu.clickgui.ThemeManager;
import net.minecraft.network.chat.Component;

public class ThemeCommand {
    public static int load(String name) {
        ThemeManager.getInstance().loadTheme(name);
        sendMessage("Loaded theme: " + name);
        return 1;
    }

    public static int save(String name) {
        ThemeManager.getInstance().saveTheme(name);
        sendMessage("Saved theme: " + name);
        return 1;
    }

    public static int delete(String name) {
        ThemeManager.getInstance().deleteTheme(name);
        sendMessage("Deleted theme: " + name);
        return 1;
    }

    public static int list() {
        sendMessage("Themes:");
        for (String theme : ThemeManager.getInstance().listThemes()) {
            sendMessage("  - " + theme + (theme.equals(ThemeManager.getInstance().getCurrentTheme()) ? " (active)" : ""));
        }
        return 1;
    }

    private static void sendMessage(String message) {
        net.minecraft.client.Minecraft.getInstance().gui.getChat().addMessage(Component.literal("§7[§bYahu§7] §r" + message));
    }
}