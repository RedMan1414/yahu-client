package com.yahu.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;

public class Renderer {
    private static Minecraft mc;
    private static MultiBufferSource bufferSource;

    public static void init() {
        mc = Minecraft.getInstance();
    }

    public static void begin(MultiBufferSource buffer) {
        bufferSource = buffer;
    }

    public static void end() {
        bufferSource = null;
    }

    public static void drawBox(Vec3 min, Vec3 max, int color, float lineWidth) {
        // Implementation for drawing 3D boxes
    }

    public static void drawLine(Vec3 start, Vec3 end, int color, float width) {
        // Implementation for drawing lines
    }

    public static void drawRect(float x, float y, float width, float height, int color) {
        // Implementation for 2D rectangles
    }

    public static void drawGradientRect(float x, float y, float width, float height, int startColor, int endColor, boolean horizontal) {
        // Implementation for gradient rectangles
    }

    public static Vec3 worldToScreen(Vec3 worldPos) {
        // Implementation for world to screen projection
        return Vec3.ZERO;
    }

    public static PoseStack getMatrixStack() {
        return mc.gameRenderer.getMainRenderTarget().getMainRenderTarget() != null
                ? new PoseStack() : new PoseStack();
    }

    public static VertexConsumer getBuffer(RenderType type) {
        return bufferSource != null ? bufferSource.getBuffer(type) : null;
    }
}