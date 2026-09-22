package com.yahu.module.modules;

import com.yahu.module.Category;
import com.yahu.module.Module;
import com.yahu.module.ModuleInfo;
import com.yahu.setting.*;

import java.util.*;

@ModuleInfo(name = "AutoMine", category = Category.WORLD, description = "Automatically mines specified blocks")
public class AutoMineModule extends Module {
    public final BoolSetting enabled = new BoolSetting("Enabled", "Toggle AutoMine", false);
    public final ListSetting<TargetBlock> blocks = new ListSetting<>("Blocks", "Blocks to mine", new ArrayList<>(), TargetBlock.class);
    public final IntSetting range = new IntSetting("Range", "Mining range", 4, 1, 6);
    public final EnumSetting<RotationMode> rotationMode = new EnumSetting<>("Rotation Mode", "Rotation mode", RotationMode.LEGIT, RotationMode.class);
    public final BoolSetting instant = new BoolSetting("Instant", "Instant break (packet mine)", false);
    public final BoolSetting switch_ = new BoolSetting("Auto Switch", "Auto switch to best tool", true);

    public enum TargetBlock {
        DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE, EMERALD_ORE, DEEPSLATE_EMERALD_ORE,
        ANCIENT_DEBRIS, NETHER_GOLD_ORE, COAL_ORE, DEEPSLATE_COAL_ORE,
        IRON_ORE, DEEPSLATE_IRON_ORE, COPPER_ORE, DEEPSLATE_COPPER_ORE,
        REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE, LAPIS_ORE, DEEPSLATE_LAPIS_ORE
    }

    public AutoMineModule() {
        super(new ModuleInfo() {
            @Override public String name() { return "AutoMine"; }
            @Override public Category category() { return Category.WORLD; }
            @Override public String description() { return "Automatically mines specified blocks"; }
            @Override public String[] aliases() { return new String[]{"am", "automine"}; }
            @Override public int keybind() { return -1; }
            @Override public boolean defaultEnabled() { return false; }
        });

        SettingGroup general = createGroup("General");
        general.add(enabled);
        general.add(range);
        general.add(rotationMode);
        general.add(instant);
        general.add(switch_);

        SettingGroup targets = createGroup("Targets");
        targets.add(blocks);

        registerSetting(enabled);
        registerSetting(blocks);
        registerSetting(range);
        registerSetting(rotationMode);
        registerSetting(instant);
        registerSetting(switch_);
    }

    public void setTargetBlock(String blockName) {
        try {
            TargetBlock block = TargetBlock.valueOf(blockName.toUpperCase());
            if (!blocks.getValue().contains(block)) {
                List<TargetBlock> newList = new ArrayList<>(blocks.getValue());
                newList.add(block);
                blocks.setValue(newList);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid block: " + blockName);
        }
    }

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {}

    @Override
    public void onTick() {}
}