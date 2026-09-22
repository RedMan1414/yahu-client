package com.yahu.command.commands;

import com.yahu.module.ModuleRegistry;
import com.yahu.module.modules.AutoMineModule;
import net.minecraft.network.chat.Component;

public class MineCommand {
    public static int execute(String block) {
        AutoMineModule module = (AutoMineModule) ModuleRegistry.getInstance().get("AutoMine");
        if (module == null) {
            sendMessage("AutoMine module not found");
            return 0;
        }
        module.setTargetBlock(block);
        module.setEnabled(true);
        sendMessage("AutoMine targeting: " + block);
        return 1;
    }

    private static void sendMessage(String message) {
        net.minecraft.client.Minecraft.getInstance().gui.getChat().addMessage(Component.literal("§7[§bYahu§7] §r" + message));
    }
}