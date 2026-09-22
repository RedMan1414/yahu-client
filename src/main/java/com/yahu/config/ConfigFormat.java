package com.yahu.config;

public class ConfigFormat {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static String toJson(Config config) {
        return GSON.toJson(config);
    }

    public static Config fromJson(String json) {
        return GSON.fromJson(json, Config.class);
    }

    public static String toBase64(Config config) {
        return Base64.getEncoder().encodeToJson(toJson(config));
    }

    public static Config fromBase64(String base64) {
        return fromJson(new String(Base64.getDecoder().decode(base64)));
    }
}