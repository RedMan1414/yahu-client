package com.yahu.event.events;

import com.yahu.event.Event;
import com.mojang.blaze3d.vertex.PoseStack;

public class EventRender extends Event {
    public enum Type { WORLD, HAND, GUI, WORLD_2D, WORLD_3D }

    private final Type type;
    private final PoseStack matrices;
    private final float tickDelta;

    public EventRender(Type type, PoseStack matrices, float tickDelta) {
        this.type = type;
        this.matrices = matrices;
        this.tickDelta = tickDelta;
    }

    public Type getType() { return type; }
    public PoseStack getMatrices() { return matrices; }
    public float getTickDelta() { return tickDelta; }
}