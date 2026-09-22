package com.yahu.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.entity.Entity;

public class RaycastUtil {
    public static HitResult raycast(double reach, float partialTicks) {
        var mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return null;

        Vec3 eyePos = mc.player.getEyePosition(partialTicks);
        Vec3 lookVec = mc.player.getViewVector(partialTicks);
        Vec3 endPos = eyePos.add(lookVec.scale(reach));

        return mc.level.clip(new net.minecraft.world.phys.AABB(eyePos, endPos).inflate(1e-6), 
            mc.player, e -> !e.isSpectator() && e.isPickable());
    }

    public static BlockHitResult raycastBlock(double reach, float partialTicks) {
        HitResult result = raycast(reach, partialTicks);
        return result instanceof BlockHitResult bhr ? bhr : null;
    }

    public static EntityHitResult raycastEntity(double reach, float partialTicks) {
        HitResult result = raycast(reach, partialTicks);
        return result instanceof EntityHitResult ehr ? ehr : null;
    }
}