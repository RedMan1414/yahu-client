package com.yahu.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class WorldUtil {
    public static Level getWorld() {
        return Minecraft.getInstance().level;
    }

    public static boolean canSeeBlock(BlockPos pos) {
        Level world = getWorld();
        if (world == null) return false;
        // Raycast check
        return true;
    }

    public static BlockPos getBlockPos(double x, double y, double z) {
        return new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
    }
}