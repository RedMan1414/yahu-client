package com.yahu.event.events;

import com.yahu.event.Event;
import net.minecraft.world.entity.Entity;

public class EventTotemPop extends Event {
    private final Entity entity;
    private final int count;

    public EventTotemPop(Entity entity, int count) {
        this.entity = entity;
        this.count = count;
    }

    public Entity getEntity() { return entity; }
    public int getCount() { return count; }
}