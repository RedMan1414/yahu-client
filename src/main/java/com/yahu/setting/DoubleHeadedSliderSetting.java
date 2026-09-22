package com.yahu.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class DoubleHeadedSliderSetting extends AbstractSetting<Range> {
    private final double min;
    private final double max;
    private final double step;
    private final boolean integer;

    public DoubleHeadedSliderSetting(String name, String description, Range defaultValue, double min, double max) {
        this(name, description, defaultValue, min, max, 0.1, false);
    }

    public DoubleHeadedSliderSetting(String name, String description, Range defaultValue, double min, double max, double step, boolean integer) {
        super(name, description, clamp(defaultValue, min, max));
        this.min = min;
        this.max = max;
        this.step = step;
        this.integer = integer;
    }

    public double getMin() { return min; }
    public double getMax() { return max; }
    public double getStep() { return step; }
    public boolean isInteger() { return integer; }

    @Override
    public SettingType getType() { return SettingType.DOUBLE_HEADED_SLIDER; }

    @Override
    public JsonElement serialize() {
        return getValue().toJson();
    }

    @Override
    public void deserialize(JsonElement element) {
        if (element.isJsonObject()) {
            setValue(Range.fromJson(element.getAsJsonObject()));
        }
    }

    public static Range clamp(Range range, double min, double max) {
        return new Range(
            Math.max(min, Math.min(max, range.min)),
            Math.max(min, Math.min(max, range.max))
        );
    }

    public static class Range {
        public final double min;
        public final double max;

        public Range(double min, double max) {
            this.min = Math.min(min, max);
            this.max = Math.max(min, max);
        }

        public com.google.gson.JsonObject toJson() {
            com.google.gson.JsonObject obj = new com.google.gson.JsonObject();
            obj.addProperty("min", min);
            obj.addProperty("max", max);
            return obj;
        }

        public static Range fromJson(com.google.gson.JsonObject obj) {
            return new Range(obj.get("min").getAsDouble(), obj.get("max").getAsDouble());
        }

        @Override
        public String toString() {
            return String.format("[%.2f, %.2f]", min, max);
        }
    }
}