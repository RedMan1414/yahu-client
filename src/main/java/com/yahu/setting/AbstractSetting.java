package com.yahu.setting;

import com.google.gson.*;

import java.lang.reflect.Type;

public abstract class AbstractSetting<T> implements Setting<T> {
    private final String name;
    private final String description;
    private final T defaultValue;
    private T value;
    private boolean visible = true;

    protected AbstractSetting(String name, String description, T defaultValue) {
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    @Override
    public String getName() { return name; }

    @Override
    public String getDescription() { return description; }

    @Override
    public T getValue() { return value; }

    @Override
    public void setValue(T value) { this.value = value; }

    @Override
    public T getDefaultValue() { return defaultValue; }

    @Override
    public boolean isVisible() { return visible; }

    @Override
    public void setVisible(boolean visible) { this.visible = visible; }

    public abstract JsonElement serialize();

    public abstract void deserialize(JsonElement element);

    protected static final Gson GSON = new GsonBuilder().create();
}