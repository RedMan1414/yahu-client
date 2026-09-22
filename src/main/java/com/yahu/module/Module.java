package com.yahu.module;

import com.yahu.event.EventBus;
import com.yahu.setting.Setting;
import com.yahu.setting.SettingGroup;
import com.yahu.setting.SettingRegistry;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    private final String name;
    private final Category category;
    private final String description;
    private final String[] aliases;
    private final int defaultKeybind;
    private boolean enabled;
    private int keybind;
    private boolean holdMode;
    private boolean visibleInArrayList = true;
    private boolean draggable = false;
    private final SettingRegistry settingRegistry = new SettingRegistry();
    private final List<SettingGroup> settingGroups = new ArrayList<>();

    protected Module(ModuleInfo info) {
        this.name = info.name();
        this.category = info.category();
        this.description = info.description();
        this.aliases = info.aliases();
        this.defaultKeybind = info.keybind();
        this.keybind = info.keybind();
        this.enabled = info.defaultEnabled();
    }

    protected Module(String name, Category category, String description, String[] aliases, int keybind, boolean defaultEnabled) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.aliases = aliases;
        this.defaultKeybind = keybind;
        this.keybind = keybind;
        this.enabled = defaultEnabled;
    }

    public final String getName() { return name; }
    public final Category getCategory() { return category; }
    public final String getDescription() { return description; }
    public final String[] getAliases() { return aliases; }
    public final int getKeybind() { return keybind; }
    public final void setKeybind(int keybind) { this.keybind = keybind; }
    public final boolean isHoldMode() { return holdMode; }
    public final void setHoldMode(boolean holdMode) { this.holdMode = holdMode; }
    public final boolean isEnabled() { return enabled; }
    public final boolean isVisibleInArrayList() { return visibleInArrayList; }
    public final void setVisibleInArrayList(boolean visible) { this.visibleInArrayList = visible; }
    public final boolean isDraggable() { return draggable; }
    public final void setDraggable(boolean draggable) { this.draggable = draggable; }

    public final SettingRegistry getSettingRegistry() { return settingRegistry; }
    public final List<SettingGroup> getSettingGroups() { return settingGroups; }

    protected final <T> Setting<T> registerSetting(Setting<T> setting) {
        return settingRegistry.register(setting);
    }

    protected final SettingGroup createGroup(String name) {
        SettingGroup group = new SettingGroup(name);
        settingGroups.add(group);
        return group;
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}

    protected final void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;
        if (enabled) {
            EventBus.getInstance().subscribe(com.yahu.event.events.EventTick.class, this);
            onEnable();
        } else {
            EventBus.getInstance().unsubscribe(com.yahu.event.events.EventTick.class, this);
            onDisable();
        }
    }

    public void toggle() {
        setEnabled(!enabled);
    }
}