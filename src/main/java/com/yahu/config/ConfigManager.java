package com.yahu.config;

import com.google.gson.*;
import com.yahu.module.Module;
import com.yahu.module.ModuleRegistry;
import com.yahu.setting.SettingRegistry;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ConfigManager {
    private static final ConfigManager INSTANCE = new ConfigManager();
    private final Path configDir;
    private final Path profilesDir;
    private final Path serversDir;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Config currentConfig = new Config();
    private String currentProfile = "default";
    private String currentServer = "";

    private ConfigManager() {
        Path mcDir = Path.of(System.getProperty("user.home"), ".minecraft", "Yahu Client");
        this.configDir = mcDir.resolve("configs");
        this.profilesDir = configDir.resolve("profiles");
        this.serversDir = configDir.resolve("servers");
        createDirectories();
    }

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    private void createDirectories() {
        try {
            Files.createDirectories(configDir);
            Files.createDirectories(profilesDir);
            Files.createDirectories(serversDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        loadProfile("default");
        loadServerConfig();
    }

    public void loadProfile(String name) {
        currentProfile = name;
        Path file = profilesDir.resolve(name + ".json");
        if (Files.exists(file)) {
            try (Reader reader = Files.newBufferedReader(file)) {
                currentConfig = gson.fromJson(reader, Config.class);
                if (currentConfig == null) currentConfig = new Config();
            } catch (IOException e) {
                e.printStackTrace();
                currentConfig = new Config();
            }
        } else {
            currentConfig = new Config();
        }
        applyConfig();
    }

    public void loadServerConfig() {
        currentServer = getServerAddress();
        if (currentServer.isEmpty()) return;

        Path file = serversDir.resolve(sanitize(currentServer) + ".json");
        if (Files.exists(file)) {
            try (Reader reader = Files.newBufferedReader(file)) {
                Config serverConfig = gson.fromJson(reader, Config.class);
                if (serverConfig != null) {
                    currentConfig = serverConfig;
                    applyConfig();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getServerAddress() {
        return net.minecraft.client.Minecraft.getInstance().getSingleplayerServer() != null
                ? "localhost"
                : (net.minecraft.client.Minecraft.getInstance().getConnection() != null
                        ? net.minecraft.client.Minecraft.getInstance().getConnection().getRemoteAddress().toString()
                        : "");
    }

    public void save() {
        saveProfile(currentProfile);
        saveServerConfig();
    }

    public void saveProfile(String name) {
        collectConfig();
        Path file = profilesDir.resolve(name + ".json");
        try (Writer writer = Files.newBufferedWriter(file)) {
            gson.toJson(currentConfig, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveServerConfig() {
        if (currentServer.isEmpty()) return;
        collectConfig();
        Path file = serversDir.resolve(sanitize(currentServer) + ".json");
        try (Writer writer = Files.newBufferedWriter(file)) {
            gson.toJson(currentConfig, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void collectConfig() {
        JsonObject modulesObj = new JsonObject();
        for (Module module : ModuleRegistry.getInstance().getAll()) {
            modulesObj.add(module.getName(), module.getSettingRegistry().serialize());
        }
        currentConfig.setModules(modulesObj);
        currentConfig.setVersion(4);
    }

    private void applyConfig() {
        JsonObject modulesObj = currentConfig.getModules();
        for (Module module : ModuleRegistry.getInstance().getAll()) {
            if (modulesObj.has(module.getName())) {
                module.getSettingRegistry().deserialize(modulesObj.get(module.getName()).getAsJsonObject());
            }
        }
    }

    public void deleteProfile(String name) {
        if (name.equals("default")) return;
        Path file = profilesDir.resolve(name + ".json");
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> listProfiles() {
        try {
            return Files.list(profilesDir)
                    .filter(p -> p.toString().endsWith(".json"))
                    .map(p -> p.getFileName().toString().replace(".json", ""))
                    .toList();
        } catch (IOException e) {
            return List.of();
        }
    }

    public String exportConfig() {
        collectConfig();
        return Base64.getEncoder().encodeToString(gson.toJson(currentConfig).getBytes());
    }

    public void importConfig(String base64) {
        try {
            String json = new String(Base64.getDecoder().decode(base64));
            currentConfig = gson.fromJson(json, Config.class);
            applyConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Config getCurrentConfig() { return currentConfig; }
    public String getCurrentProfile() { return currentProfile; }

    private String sanitize(String s) {
        return s.replaceAll("[^a-zA-Z0-9.-]", "_");
    }
}