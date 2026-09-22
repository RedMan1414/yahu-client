package com.yahu.event.events;

import com.yahu.event.Event;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;

public class EventDeath extends Event {
    private final Entity entity;
    private final DamageSource source;

    public EventDeath(Entity entity, DamageSource source) {
        this.entity = entity;
        this.source = source;
    }

    public Entity getEntity() { return entity; }
    public DamageSource getSource() { return source; }
}