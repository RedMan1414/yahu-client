package com.yahu.mixin;

import com.yahu.packet.manipulators.NameProtectManipulator;
import com.yahu.packet.PacketManipulatorRegistry;
import net.minecraft.world.scores.PlayerTeam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(PlayerTeam.class)
public class TeamMixin {
    @Inject(method = "getPlayers", at = @At("HEAD"), cancellable = true)
    private void onGetPlayers(CallbackInfoReturnable<java.util.Collection<String>> cir) {
        NameProtectManipulator manipulator = PacketManipulatorRegistry.getInstance().getAll().stream()
                .filter(m -> m instanceof NameProtectManipulator)
                .map(m -> (NameProtectManipulator) m)
                .findFirst()
                .orElse(null);

        if (manipulator != null) {
            java.util.Collection<String> players = ((PlayerTeam) (Object) this).getPlayers();
            java.util.List<String> filtered = players.stream()
                    .filter(name -> {
                        // Would need to resolve name to UUID
                        return true;
                    })
                    .toList();
            cir.setReturnValue(filtered);
        }
    }
}