package com.yahu.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import net.minecraft.client.KeyMapping;

public class KeybindSetting extends AbstractSetting<Keybind> {
    public KeybindSetting(String name, String description, Keybind defaultValue) {
        super(name, description, defaultValue);
    }

    @Override
    public SettingType getType() { return SettingType.KEYBIND; }

    @Override
    public JsonElement serialize() {
        JsonObject obj = new JsonObject();
        Keybind kb = getValue();
        obj.addProperty("key", kb.getKey());
        obj.addProperty("holdMode", kb.isHoldMode());
        return obj;
    }

    @Override
    public void deserialize(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            int key = obj.has("key") ? obj.get("key").getAsInt() : -1;
            boolean hold = obj.has("holdMode") && obj.get("holdMode").getAsBoolean();
            setValue(new Keybind(key, hold));
        }
    }

    public static class Keybind {
        private int key;
        private boolean holdMode;

        public Keybind(int key, boolean holdMode) {
            this.key = key;
            this.holdMode = holdMode;
        }

        public int getKey() { return key; }
        public void setKey(int key) { this.key = key; }
        public boolean isHoldMode() { return holdMode; }
        public void setHoldMode(boolean holdMode) { this.holdMode = holdMode; }

        public boolean isPressed() {
            if (key == -1) return false;
            KeyMapping mapping = KeyMapping.get(key);
            return mapping != null && mapping.isDown();
        }

        public boolean matches(int key, boolean holdMode) {
            return this.key == key && this.holdMode == holdMode;
        }

        @Override
        public String toString() {
            if (key == -1) return "None";
            String name = KeyMapping.get(key) != null ? KeyMapping.get(key).getTranslatedKeyMessage().getString() : "Unknown";
            return name + (holdMode ? " (Hold)" : "");
        }
    }
}