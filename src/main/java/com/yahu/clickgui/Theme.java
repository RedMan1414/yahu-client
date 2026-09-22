package com.yahu.clickgui;

import com.google.gson.*;

public class Theme {
    private String name;
    private int backgroundColor = 0xFF1E1E1E;
    private int headerColor = 0xFF2D2D2D;
    private int accentColor = 0xFF00BFFF;
    private int textColor = 0xFFFFFFFF;
    private int disabledColor = 0xFF888888;
    private int borderColor = 0xFF3D3D3D;
    private int hoverColor = 0xFF3A3A3A;
    private float rounding = 4.0f;
    private int padding = 8;
    private int fontSize = 14;
    private boolean shadows = true;
    private float animationSpeed = 0.15f;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(int backgroundColor) { this.backgroundColor = backgroundColor; }
    public int getHeaderColor() { return headerColor; }
    public void setHeaderColor(int headerColor) { this.headerColor = headerColor; }
    public int getAccentColor() { return accentColor; }
    public void setAccentColor(int accentColor) { this.accentColor = accentColor; }
    public int getTextColor() { return textColor; }
    public void setTextColor(int textColor) { this.textColor = textColor; }
    public int getDisabledColor() { return disabledColor; }
    public void setDisabledColor(int disabledColor) { this.disabledColor = disabledColor; }
    public int getBorderColor() { return borderColor; }
    public void setBorderColor(int borderColor) { this.borderColor = borderColor; }
    public int getHoverColor() { return hoverColor; }
    public void setHoverColor(int hoverColor) { this.hoverColor = hoverColor; }
    public float getRounding() { return rounding; }
    public void setRounding(float rounding) { this.rounding = rounding; }
    public int getPadding() { return padding; }
    public void setPadding(int padding) { this.padding = padding; }
    public int getFontSize() { return fontSize; }
    public void setFontSize(int fontSize) { this.fontSize = fontSize; }
    public boolean isShadows() { return shadows; }
    public void setShadows(boolean shadows) { this.shadows = shadows; }
    public float getAnimationSpeed() { return animationSpeed; }
    public void setAnimationSpeed(float animationSpeed) { this.animationSpeed = animationSpeed; }

    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("name", name);
        obj.addProperty("backgroundColor", backgroundColor);
        obj.addProperty("headerColor", headerColor);
        obj.addProperty("accentColor", accentColor);
        obj.addProperty("textColor", textColor);
        obj.addProperty("disabledColor", disabledColor);
        obj.addProperty("borderColor", borderColor);
        obj.addProperty("hoverColor", hoverColor);
        obj.addProperty("rounding", rounding);
        obj.addProperty("padding", padding);
        obj.addProperty("fontSize", fontSize);
        obj.addProperty("shadows", shadows);
        obj.addProperty("animationSpeed", animationSpeed);
        return obj;
    }

    public static Theme fromJson(JsonObject obj) {
        Theme theme = new Theme();
        theme.name = obj.has("name") ? obj.get("name").getAsString() : "default";
        theme.backgroundColor = obj.has("backgroundColor") ? obj.get("backgroundColor").getAsInt() : 0xFF1E1E1E;
        theme.headerColor = obj.has("headerColor") ? obj.get("headerColor").getAsInt() : 0xFF2D2D2D;
        theme.accentColor = obj.has("accentColor") ? obj.get("accentColor").getAsInt() : 0xFF00BFFF;
        theme.textColor = obj.has("textColor") ? obj.get("textColor").getAsInt() : 0xFFFFFFFF;
        theme.disabledColor = obj.has("disabledColor") ? obj.get("disabledColor").getAsInt() : 0xFF888888;
        theme.borderColor = obj.has("borderColor") ? obj.get("borderColor").getAsInt() : 0xFF3D3D3D;
        theme.hoverColor = obj.has("hoverColor") ? obj.get("hoverColor").getAsInt() : 0xFF3A3A3A;
        theme.rounding = obj.has("rounding") ? obj.get("rounding").getAsFloat() : 4.0f;
        theme.padding = obj.has("padding") ? obj.get("padding").getAsInt() : 8;
        theme.fontSize = obj.has("fontSize") ? obj.get("fontSize").getAsInt() : 14;
        theme.shadows = obj.has("shadows") ? obj.get("shadows").getAsBoolean() : true;
        theme.animationSpeed = obj.has("animationSpeed") ? obj.get("animationSpeed").getAsFloat() : 0.15f;
        return theme;
    }
}