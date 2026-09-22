package com.yahu.waypoint;

import com.google.gson.*;
import com.yahu.config.ConfigManager;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WaypointManager {
    private static final WaypointManager INSTANCE = new WaypointManager();
    private final Map<String, Waypoint> waypoints = new ConcurrentHashMap<>();
    private final Path file;

    private WaypointManager() {
        Path mcDir = Path.of(System.getProperty("user.home"), ".minecraft", "Yahu Client");
        this.file = mcDir.resolve("waypoints.nbt");
        load();
    }

    public static WaypointManager getInstance() {
        return INSTANCE;
    }

    public void add(Waypoint waypoint) {
        waypoints.put(waypoint.getName().toLowerCase(), waypoint);
        save();
    }

    public void remove(String name) {
        waypoints.remove(name.toLowerCase());
        save();
    }

    public Waypoint get(String name) {
        return waypoints.get(name.toLowerCase());
    }

    public Collection<Waypoint> getAll() {
        return Collections.unmodifiableCollection(waypoints.values());
    }

    public void load() {
        if (!Files.exists(file)) return;
        try (Reader reader = Files.newBufferedReader(file)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                String name = obj.get("name").getAsString();
                int x = obj.get("x").getAsInt();
                int y = obj.get("y").getAsInt();
                int z = obj.get("z").getAsInt();
                String dimension = obj.has("dimension") ? obj.get("dimension").getAsString() : "overworld";
                Color color = obj.has("color") ? new Color(obj.get("color").getAsInt()) : Color.WHITE;
                waypoints.put(name.toLowerCase(), new Waypoint(name, x, y, z, dimension, color));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Files.createDirectories(file.getParent());
            JsonArray array = new JsonArray();
            for (Waypoint wp : waypoints.values()) {
                JsonObject obj = new JsonObject();
                obj.addProperty("name", wp.getName());
                obj.addProperty("x", wp.getX());
                obj.addProperty("y", wp.getY());
                obj.addProperty("z", wp.getZ());
                obj.addProperty("dimension", wp.getDimension());
                obj.addProperty("color", wp.getColor().getRGB());
                array.add(obj);
            }
            try (Writer writer = Files.newBufferedWriter(file)) {
                ConfigManager.getInstance().getClass().getDeclaredField("gson").setAccessible(true);
                com.google.gson.Gson gson = (com.google.gson.Gson) ConfigManager.getInstance().getClass().getDeclaredField("gson").get(ConfigManager.getInstance());
                gson.toJson(array, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}