package com.yahu.command.commands;

import com.yahu.config.ConfigManager;
import net.minecraft.network.chat.Component;

public class ConfigCommand {
    public static int load(String name) {
        ConfigManager.getInstance().loadProfile(name);
        sendMessage("Loaded config profile: " + name);
        return 1;
    }

    public static int save(String name) {
        ConfigManager.getInstance().saveProfile(name);
        sendMessage("Saved config profile: " + name);
        return 1;
    }

    public static int delete(String name) {
        if (name.equals("default")) {
            sendMessage("Cannot delete default profile");
            return 0;
        }
        ConfigManager.getInstance().deleteProfile(name);
        sendMessage("Deleted config profile: " + name);
        return 1;
    }

    public static int list() {
        sendMessage("Config profiles:");
        for (String profile : ConfigManager.getInstance().listProfiles()) {
            sendMessage("  - " + profile + (profile.equals(ConfigManager.getInstance().getCurrentProfile()) ? " (active)" : ""));
        }
        return 1;
    }

    public static int export() {
        String exported = ConfigManager.getInstance().exportConfig();
        sendMessage("Config exported (copy to clipboard):");
        sendMessage(exported);
        return 1;
    }

    public static int importConfig(String base64) {
        ConfigManager.getInstance().importConfig(base64);
        sendMessage("Config imported successfully");
        return 1;
    }

    private static void sendMessage(String message) {
        net.minecraft.client.Minecraft.getInstance().gui.getChat().addMessage(Component.literal("§7[§bYahu§7] §r" + message));
    }
}