package com.yahu.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

public class InventoryUtil {
    public static int findItem(net.minecraft.world.item.Item item) {
        var player = Minecraft.getInstance().player;
        if (player == null) return -1;
        for (int i = 0; i < 36; i++) {
            if (player.getInventory().getItem(i).getItem() == item) return i;
        }
        return -1;
    }

    public static int findItemInHotbar(net.minecraft.world.item.Item item) {
        var player = Minecraft.getInstance().player;
        if (player == null) return -1;
        for (int i = 0; i < 9; i++) {
            if (player.getInventory().getItem(i).getItem() == item) return i;
        }
        return -1;
    }

    public static void swapToSlot(int slot) {
        var player = Minecraft.getInstance().player;
        if (player != null) player.getInventory().selected = slot;
    }

    public static boolean isFull() {
        var player = Minecraft.getInstance().player;
        if (player == null) return true;
        for (int i = 0; i < 36; i++) {
            if (player.getInventory().getItem(i).isEmpty()) return false;
        }
        return true;
    }
}