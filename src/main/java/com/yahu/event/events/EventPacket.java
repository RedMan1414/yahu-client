package com.yahu.event.events;

import com.yahu.event.Event;
import net.minecraft.network.protocol.Packet;

public class EventPacket extends Event {
    public static class Send extends EventPacket {
        private final Packet<?> packet;

        public Send(Packet<?> packet) { this.packet = packet; }
        public Packet<?> getPacket() { return packet; }
    }

    public static class Receive extends EventPacket {
        private final Packet<?> packet;

        public Receive(Packet<?> packet) { this.packet = packet; }
        public Packet<?> getPacket() { return packet; }
    }
}