package com.yahu.rotation;

public class LegitRotation {
    public static void update(RotationManager manager) {
        float yawDiff = Math.abs(manager.getTargetYaw() - manager.getYaw()) % 360;
        float pitchDiff = Math.abs(manager.getTargetPitch() - manager.getPitch());

        if (yawDiff < 0.5f && pitchDiff < 0.5f) {
            manager.setRotating(false);
            return;
        }

        float speed = 20.0f + (float) (Math.random() * 10);
        float newYaw = rotateTowards(manager.getYaw(), manager.getTargetYaw(), speed);
        float newPitch = rotateTowards(manager.getPitch(), manager.getTargetPitch(), speed);

        manager.currentYaw = newYaw;
        manager.currentPitch = newPitch;

        if (Math.abs(newYaw - manager.getTargetYaw()) < 0.5f &&
            Math.abs(newPitch - manager.getTargetPitch()) < 0.5f) {
            manager.setRotating(false);
        }
    }

    private static float rotateTowards(float current, float target, float speed) {
        float diff = (target - current + 180) % 360 - 180;
        if (Math.abs(diff) < speed) return target;
        return current + Math.signum(diff) * speed;
    }
}