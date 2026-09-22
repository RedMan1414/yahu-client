package com.yahu.event.events;

import com.yahu.event.Event;
import net.minecraft.client.KeyMapping;

public class EventKeyPress extends Event {
    private final KeyMapping key;
    private final int action;
    private final int mods;

    public EventKeyPress(KeyMapping key, int action, int mods) {
        this.key = key;
        this.action = action;
        this.mods = mods;
    }

    public KeyMapping getKey() { return key; }
    public int getAction() { return action; }
    public int getMods() { return mods; }
}