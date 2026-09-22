package com.yahu.module.modules;

import com.yahu.module.Category;
import com.yahu.module.Module;
import com.yahu.module.ModuleInfo;
import com.yahu.setting.*;
import com.yahu.target.TargetSelector;

@ModuleInfo(name = "KillAura", category = Category.COMBAT, description = "Automatically attacks nearby entities")
public class KillAuraModule extends Module {
    public final BoolSetting enabled = new BoolSetting("Enabled", "Toggle KillAura", false);
    public final IntSetting range = new IntSetting("Range", "Attack range in blocks", 4, 1, 6);
    public final DoubleSetting rangeD = new DoubleSetting("Range (Precise)", "Precise attack range", 4.5, 0.1, 6.0, 0.1);
    public final EnumSetting<TargetSelector.TargetPriority> priority = new EnumSetting<>("Priority", "Target priority", TargetSelector.TargetPriority.DISTANCE, TargetSelector.TargetPriority.class);
    public final EnumSetting<RotationMode> rotationMode = new EnumSetting<>("Rotation Mode", "How to rotate", RotationMode.LEGIT, RotationMode.class);
    public final BoolSetting autoBlock = new BoolSetting("Auto Block", "Block while attacking", true);
    public final BoolSetting criticals = new BoolSetting("Critical Hits", "Jump for critical hits", false);
    public final IntSetting cps = new IntSetting("CPS", "Clicks per second", 12, 1, 20);
    public final BoolSetting swing = new BoolSetting("Swing", "Swing arm client-side", true);
    public final ColorSetting targetColor = new ColorSetting("Target Color", "ESP color for target", new java.awt.Color(255, 0, 0, 255));

    public KillAuraModule() {
        super(new ModuleInfo() {
            @Override public String name() { return "KillAura"; }
            @Override public Category category() { return Category.COMBAT; }
            @Override public String description() { return "Automatically attacks nearby entities"; }
            @Override public String[] aliases() { return new String[]{"ka", "aura"}; }
            @Override public int keybind() { return -1; }
            @Override public boolean defaultEnabled() { return false; }
        });

        SettingGroup general = createGroup("General");
        general.add(enabled);
        general.add(range);
        general.add(rangeD);
        general.add(priority);
        general.add(rotationMode);

        SettingGroup combat = createGroup("Combat");
        combat.add(autoBlock);
        combat.add(criticals);
        combat.add(cps);
        combat.add(swing);

        SettingGroup visual = createGroup("Visual");
        visual.add(targetColor);

        registerSetting(enabled);
        registerSetting(range);
        registerSetting(rangeD);
        registerSetting(priority);
        registerSetting(rotationMode);
        registerSetting(autoBlock);
        registerSetting(criticals);
        registerSetting(cps);
        registerSetting(swing);
        registerSetting(targetColor);
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Override
    public void onTick() {}
}