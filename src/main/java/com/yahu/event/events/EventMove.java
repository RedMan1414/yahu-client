package com.yahu.event.events;

import com.yahu.event.Event;
import net.minecraft.world.phys.Vec3;

public class EventMove extends Event {
    private final Vec3 movementInput;

    public EventMove(Vec3 movementInput) {
        this.movementInput = movementInput;
    }

    public Vec3 getMovementInput() { return movementInput; }
    public void setMovementInput(Vec3 input) { this.movementInput.set(input.x, input.y, input.z); }
}