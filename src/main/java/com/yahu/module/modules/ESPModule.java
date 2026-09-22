package com.yahu.module.modules;

import com.yahu.module.Category;
import com.yahu.module.Module;
import com.yahu.module.ModuleInfo;
import com.yahu.setting.*;

import java.awt.*;

@ModuleInfo(name = "ESP", category = Category.RENDER, description = "Entity ESP with boxes, tracers, chams, etc.")
public class ESPModule extends Module {
    public final BoolSetting enabled = new BoolSetting("Enabled", "Toggle ESP", false);
    public final BoolSetting players = new BoolSetting("Players", "Render player ESP", true);
    public final BoolSetting mobs = new BoolSetting("Mobs", "Render mob ESP", false);
    public final BoolSetting items = new BoolSetting("Items", "Render item ESP", false);
    public final BoolSetting containers = new BoolSetting("Containers", "Render container ESP", true);
    public final BoolSetting crystals = new BoolSetting("Crystals", "Render crystal ESP", true);

    public final EnumSetting<BoxMode> boxMode = new EnumSetting<>("Box Mode", "Box render mode", BoxMode.BOX_2D, BoxMode.class);
    public final BoolSetting skeleton = new BoolSetting("Skeleton", "Render skeleton", false);
    public final BoolSetting tracers = new BoolSetting("Tracers", "Render tracers", false);
    public final BoolSetting chams = new BoolSetting("Chams", "Render chams", false);
    public final BoolSetting nametags = new BoolSetting("Nametags", "Render nametags", true);
    public final BoolSetting health = new BoolSetting("Health", "Show health", true);
    public final BoolSetting armor = new BoolSetting("Armor", "Show armor", true);

    public final ColorSetting playerColor = new ColorSetting("Player Color", "Color for players", new Color(255, 255, 255, 255));
    public final ColorSetting mobColor = new ColorSetting("Mob Color", "Color for mobs", new Color(255, 0, 0, 255));
    public final ColorSetting itemColor = new ColorSetting("Item Color", "Color for items", new Color(255, 255, 0, 255));
    public final ColorSetting containerColor = new ColorSetting("Container Color", "Color for containers", new Color(0, 255, 255, 255));

    public enum BoxMode { OFF, BOX_2D, BOX_3D, CORNERS }

    public ESPModule() {
        super(new ModuleInfo() {
            @Override public String name() { return "ESP"; }
            @Override public Category category() { return Category.RENDER; }
            @Override public String description() { return "Entity ESP with boxes, tracers, chams, etc."; }
            @Override public String[] aliases() { return new String[]{"esp"}; }
            @Override public int keybind() { return -1; }
            @Override public boolean defaultEnabled() { return false; }
        });

        SettingGroup targets = createGroup("Targets");
        targets.add(enabled);
        targets.add(players);
        targets.add(mobs);
        targets.add(items);
        targets.add(containers);
        targets.add(crystals);

        SettingGroup render = createGroup("Render");
        render.add(boxMode);
        render.add(skeleton);
        render.add(tracers);
        render.add(chams);
        render.add(nametags);
        render.add(health);
        render.add(armor);

        SettingGroup colors = createGroup("Colors");
        colors.add(playerColor);
        colors.add(mobColor);
        colors.add(itemColor);
        colors.add(containerColor);

        registerSetting(enabled);
        registerSetting(players);
        registerSetting(mobs);
        registerSetting(items);
        registerSetting(containers);
        registerSetting(crystals);
        registerSetting(boxMode);
        registerSetting(skeleton);
        registerSetting(tracers);
        registerSetting(chams);
        registerSetting(nametags);
        registerSetting(health);
        registerSetting(armor);
        registerSetting(playerColor);
        registerSetting(mobColor);
        registerSetting(itemColor);
        registerSetting(containerColor);
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Override
    public void onTick() {}
}