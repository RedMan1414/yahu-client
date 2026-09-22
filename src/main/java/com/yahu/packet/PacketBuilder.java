package com.yahu.packet;

import net.minecraft.network.protocol.Packet;

import java.util.*;

public class PacketBuilder {
    private final Packet<?> packet;
    private final Map<String, Object> fields = new HashMap<>();

    private PacketBuilder(Packet<?> packet) {
        this.packet = packet;
    }

    public static <T extends Packet<?>> PacketBuilder create(T packet) {
        return new PacketBuilder(packet);
    }

    public PacketBuilder field(String name, Object value) {
        fields.put(name, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T build() {
        try {
            for (Map.Entry<String, Object> entry : fields.entrySet()) {
                java.lang.reflect.Field field = packet.getClass().getDeclaredField(entry.getKey());
                field.setAccessible(true);
                field.set(packet, entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) packet;
    }

    public void send() {
        net.minecraft.client.Minecraft.getInstance().getConnection().send(build());
    }
}