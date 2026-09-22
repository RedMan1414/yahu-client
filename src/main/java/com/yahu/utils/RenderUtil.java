package com.yahu.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

public class RenderUtil {
    public static void drawRect(PoseStack matrices, float x, float y, float width, float height, int color) {
        // 2D rectangle drawing
    }

    public static void drawGradientRect(PoseStack matrices, float x, float y, float width, float height, int startColor, int endColor, boolean horizontal) {
        // Gradient rectangle
    }

    public static void drawOutlinedRect(PoseStack matrices, float x, float y, float width, float height, float lineWidth, int color) {
        // Outlined rectangle
    }

    public static void drawRoundedRect(PoseStack matrices, float x, float y, float width, float height, float radius, int color) {
        // Rounded rectangle
    }

    public static void drawLine(PoseStack matrices, float x1, float y1, float x2, float y2, float width, int color) {
        // Line drawing
    }

    public static void drawCircle(PoseStack matrices, float x, float y, float radius, int color, int segments) {
        // Circle drawing
    }
}