package com.yahu.target;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.function.Predicate;

public class TargetFilter {
    public static Predicate<LivingEntity> teams() {
        return e -> !(e instanceof Player p) || !isSameTeam(p);
    }

    public static Predicate<LivingEntity> friends() {
        return e -> !(e instanceof Player p) || !FriendManager.getInstance().isFriend(p.getName().getString());
    }

    public static Predicate<LivingEntity> invisibles() {
        return e -> !e.isInvisible();
    }

    public static Predicate<LivingEntity> dead() {
        return LivingEntity::isAlive;
    }

    public static Predicate<LivingEntity> distance(double max) {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
        return e -> mc.player != null && e.distanceTo(mc.player) <= max;
    }

    public static Predicate<LivingEntity> vehicles() {
        return e -> e.getVehicle() == null && e.getPassengers().isEmpty();
    }

    public static Predicate<LivingEntity> nametag(String regex) {
        return e -> e.getName().getString().matches(regex);
    }

    private static boolean isSameTeam(Player player) {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
        if (mc.player == null || mc.player.getTeam() == null || player.getTeam() == null) return false;
        return mc.player.getTeam().equals(player.getTeam());
    }
}