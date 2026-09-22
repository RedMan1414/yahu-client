package com.yahu.rotation;

import com.yahu.setting.EnumSetting;
import com.yahu.utils.RotationUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class RotationManager {
    private RotationMode mode = RotationMode.SILENT;
    private float currentYaw, currentPitch;
    private float targetYaw, targetPitch;
    private boolean rotating = false;

    public void setMode(RotationMode mode) {
        this.mode = mode;
    }

    public RotationMode getMode() {
        return mode;
    }

    public void setRotations(float yaw, float pitch) {
        this.targetYaw = yaw;
        this.targetPitch = pitch;
        this.rotating = true;
    }

    public void setTarget(LivingEntity target) {
        Vec3 pos = target.getEyePosition();
        float[] rots = RotationUtil.getRotations(pos);
        setRotations(rots[0], rots[1]);
    }

    public void setTarget(Vec3 pos) {
        float[] rots = RotationUtil.getRotations(pos);
        setRotations(rots[0], rots[1]);
    }

    public void update() {
        if (!rotating) return;

        switch (mode) {
            case SILENT -> {
                currentYaw = targetYaw;
                currentPitch = targetPitch;
                rotating = false;
            }
            case SNAP -> {
                currentYaw = targetYaw;
                currentPitch = targetPitch;
                rotating = false;
            }
            case LEGIT -> {
                LegitRotation.update(this);
            }
            case SERVERSIDE -> {
                // Handled by game
                rotating = false;
            }
        }
    }

    public float getYaw() { return currentYaw; }
    public float getPitch() { return currentPitch; }
    public float getTargetYaw() { return targetYaw; }
    public float getTargetPitch() { return targetPitch; }
    public boolean isRotating() { return rotating; }
    public void setRotating(boolean rotating) { this.rotating = rotating; }
}