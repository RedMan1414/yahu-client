package com.yahu.mixin;

import com.yahu.event.EventBus;
import com.yahu.event.events.EventPacket;
import com.yahu.packet.PacketManipulatorRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onSendPacket(Packet<?> packet, CallbackInfo ci) {
        EventPacket.Send event = new EventPacket.Send(packet);
        EventBus.getInstance().post(event);

        if (PacketManipulatorRegistry.getInstance().processSend(packet, event)) {
            ci.cancel();
        }

        if (event.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method = "handlePacket(Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void onReceivePacket(Packet<?> packet, CallbackInfo ci) {
        EventPacket.Receive event = new EventPacket.Receive(packet);
        EventBus.getInstance().post(event);

        if (PacketManipulatorRegistry.getInstance().processReceive(packet, event)) {
            ci.cancel();
        }

        if (event.isCancelled()) {
            ci.cancel();
        }
    }
}