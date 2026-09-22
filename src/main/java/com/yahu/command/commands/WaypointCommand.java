package com.yahu.command.commands;

import com.yahu.waypoint.Waypoint;
import com.yahu.waypoint.WaypointManager;
import net.minecraft.network.chat.Component;

import java.awt.*;

public class WaypointCommand {
    public static int add(String name, int x, int y, int z) {
        WaypointManager.getInstance().add(new Waypoint(name, x, y, z, "overworld", Color.WHITE));
        sendMessage("Added waypoint: " + name + " at " + x + ", " + y + ", " + z);
        return 1;
    }

    public static int remove(String name) {
        WaypointManager.getInstance().remove(name);
        sendMessage("Removed waypoint: " + name);
        return 1;
    }

    public static int teleport(String name) {
        Waypoint wp = WaypointManager.getInstance().get(name);
        if (wp == null) {
            sendMessage("Waypoint not found: " + name);
            return 0;
        }
        // Teleport logic would go here
        sendMessage("Teleported to " + name);
        return 1;
    }

    public static int list() {
        sendMessage("Waypoints:");
        for (Waypoint wp : WaypointManager.getInstance().getAll()) {
            sendMessage("  - " + wp.getName() + " [" + wp.getX() + ", " + wp.getY() + ", " + wp.getZ() + "] (" + wp.getDimension() + ")");
        }
        return 1;
    }

    private static void sendMessage(String message) {
        net.minecraft.client.Minecraft.getInstance().gui.getChat().addMessage(Component.literal("§7[§bYahu§7] §r" + message));
    }
}