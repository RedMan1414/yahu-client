package xyz.yahuclient.client;

import com.yahu.config.ConfigManager;
import com.yahu.event.EventBus;
import com.yahu.event.events.EventRender;
import com.yahu.module.ModuleRegistry;
import com.yahu.render.Renderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.vertex.PoseStack;

public class YahuClientClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Initialize renderer
        Renderer.init();

        // Register render events
        HudRenderCallback.EVENT.register((guiGraphics, tickDelta) -> {
            PoseStack matrices = guiGraphics.pose();
            EventBus.getInstance().post(new EventRender(EventRender.Type.GUI, matrices, tickDelta));
            EventBus.getInstance().post(new EventRender(EventRender.Type.WORLD_2D, matrices, tickDelta));
        });

        // World render events are handled via mixins
    }
}