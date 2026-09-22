package com.yahu.setting;

public interface Setting<T> {
    String getName();
    String getDescription();
    T getValue();
    void setValue(T value);
    T getDefaultValue();
    SettingType getType();
    boolean isVisible();
    void setVisible(boolean visible);
}