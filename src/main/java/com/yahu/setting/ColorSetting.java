package com.yahu.setting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.awt.*;

public class ColorSetting extends AbstractSetting<Color> {
    private boolean rainbow;
    private double rainbowSpeed = 1.0;

    public ColorSetting(String name, String description, Color defaultValue) {
        super(name, description, defaultValue);
    }

    @Override
    public SettingType getType() { return SettingType.COLOR; }

    public boolean isRainbow() { return rainbow; }
    public void setRainbow(boolean rainbow) { this.rainbow = rainbow; }
    public double getRainbowSpeed() { return rainbowSpeed; }
    public void setRainbowSpeed(double speed) { this.rainbowSpeed = speed; }

    @Override
    public JsonElement serialize() {
        JsonObject obj = new JsonObject();
        Color c = getValue();
        obj.addProperty("r", c.getRed());
        obj.addProperty("g", c.getGreen());
        obj.addProperty("b", c.getBlue());
        obj.addProperty("a", c.getAlpha());
        obj.addProperty("rainbow", rainbow);
        obj.addProperty("rainbowSpeed", rainbowSpeed);
        return obj;
    }

    @Override
    public void deserialize(JsonElement element) {
        if (element.isJsonObject()) {
            JsonObject obj = element.getAsJsonObject();
            int r = obj.has("r") ? obj.get("r").getAsInt() : 255;
            int g = obj.has("g") ? obj.get("g").getAsInt() : 255;
            int b = obj.has("b") ? obj.get("b").getAsInt() : 255;
            int a = obj.has("a") ? obj.get("a").getAsInt() : 255;
            setValue(new Color(r, g, b, a));
            if (obj.has("rainbow")) setRainbow(obj.get("rainbow").getAsBoolean());
            if (obj.has("rainbowSpeed")) setRainbowSpeed(obj.get("rainbowSpeed").getAsDouble());
        }
    }

    public int getRGBA() {
        return getValue().getRGB();
    }

    public float[] getHSVA() {
        Color c = getValue();
        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), null);
        return new float[]{hsb[0], hsb[1], hsb[2], c.getAlpha() / 255f};
    }
}