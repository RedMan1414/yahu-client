package com.yahu.event.events;

import com.yahu.event.Event;

public class EventTick extends Event {
    public enum Phase { PRE, POST }
    public enum Side { CLIENT, SERVER }

    private final Phase phase;
    private final Side side;

    public EventTick(Phase phase, Side side) {
        this.phase = phase;
        this.side = side;
    }

    public Phase getPhase() { return phase; }
    public Side getSide() { return side; }
}