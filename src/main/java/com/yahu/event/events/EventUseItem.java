package com.yahu.event.events;

import com.yahu.event.Event;
import net.minecraft.world.InteractionHand;

public class EventUseItem extends Event {
    private final InteractionHand hand;

    public EventUseItem(InteractionHand hand) {
        this.hand = hand;
    }

    public InteractionHand getHand() { return hand; }
}