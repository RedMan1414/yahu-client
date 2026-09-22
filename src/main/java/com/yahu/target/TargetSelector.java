package com.yahu.target;

import net.minecraft.world.entity.LivingEntity;

import java.util.*;
import java.util.function.Predicate;

public class TargetSelector {
    private final List<TargetPriority> priorities = new ArrayList<>();
    private final List<Predicate<LivingEntity>> filters = new ArrayList<>();
    private TargetMode mode = TargetMode.SINGLE;
    private double range = 4.5;
    private float fov = 360;

    public enum TargetPriority {
        DISTANCE, HEALTH, ARMOR, HURT_TIME, ANGLE, FOV, WALL
    }

    public enum TargetMode {
        SINGLE, MULTI, SWITCH, SILENT
    }

    public TargetSelector addPriority(TargetPriority priority) {
        priorities.add(priority);
        return this;
    }

    public TargetSelector addFilter(Predicate<LivingEntity> filter) {
        filters.add(filter);
        return this;
    }

    public TargetSelector setMode(TargetMode mode) {
        this.mode = mode;
        return this;
    }

    public TargetSelector setRange(double range) {
        this.range = range;
        return this;
    }

    public TargetSelector setFov(float fov) {
        this.fov = fov;
        return this;
    }

    public List<LivingEntity> getTargets() {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return List.of();

        List<LivingEntity> candidates = mc.level.getEntitiesOfClass(LivingEntity.class,
                mc.player.getBoundingBox().inflate(range),
                e -> e != mc.player && e.isAlive() && passesFilters(e));

        candidates.sort(this::compareTargets);

        return switch (mode) {
            case SINGLE -> candidates.isEmpty() ? List.of() : List.of(candidates.getFirst());
            case MULTI -> candidates;
            case SWITCH, SILENT -> candidates;
        };
    }

    public LivingEntity getTarget() {
        List<LivingEntity> targets = getTargets();
        return targets.isEmpty() ? null : targets.getFirst();
    }

    private boolean passesFilters(LivingEntity entity) {
        for (Predicate<LivingEntity> filter : filters) {
            if (!filter.test(entity)) return false;
        }
        return true;
    }

    private int compareTargets(LivingEntity a, LivingEntity b) {
        for (TargetPriority priority : priorities) {
            int result = compareByPriority(a, b, priority);
            if (result != 0) return result;
        }
        return 0;
    }

    private int compareByPriority(LivingEntity a, LivingEntity b, TargetPriority priority) {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
        return switch (priority) {
            case DISTANCE -> Double.compare(a.distanceTo(mc.player), b.distanceTo(mc.player));
            case HEALTH -> Float.compare(a.getHealth(), b.getHealth());
            case ARMOR -> Integer.compare(getArmorValue(a), getArmorValue(b));
            case HURT_TIME -> Integer.compare(b.hurtTime, a.hurtTime);
            case ANGLE -> Float.compare(getAngle(a), getAngle(b));
            case FOV -> Boolean.compare(isInFov(b), isInFov(a));
            case WALL -> Boolean.compare(mc.player.hasLineOfSight(b), mc.player.hasLineOfSight(a));
        };
    }

    private int getArmorValue(LivingEntity entity) {
        return entity.getArmorSlots().mapToInt(stack -> stack.getItem().getMaxDamage() > 0 ? stack.getMaxDamage() - stack.getDamageValue() : 0).sum();
    }

    private float getAngle(LivingEntity entity) {
        float[] rots = com.yahu.utils.RotationUtil.getRotations(entity.getEyePosition());
        return com.yahu.utils.RotationUtil.getAngleDifference(mc.player.getYRot(), rots[0]);
    }

    private boolean isInFov(LivingEntity entity) {
        return getAngle(entity) <= fov;
    }
}