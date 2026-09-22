package com.yahu.event.events;

import com.yahu.event.Event;
import net.minecraft.world.entity.Entity;

public class EventAttack extends Event {
    private final Entity target;

    public EventAttack(Entity target) {
        this.target = target;
    }

    public Entity getTarget() { return target; }
}