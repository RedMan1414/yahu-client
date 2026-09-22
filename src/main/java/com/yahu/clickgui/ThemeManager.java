package com.yahu.clickgui;

import com.google.gson.*;
import com.yahu.config.ConfigManager;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ThemeManager {
    private static final ThemeManager INSTANCE = new ThemeManager();
    private final Map<String, Theme> themes = new LinkedHashMap<>();
    private final Path themesDir;
    private Theme currentTheme;

    private ThemeManager() {
        Path mcDir = Path.of(System.getProperty("user.home"), ".minecraft", "Yahu Client", "themes");
        this.themesDir = mcDir;
        loadThemes();
        if (currentTheme == null) {
            currentTheme = new Theme();
            currentTheme.setName("default");
        }
    }

    public static ThemeManager getInstance() {
        return INSTANCE;
    }

    public void loadTheme(String name) {
        Theme theme = themes.get(name);
        if (theme != null) {
            currentTheme = theme;
        }
    }

    public void saveTheme(String name) {
        Theme theme = new Theme();
        theme.setName(name);
        theme.setBackgroundColor(currentTheme.getBackgroundColor());
        theme.setHeaderColor(currentTheme.getHeaderColor());
        theme.setAccentColor(currentTheme.getAccentColor());
        theme.setTextColor(currentTheme.getTextColor());
        theme.setDisabledColor(currentTheme.getDisabledColor());
        theme.setBorderColor(currentTheme.getBorderColor());
        theme.setHoverColor(currentTheme.getHoverColor());
        theme.setRounding(currentTheme.getRounding());
        theme.setPadding(currentTheme.getPadding());
        theme.setFontSize(currentTheme.getFontSize());
        theme.setShadows(currentTheme.isShadows());
        theme.setAnimationSpeed(currentTheme.getAnimationSpeed());
        themes.put(name, theme);
        saveThemeToFile(name, theme);
    }

    public void deleteTheme(String name) {
        if (name.equals("default")) return;
        themes.remove(name);
        try {
            Files.deleteIfExists(themesDir.resolve(name + ".json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> listThemes() {
        return new ArrayList<>(themes.keySet());
    }

    public Theme getCurrentTheme() { return currentTheme; }
    public String getCurrentThemeName() { return currentTheme != null ? currentTheme.getName() : "default"; }

    private void loadThemes() {
        try {
            Files.createDirectories(themesDir);
            if (Files.exists(themesDir)) {
                try (var stream = Files.list(themesDir)) {
                    stream.filter(p -> p.toString().endsWith(".json"))
                            .forEach(path -> {
                                try (Reader reader = Files.newBufferedReader(path)) {
                                    JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();
                                    Theme theme = Theme.fromJson(obj);
                                    themes.put(theme.getName(), theme);
                                    if (theme.getName().equals("default")) {
                                        currentTheme = theme;
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveThemeToFile(String name, Theme theme) {
        try {
            Files.createDirectories(themesDir);
            Path file = themesDir.resolve(name + ".json");
            try (Writer writer = Files.newBufferedWriter(file)) {
                ConfigManager.getInstance().getClass().getDeclaredField("gson").setAccessible(true);
                com.google.gson.Gson gson = (com.google.gson.Gson) ConfigManager.getInstance().getClass().getDeclaredField("gson").get(ConfigManager.getInstance());
                gson.toJson(theme.toJson(), writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}