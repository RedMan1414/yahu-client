package xyz.yahuclient;

import com.yahu.config.ConfigManager;
import com.yahu.event.EventBus;
import com.yahu.module.ModuleRegistry;
import com.yahu.module.modules.*;
import com.yahu.packet.PacketManipulatorRegistry;
import com.yahu.packet.manipulators.NameProtectManipulator;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.client.Minecraft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YahuClient implements ModInitializer {
    public static final String MOD_ID = "yahu-client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Yahu Client...");

        // Register modules
        ModuleRegistry registry = ModuleRegistry.getInstance();
        registry.register(new KillAuraModule());
        registry.register(new ScaffoldModule());
        registry.register(new ESPModule());
        registry.register(new AutoCrystalModule());
        registry.register(new ExampleModule());

        // Register packet manipulators
        PacketManipulatorRegistry.getInstance().register(new NameProtectManipulator());

        // Load config
        ConfigManager.getInstance().load();

        // Register tick event
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            EventBus.getInstance().post(new com.yahu.event.events.EventTick(
                com.yahu.event.events.EventTick.Phase.POST,
                com.yahu.event.events.EventTick.Side.CLIENT
            ));
        });

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            EventBus.getInstance().post(new com.yahu.event.events.EventTick(
                com.yahu.event.events.EventTick.Phase.PRE,
                com.yahu.event.events.EventTick.Side.CLIENT
            ));
        });

        LOGGER.info("Yahu Client initialized!");
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
