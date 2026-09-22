package com.yahu.command.commands;

import com.yahu.pathfind.Pathfinder;
import com.yahu.pathfind.Goal;
import net.minecraft.network.chat.Component;

public class GotoCommand {
    public static int execute(int x, int y, int z) {
        Pathfinder.getInstance().setGoal(new Goal.PositionGoal(x, y, z));
        sendMessage("Pathfinding to " + x + ", " + y + ", " + z);
        return 1;
    }

    private static void sendMessage(String message) {
        net.minecraft.client.Minecraft.getInstance().gui.getChat().addMessage(Component.literal("§7[§bYahu§7] §r" + message));
    }
}