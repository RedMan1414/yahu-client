package com.yahu.mixin;

import com.yahu.packet.manipulators.NameProtectManipulator;
import com.yahu.packet.PacketManipulatorRegistry;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(PlayerInfo.class)
public class PlayerListEntryMixin {
    @Inject(method = "getDisplayName", at = @At("HEAD"), cancellable = true)
    private void onGetDisplayName(CallbackInfoReturnable<Component> cir) {
        NameProtectManipulator manipulator = PacketManipulatorRegistry.getInstance().getAll().stream()
                .filter(m -> m instanceof NameProtectManipulator)
                .map(m -> (NameProtectManipulator) m)
                .findFirst()
                .orElse(null);

        if (manipulator != null && manipulator.getHiddenPlayers().contains(((PlayerInfo) (Object) this).getProfile().getId())) {
            cir.setReturnValue(Component.literal("§8[Hidden]"));
        }
    }

    @Inject(method = "getLatency", at = @At("HEAD"), cancellable = true)
    private void onGetLatency(CallbackInfoReturnable<Integer> cir) {
        // Ping spoof logic here
    }
}