package com.yahu.utils;

import net.minecraft.client.Minecraft;

public class TimerUtil {
    private static float timerSpeed = 1.0f;

    public static void setTimer(float speed) {
        timerSpeed = speed;
        var mc = Minecraft.getInstance();
        if (mc.level != null) {
            mc.level.getLevelData().setGameTime((long) (mc.level.getGameTime() * speed));
        }
    }

    public static float getTimer() {
        return timerSpeed;
    }

    public static void reset() {
        timerSpeed = 1.0f;
    }
}