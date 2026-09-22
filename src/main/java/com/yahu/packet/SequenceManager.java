package com.yahu.packet;

import net.minecraft.network.protocol.Packet;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SequenceManager {
    private int clientSequence = 0;
    private int serverSequence = 0;
    private final Queue<Packet<?>> chokedPackets = new ConcurrentLinkedQueue<>();

    public void choke(Packet<?> packet) {
        chokedPackets.add(packet);
    }

    public void flush() {
        Packet<?> packet;
        while ((packet = chokedPackets.poll()) != null) {
            net.minecraft.client.Minecraft.getInstance().getConnection().send(packet);
        }
    }

    public int getNextSequence() {
        return ++clientSequence;
    }

    public void ack(int sequence) {
        serverSequence = sequence;
    }

    public int getClientSequence() {
        return clientSequence;
    }

    public int getServerSequence() {
        return serverSequence;
    }

    public int getChokedCount() {
        return chokedPackets.size();
    }

    public void clearChoked() {
        chokedPackets.clear();
    }
}