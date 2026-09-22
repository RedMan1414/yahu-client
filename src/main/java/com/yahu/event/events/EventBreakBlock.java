package com.yahu.event.events;

import com.yahu.event.Event;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class EventBreakBlock extends Event {
    private final BlockPos pos;
    private final Direction direction;

    public EventBreakBlock(BlockPos pos, Direction direction) {
        this.pos = pos;
        this.direction = direction;
    }

    public BlockPos getPos() { return pos; }
    public Direction getDirection() { return direction; }
}