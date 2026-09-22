package com.yahu.command.commands;

import com.yahu.module.Module;
import com.yahu.module.ModuleRegistry;
import net.minecraft.network.chat.Component;

public class ModuleCommand {
    public static int enable(String name) {
        Module module = ModuleRegistry.getInstance().get(name);
        if (module == null) {
            sendMessage("Module not found: " + name);
            return 0;
        }
        module.setEnabled(true);
        sendMessage("Enabled " + module.getName());
        return 1;
    }

    public static int disable(String name) {
        Module module = ModuleRegistry.getInstance().get(name);
        if (module == null) {
            sendMessage("Module not found: " + name);
            return 0;
        }
        module.setEnabled(false);
        sendMessage("Disabled " + module.getName());
        return 1;
    }

    public static int toggle(String name) {
        Module module = ModuleRegistry.getInstance().get(name);
        if (module == null) {
            sendMessage("Module not found: " + name);
            return 0;
        }
        module.toggle();
        sendMessage((module.isEnabled() ? "Enabled" : "Disabled") + " " + module.getName());
        return 1;
    }

    public static int list() {
        sendMessage("Modules:");
        for (Module module : ModuleRegistry.getInstance().getAll()) {
            String status = module.isEnabled() ? "§a[ON]§r" : "§c[OFF]§r";
            sendMessage("  " + status + " " + module.getName() + " (" + module.getCategory().getName() + ")");
        }
        return 1;
    }

    private static void sendMessage(String message) {
        net.minecraft.client.Minecraft.getInstance().gui.getChat().addMessage(Component.literal("§7[§bYahu§7] §r" + message));
    }
}