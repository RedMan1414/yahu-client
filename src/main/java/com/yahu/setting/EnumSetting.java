package com.yahu.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class EnumSetting<E extends Enum<E>> extends AbstractSetting<E> {
    private final Class<E> enumClass;

    public EnumSetting(String name, String description, E defaultValue, Class<E> enumClass) {
        super(name, description, defaultValue);
        this.enumClass = enumClass;
    }

    public Class<E> getEnumClass() { return enumClass; }
    public E[] getValues() { return enumClass.getEnumConstants(); }

    @Override
    public SettingType getType() { return SettingType.ENUM; }

    @Override
    public JsonElement serialize() {
        return new JsonPrimitive(getValue().name());
    }

    @Override
    public void deserialize(JsonElement element) {
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            try {
                setValue(Enum.valueOf(enumClass, element.getAsString()));
            } catch (IllegalArgumentException ignored) {
                setValue(getDefaultValue());
            }
        }
    }
}