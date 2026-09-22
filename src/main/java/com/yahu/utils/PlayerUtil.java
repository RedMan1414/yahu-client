package com.yahu.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

public class PlayerUtil {
    public static Player getPlayer() {
        return Minecraft.getInstance().player;
    }

    public static boolean isAlive(Player player) {
        return player != null && player.isAlive();
    }

    public static float getDistance(Entity a, Entity b) {
        return a.distanceTo(b);
    }

    public static boolean isHolding(Player player, net.minecraft.world.item.Item item) {
        return player.getMainHandItem().getItem() == item || player.getOffhandItem().getItem() == item;
    }
}