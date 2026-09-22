package com.yahu.setting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.*;
import java.util.stream.Collectors;

public class ListSetting<E extends Enum<E>> extends AbstractSetting<List<E>> {
    private final Class<E> enumClass;

    public ListSetting(String name, String description, List<E> defaultValue, Class<E> enumClass) {
        super(name, description, new ArrayList<>(defaultValue));
        this.enumClass = enumClass;
    }

    public Class<E> getEnumClass() { return enumClass; }
    public E[] getValues() { return enumClass.getEnumConstants(); }

    @Override
    public SettingType getType() { return SettingType.LIST; }

    @Override
    public JsonElement serialize() {
        JsonArray array = new JsonArray();
        for (E e : getValue()) {
            array.add(new JsonPrimitive(e.name()));
        }
        return array;
    }

    @Override
    public void deserialize(JsonElement element) {
        if (element.isJsonArray()) {
            List<E> list = new ArrayList<>();
            for (JsonElement e : element.getAsJsonArray()) {
                if (e.isJsonPrimitive() && e.getAsJsonPrimitive().isString()) {
                    try {
                        list.add(Enum.valueOf(enumClass, e.getAsString()));
                    } catch (IllegalArgumentException ignored) {}
                }
            }
            setValue(list);
        }
    }

    public boolean contains(E value) {
        return getValue().contains(value);
    }

    public void toggle(E value) {
        List<E> list = new ArrayList<>(getValue());
        if (list.contains(value)) {
            list.remove(value);
        } else {
            list.add(value);
        }
        setValue(list);
    }
}