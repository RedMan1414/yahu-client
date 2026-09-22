package com.yahu.mixin;

import com.yahu.packet.manipulators.NameProtectManipulator;
import com.yahu.packet.PacketManipulatorRegistry;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(Scoreboard.class)
public class ScoreboardMixin {
    @Inject(method = "addPlayerToTeam", at = @At("HEAD"), cancellable = true)
    private void onAddPlayerToTeam(String playerName, PlayerTeam team, CallbackInfo ci) {
        // Filter hidden players from scoreboard
    }
}