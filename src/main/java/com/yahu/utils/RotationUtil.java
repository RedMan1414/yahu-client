package com.yahu.utils;

import net.minecraft.world.phys.Vec3;

public class RotationUtil {
    public static float[] getRotations(Vec3 target) {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
        if (mc.player == null) return new float[]{0, 0};

        Vec3 eyes = mc.player.getEyePosition();
        double diffX = target.x - eyes.x;
        double diffY = target.y - eyes.y;
        double diffZ = target.z - eyes.z;
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90;
        float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

        yaw = wrap(yaw);
        pitch = clamp(pitch, -90, 90);

        return new float[]{yaw, pitch};
    }

    public static float wrap(float angle) {
        angle %= 360;
        if (angle < 0) angle += 360;
        return angle;
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }

    public static float getAngleDifference(float a, float b) {
        float diff = Math.abs(a - b) % 360;
        return diff > 180 ? 360 - diff : diff;
    }
}