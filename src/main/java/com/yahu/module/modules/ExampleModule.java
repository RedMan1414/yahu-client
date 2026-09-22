package com.yahu.module.modules;

import com.yahu.module.Category;
import com.yahu.module.Module;
import com.yahu.module.ModuleInfo;
import com.yahu.setting.*;

@ModuleInfo(name = "ExampleModule", category = Category.MISC, description = "Example module demonstrating all setting types")
public class ExampleModule extends Module {
    // BoolSetting
    public final BoolSetting enabledSetting = new BoolSetting("Enabled", "Toggle the module", false);
    public final BoolSetting debugMode = new BoolSetting("Debug Mode", "Enable debug logging", false);

    // IntSetting
    public final IntSetting intSetting = new IntSetting("Integer", "An integer slider", 50, 0, 100);
    public final IntSetting cooldown = new IntSetting("Cooldown", "Cooldown in ticks", 20, 0, 1000, 5);

    // DoubleSetting
    public final DoubleSetting doubleSetting = new DoubleSetting("Double", "A double slider", 1.5, 0.0, 10.0);
    public final DoubleSetting range = new DoubleSetting("Range", "Target range", 4.5, 0.1, 10.0, 0.1);

    // DoubleHeadedSliderSetting
    public final DoubleHeadedSliderSetting.Range defaultRange = new DoubleHeadedSliderSetting.Range(2.0, 8.0);
    public final DoubleHeadedSliderSetting doubleSlider = new DoubleHeadedSliderSetting("Range Slider", "Min/Max range", defaultRange, 0.0, 20.0);
    public final DoubleHeadedSliderSetting healthRange = new DoubleHeadedSliderSetting("Health Range", "Health percentage range", new DoubleHeadedSliderSetting.Range(20, 80), 0, 100, 1, true);

    // EnumSetting
    public enum TestEnum { OPTION_A, OPTION_B, OPTION_C }
    public final EnumSetting<TestEnum> enumSetting = new EnumSetting<>("Enum", "Dropdown enum", TestEnum.OPTION_A, TestEnum.class);

    // StringSetting
    public final StringSetting stringSetting = new StringSetting("String", "Text input", "default");
    public final StringSetting customMessage = new StringSetting("Custom Message", "Message to send", "Hello World!");

    // ColorSetting
    public final ColorSetting colorSetting = new ColorSetting("Color", "Pick a color", new java.awt.Color(255, 0, 0, 255));
    public final ColorSetting accentColor = new ColorSetting("Accent", "Accent color", new java.awt.Color(0, 255, 0, 200));

    // KeybindSetting
    public final KeybindSetting keybindSetting = new KeybindSetting("Keybind", "Test keybind", new KeybindSetting.Keybind(-1, false));
    public final KeybindSetting toggleKey = new KeybindSetting("Toggle Key", "Key to toggle feature", new KeybindSetting.Keybind(-1, true));

    // ListSetting
    public enum Priority { DISTANCE, HEALTH, ARMOR, ANGLE }
    public final ListSetting<Priority> priorityList = new ListSetting<>("Priorities", "Target priorities", java.util.List.of(Priority.DISTANCE), Priority.class);

    // ButtonSetting
    public final ButtonSetting resetButton = new ButtonSetting("Reset Config", "Reset all settings to defaults", this::resetToDefaults);
    public final ButtonSetting printSettings = new ButtonSetting("Print Settings", "Print all settings to console", this::printAllSettings);

    // SettingGroup
    public final SettingGroup generalGroup = createGroup("General");
    public final SettingGroup combatGroup = createGroup("Combat");
    public final SettingGroup visualGroup = createGroup("Visual");

    public ExampleModule() {
        super(new ModuleInfo() {
            @Override public String name() { return "ExampleModule"; }
            @Override public Category category() { return Category.MISC; }
            @Override public String description() { return "Example module demonstrating all setting types"; }
            @Override public String[] aliases() { return new String[]{"ex", "example"}; }
            @Override public int keybind() { return -1; }
            @Override public boolean defaultEnabled() { return false; }
        });

        // General group
        generalGroup.add(enabledSetting);
        generalGroup.add(debugMode);
        generalGroup.add(intSetting);
        generalGroup.add(cooldown);
        generalGroup.add(doubleSetting);
        generalGroup.add(range);

        // Combat group
        combatGroup.add(doubleSlider);
        combatGroup.add(healthRange);
        combatGroup.add(enumSetting);
        combatGroup.add(priorityList);

        // Visual group
        visualGroup.add(colorSetting);
        visualGroup.add(accentColor);
        visualGroup.add(stringSetting);
        visualGroup.add(customMessage);
        visualGroup.add(keybindSetting);
        visualGroup.add(toggleKey);
        visualGroup.add(resetButton);
        visualGroup.add(printSettings);

        // Register all settings
        registerSetting(enabledSetting);
        registerSetting(debugMode);
        registerSetting(intSetting);
        registerSetting(cooldown);
        registerSetting(doubleSetting);
        registerSetting(range);
        registerSetting(doubleSlider);
        registerSetting(healthRange);
        registerSetting(enumSetting);
        registerSetting(stringSetting);
        registerSetting(customMessage);
        registerSetting(colorSetting);
        registerSetting(accentColor);
        registerSetting(keybindSetting);
        registerSetting(toggleKey);
        registerSetting(priorityList);
        registerSetting(resetButton);
        registerSetting(printSettings);
    }

    @Override
    public void onEnable() {
        if (debugMode.getValue()) {
            System.out.println("[ExampleModule] Enabled!");
        }
    }

    @Override
    public void onDisable() {
        if (debugMode.getValue()) {
            System.out.println("[ExampleModule] Disabled!");
        }
    }

    @Override
    public void onTick() {
        if (!enabledSetting.getValue()) return;

        if (keybindSetting.getValue().isPressed()) {
            if (debugMode.getValue()) {
                System.out.println("[ExampleModule] Keybind pressed!");
            }
        }
    }

    private void resetToDefaults() {
        for (com.yahu.setting.Setting<?> setting : getSettingRegistry().getAll()) {
            setting.setValue(setting.getDefaultValue());
        }
        System.out.println("[ExampleModule] Settings reset to defaults");
    }

    private void printAllSettings() {
        System.out.println("[ExampleModule] Current settings:");
        for (com.yahu.setting.Setting<?> setting : getSettingRegistry().getAll()) {
            System.out.println("  " + setting.getName() + " = " + setting.getValue());
        }
    }
}