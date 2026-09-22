package com.yahu.packet.manipulators;

import com.yahu.packet.PacketManipulator;
import com.yahu.packet.PacketEvent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.*;

public class NameProtectManipulator extends PacketManipulator {
    private final java.util.Set<java.util.UUID> hiddenPlayers = java.util.Collections.synchronizedSet(new java.util.HashSet<>());

    @Override
    public boolean onSend(Packet<?> packet, PacketEvent.Send event) {
        return false;
    }

    @Override
    public boolean onReceive(Packet<?> packet, PacketEvent.Receive event) {
        if (packet instanceof ClientboundPlayerInfoUpdatePacket infoPacket) {
            for (ClientboundPlayerInfoUpdatePacket.Entry entry : infoPacket.entries()) {
                if (hiddenPlayers.contains(entry.profile().getId())) {
                    event.cancel();
                    return true;
                }
            }
        } else if (packet instanceof ClientboundSetEntityDataPacket metadataPacket) {
            // Handle entity metadata
        } else if (packet instanceof ClientboundSetEntityPacket entityPacket) {
            // Handle entity spawn
        }
        return false;
    }

    public void addHiddenPlayer(java.util.UUID uuid) {
        hiddenPlayers.add(uuid);
    }

    public void removeHiddenPlayer(java.util.UUID uuid) {
        hiddenPlayers.remove(uuid);
    }

    public void clear() {
        hiddenPlayers.clear();
    }

    public java.util.Set<java.util.UUID> getHiddenPlayers() {
        return java.util.Collections.unmodifiableSet(hiddenPlayers);
    }
}