package com.yahu.event.events;

import com.yahu.event.Event;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;

public class EventPlaceBlock extends Event {
    private final BlockPos pos;
    private final InteractionHand hand;

    public EventPlaceBlock(BlockPos pos, InteractionHand hand) {
        this.pos = pos;
        this.hand = hand;
    }

    public BlockPos getPos() { return pos; }
    public InteractionHand getHand() { return hand; }
}