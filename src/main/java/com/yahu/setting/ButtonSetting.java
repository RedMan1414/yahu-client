package com.yahu.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class ButtonSetting extends AbstractSetting<Runnable> {
    private final Runnable action;

    public ButtonSetting(String name, String description, Runnable action) {
        super(name, description, action);
        this.action = action;
    }

    @Override
    public SettingType getType() { return SettingType.BUTTON; }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive("button");
    }

    @Override
    public void deserialize(JsonElement element) {}

    public void click() {
        if (action != null) action.run();
    }

    @Override
    public Runnable getValue() { return action; }
    @Override
    public void setValue(Runnable value) {}
    @Override
    public Runnable getDefaultValue() { return action; }
}