package com.yahu.command.commands;

import com.yahu.module.Module;
import com.yahu.module.ModuleRegistry;
import com.yahu.setting.KeybindSetting;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;

public class BindCommand {
    public static int execute(String moduleName, String keyName, boolean holdMode) {
        Module module = ModuleRegistry.getInstance().get(moduleName);
        if (module == null) {
            sendMessage("Module not found: " + moduleName);
            return 0;
        }

        int key = getKeyCode(keyName);
        if (key == -1) {
            sendMessage("Invalid key: " + keyName);
            return 0;
        }

        module.setKeybind(key);
        module.setHoldMode(holdMode);

        KeybindSetting.Keybind kb = new KeybindSetting.Keybind(key, holdMode);
        for (com.yahu.setting.Setting<?> setting : module.getSettingRegistry().getAll()) {
            if (setting instanceof KeybindSetting ks) {
                ks.setValue(kb);
                break;
            }
        }

        String keyDisplay = KeyMapping.get(key) != null ? KeyMapping.get(key).getTranslatedKeyMessage().getString() : keyName;
        sendMessage("Bound " + module.getName() + " to " + keyDisplay + (holdMode ? " (Hold)" : ""));
        return 1;
    }

    private static int getKeyCode(String keyName) {
        if (keyName.equalsIgnoreCase("none")) return -1;
        try {
            return Integer.parseInt(keyName);
        } catch (NumberFormatException e) {
            for (KeyMapping mapping : net.minecraft.client.Minecraft.getInstance().options.keyMappings) {
                if (mapping.getTranslatedKeyMessage().getString().equalsIgnoreCase(keyName)) {
                    return mapping.getKey().getValue();
                }
            }
        }
        return -1;
    }

    private static void sendMessage(String message) {
        net.minecraft.client.Minecraft.getInstance().gui.getChat().addMessage(Component.literal("§7[§bYahu§7] §r" + message));
    }
}