package com.yahu.module.modules;

import com.yahu.module.Category;
import com.yahu.module.Module;
import com.yahu.module.ModuleInfo;
import com.yahu.setting.*;
import com.yahu.rotation.RotationMode;

@ModuleInfo(name = "AutoCrystal", category = Category.COMBAT, description = "Automatically places and breaks crystals")
public class AutoCrystalModule extends Module {
    public final BoolSetting enabled = new BoolSetting("Enabled", "Toggle AutoCrystal", false);
    public final BoolSetting place = new BoolSetting("Place", "Place crystals", true);
    public final BoolSetting break_ = new BoolSetting("Break", "Break crystals", true);
    public final IntSetting placeRange = new IntSetting("Place Range", "Crystal place range", 6, 1, 6);
    public final IntSetting breakRange = new IntSetting("Break Range", "Crystal break range", 6, 1, 6);
    public final EnumSetting<RotationMode> rotationMode = new EnumSetting<>("Rotation Mode", "Rotation mode for placing/breaking", RotationMode.LEGIT, RotationMode.class);
    public final BoolSetting antiSuicide = new BoolSetting("Anti Suicide", "Don't place if self damage > target damage", true);
    public final DoubleSetting minDamage = new DoubleSetting("Min Damage", "Minimum damage to target", 4.0, 0.5, 20.0, 0.5);
    public final DoubleSetting maxSelfDamage = new DoubleSetting("Max Self Damage", "Maximum damage to self", 8.0, 0.5, 20.0, 0.5);
    public final BoolSetting facePlace = new BoolSetting("Face Place", "Place on face if feet blocked", true);
    public final IntSetting multiPlace = new IntSetting("Multi Place", "Crystals to place per tick", 1, 1, 4);

    public AutoCrystalModule() {
        super(new ModuleInfo() {
            @Override public String name() { return "AutoCrystal"; }
            @Override public Category category() { return Category.COMBAT; }
            @Override public String description() { return "Automatically places and breaks crystals"; }
            @Override public String[] aliases() { return new String[]{"ac", "crystal"}; }
            @Override public int keybind() { return -1; }
            @Override public boolean defaultEnabled() { return false; }
        });

        SettingGroup general = createGroup("General");
        general.add(enabled);
        general.add(place);
        general.add(break_);
        general.add(multiPlace);

        SettingGroup range = createGroup("Range");
        range.add(placeRange);
        range.add(breakRange);

        SettingGroup logic = createGroup("Logic");
        logic.add(rotationMode);
        logic.add(antiSuicide);
        logic.add(minDamage);
        logic.add(maxSelfDamage);
        logic.add(facePlace);

        registerSetting(enabled);
        registerSetting(place);
        registerSetting(break_);
        registerSetting(placeRange);
        registerSetting(breakRange);
        registerSetting(rotationMode);
        registerSetting(antiSuicide);
        registerSetting(minDamage);
        registerSetting(maxSelfDamage);
        registerSetting(facePlace);
        registerSetting(multiPlace);
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Override
    public void onTick() {}
}