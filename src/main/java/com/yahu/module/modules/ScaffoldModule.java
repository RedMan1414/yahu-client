package com.yahu.module.modules;

import com.yahu.module.Category;
import com.yahu.module.Module;
import com.yahu.module.ModuleInfo;
import com.yahu.setting.*;

@ModuleInfo(name = "Scaffold", category = Category.MOVEMENT, description = "Automatically places blocks under you")
public class ScaffoldModule extends Module {
    public enum ScaffoldMode { NORMAL, SWAY, MOON, GODBRIDGE, BREEZILY, CUSTOM }

    public final BoolSetting enabled = new BoolSetting("Enabled", "Toggle Scaffold", false);
    public final EnumSetting<ScaffoldMode> mode = new EnumSetting<>("Mode", "Scaffold mode", ScaffoldMode.NORMAL, ScaffoldMode.class);
    public final IntSetting expand = new IntSetting("Expand", "Blocks to expand", 1, 0, 5);
    public final IntSetting tower = new IntSetting("Tower", "Auto tower height", 0, 0, 10);
    public final BoolSetting rotate = new BoolSetting("Rotate", "Rotate to place blocks", true);
    public final BoolSetting sprint = new BoolSetting("Sprint", "Sprint while scaffolding", false);
    public final DoubleSetting delay = new DoubleSetting("Delay", "Place delay (ticks)", 0.0, 0.0, 10.0, 0.5);

    public ScaffoldModule() {
        super(new ModuleInfo() {
            @Override public String name() { return "Scaffold"; }
            @Override public Category category() { return Category.MOVEMENT; }
            @Override public String description() { return "Automatically places blocks under you"; }
            @Override public String[] aliases() { return new String[]{"scaf"}; }
            @Override public int keybind() { return -1; }
            @Override public boolean defaultEnabled() { return false; }
        });

        SettingGroup general = createGroup("General");
        general.add(enabled);
        general.add(mode);
        general.add(expand);
        general.add(tower);

        SettingGroup behavior = createGroup("Behavior");
        behavior.add(rotate);
        behavior.add(sprint);
        behavior.add(delay);

        registerSetting(enabled);
        registerSetting(mode);
        registerSetting(expand);
        registerSetting(tower);
        registerSetting(rotate);
        registerSetting(sprint);
        registerSetting(delay);
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Override
    public void onTick() {}
}