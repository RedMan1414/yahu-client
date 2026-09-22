package com.yahu.command.commands;

import com.yahu.pathfind.Pathfinder;
import com.yahu.pathfind.Goal;
import net.minecraft.network.chat.Component;

public class FollowCommand {
    public static int execute(String player) {
        Pathfinder.getInstance().setGoal(new Goal.EntityGoal(player));
        sendMessage("Following: " + player);
        return 1;
    }

    public static int stop() {
        Pathfinder.getInstance().stop();
        sendMessage("Stopped pathfinding");
        return 1;
    }

    private static void sendMessage(String message) {
        net.minecraft.client.Minecraft.getInstance().gui.getChat().addMessage(Component.literal("§7[§bYahu§7] §r" + message));
    }
}