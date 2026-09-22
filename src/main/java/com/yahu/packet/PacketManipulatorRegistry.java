package com.yahu.packet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PacketManipulatorRegistry {
    private static final PacketManipulatorRegistry INSTANCE = new PacketManipulatorRegistry();
    private final List<PacketManipulator> manipulators = new ArrayList<>();

    private PacketManipulatorRegistry() {}

    public static PacketManipulatorRegistry getInstance() {
        return INSTANCE;
    }

    public void register(PacketManipulator manipulator) {
        manipulators.add(manipulator);
        manipulators.sort(Comparator.comparingInt(PacketManipulator::priority).reversed());
    }

    public void unregister(PacketManipulator manipulator) {
        manipulators.remove(manipulator);
    }

    public List<PacketManipulator> getAll() {
        return Collections.unmodifiableList(manipulators);
    }

    public boolean processSend(net.minecraft.network.protocol.Packet<?> packet, PacketEvent.Send event) {
        for (PacketManipulator m : manipulators) {
            if (m.enabled() && m.onSend(packet, event)) {
                return true;
            }
        }
        return false;
    }

    public boolean processReceive(net.minecraft.network.protocol.Packet<?> packet, PacketEvent.Receive event) {
        for (PacketManipulator m : manipulators) {
            if (m.enabled() && m.onReceive(packet, event)) {
                return true;
            }
        }
        return false;
    }
}