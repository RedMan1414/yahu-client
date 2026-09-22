package com.yahu.friend;

import java.awt.*;

public class Friend {
    private final String name;
    private final java.util.UUID uuid;
    private final String alias;
    private final Color color;

    public Friend(String name, java.util.UUID uuid, String alias, Color color) {
        this.name = name;
        this.uuid = uuid;
        this.alias = alias;
        this.color = color;
    }

    public String getName() { return name; }
    public java.util.UUID getUuid() { return uuid; }
    public String getAlias() { return alias; }
    public Color getColor() { return color; }
    public String getDisplayName() { return alias.isEmpty() ? name : alias; }
}