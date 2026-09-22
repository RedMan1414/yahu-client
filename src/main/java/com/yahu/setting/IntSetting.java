package com.yahu.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class IntSetting extends AbstractSetting<Integer> {
    private final int min;
    private final int max;
    private final int step;

    public IntSetting(String name, String description, int defaultValue, int min, int max) {
        this(name, description, defaultValue, min, max, 1);
    }

    public IntSetting(String name, String description, int defaultValue, int min, int max, int step) {
        super(name, description, clamp(defaultValue, min, max));
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public int getMin() { return min; }
    public int getMax() { return max; }
    public int getStep() { return step; }

    @Override
    public SettingType getType() { return SettingType.INT; }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(getValue());
    }

    @Override
    public void deserialize(JsonElement element) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isNumber()) {
            setValue(clamp(element.getAsInt(), min, max));
        }
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}