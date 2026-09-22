package com.yahu.waypoint;

import java.awt.*;

public class Waypoint {
    private final String name;
    private final int x, y, z;
    private final String dimension;
    private final Color color;

    public Waypoint(String name, int x, int y, int z, String dimension, Color color) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
        this.color = color;
    }

    public String getName() { return name; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }
    public String getDimension() { return dimension; }
    public Color getColor() { return color; }
}