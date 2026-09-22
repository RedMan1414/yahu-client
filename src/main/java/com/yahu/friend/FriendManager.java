package com.yahu.friend;

import com.google.gson.*;
import com.yahu.config.ConfigManager;

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FriendManager {
    private static final FriendManager INSTANCE = new FriendManager();
    private final Map<String, Friend> friends = new ConcurrentHashMap<>();
    private final Path file;

    private FriendManager() {
        Path mcDir = Path.of(System.getProperty("user.home"), ".minecraft", "Yahu Client");
        this.file = mcDir.resolve("friends.json");
        load();
    }

    public static FriendManager getInstance() {
        return INSTANCE;
    }

    public void add(String name, java.util.UUID uuid, String alias, Color color) {
        friends.put(name.toLowerCase(), new Friend(name, uuid, alias, color));
        save();
    }

    public void remove(String name) {
        friends.remove(name.toLowerCase());
        save();
    }

    public Friend get(String name) {
        return friends.get(name.toLowerCase());
    }

    public boolean isFriend(String name) {
        return friends.containsKey(name.toLowerCase());
    }

    public Collection<Friend> getAll() {
        return Collections.unmodifiableCollection(friends.values());
    }

    public void load() {
        if (!Files.exists(file)) return;
        try (Reader reader = Files.newBufferedReader(file)) {
            JsonArray array = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement elem : array) {
                JsonObject obj = elem.getAsJsonObject();
                String name = obj.get("name").getAsString();
                java.util.UUID uuid = obj.has("uuid") ? java.util.UUID.fromString(obj.get("uuid").getAsString()) : null;
                String alias = obj.has("alias") ? obj.get("alias").getAsString() : "";
                Color color = obj.has("color") ? new Color(obj.get("color").getAsInt()) : Color.WHITE;
                friends.put(name.toLowerCase(), new Friend(name, uuid, alias, color));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Files.createDirectories(file.getParent());
            JsonArray array = new JsonArray();
            for (Friend friend : friends.values()) {
                JsonObject obj = new JsonObject();
                obj.addProperty("name", friend.getName());
                if (friend.getUuid() != null) obj.addProperty("uuid", friend.getUuid().toString());
                obj.addProperty("alias", friend.getAlias());
                obj.addProperty("color", friend.getColor().getRGB());
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