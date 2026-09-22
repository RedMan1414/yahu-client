package com.yahu.setting;

import com.google.gson.JsonObject;

import java.util.*;

public class SettingRegistry {
    private final Map<String, Setting<?>> settings = new LinkedHashMap<>();
    private final Map<String, SettingGroup> groups = new LinkedHashMap<>();

    public <T> Setting<T> register(Setting<T> setting) {
        settings.put(setting.getName(), setting);
        return setting;
    }

    public SettingGroup getOrCreateGroup(String name) {
        return groups.computeIfAbsent(name, SettingGroup::new);
    }

    public Collection<Setting<?>> getAll() {
        return settings.values();
    }

    public Setting<?> get(String name) {
        return settings.get(name);
    }

    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        for (Setting<?> setting : settings.values()) {
            obj.add(setting.getName(), setting.serialize());
        }
        return obj;
    }

    public void deserialize(JsonObject obj) {
        for (Map.Entry<String, com.google.gson.JsonElement> entry : obj.entrySet()) {
            Setting<?> setting = settings.get(entry.getKey());
            if (setting != null) {
                setting.deserialize(entry.getValue());
            }
        }
    }
}