package com.yahu.packet;

import com.yahu.event.Event;
import net.minecraft.network.protocol.Packet;

public class PacketEvent extends Event {
    public static class Send extends PacketEvent {
        private final Packet<?> packet;

        public Send(Packet<?> packet) { this.packet = packet; }
        public Packet<?> getPacket() { return packet; }
    }

    public static class Receive extends PacketEvent {
        private final Packet<?> packet;

        public Receive(Packet<?> packet) { this.packet = packet; }
        public Packet<?> getPacket() { return packet; }
    }
}