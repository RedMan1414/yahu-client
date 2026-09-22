package com.yahu.setting;

import java.util.ArrayList;
import java.util.List;

public class SettingGroup {
    private final String name;
    private final List<Setting<?>> settings = new ArrayList<>();
    private boolean expanded = true;

    public SettingGroup(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    public List<Setting<?>> getSettings() { return settings; }
    public boolean isExpanded() { return expanded; }
    public void setExpanded(boolean expanded) { this.expanded = expanded; }

    public <T> Setting<T> add(Setting<T> setting) {
        settings.add(setting);
        return setting;
    }
}