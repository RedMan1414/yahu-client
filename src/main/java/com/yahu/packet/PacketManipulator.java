package com.yahu.packet;

import net.minecraft.network.protocol.Packet;

public abstract class PacketManipulator {
    public abstract boolean onSend(Packet<?> packet, PacketEvent.Send event);
    public abstract boolean onReceive(Packet<?> packet, PacketEvent.Receive event);
    public int priority() { return 0; }
    public boolean enabled() { return true; }
}