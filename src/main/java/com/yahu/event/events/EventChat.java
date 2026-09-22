package com.yahu.event.events;

import com.yahu.event.Event;

public class EventChat extends Event {
    private final String message;

    public EventChat(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { /* immutable */ }
}