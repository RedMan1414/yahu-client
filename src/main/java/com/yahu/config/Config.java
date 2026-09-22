package com.yahu.config;

import com.google.gson.JsonObject;

import java.util.*;

public class Config {
    private int version = 4;
    private JsonObject modules = new JsonObject();
    private JsonObject hud = new JsonObject();
    private List<String> friends = new ArrayList<>();
    private String theme = "default";
    private String profile = "default";

    public int getVersion() { return version; }
    public void setVersion(int version) { this.version = version; }

    public JsonObject getModules() { return modules; }
    public void setModules(JsonObject modules) { this.modules = modules; }

    public JsonObject getHud() { return hud; }
    public void setHud(JsonObject hud) { this.hud = hud; }

    public List<String> getFriends() { return friends; }
    public void setFriends(List<String> friends) { this.friends = friends; }

    public String getTheme() { return theme; }
    public void setTheme(String theme) { this.theme = theme; }

    public String getProfile() { return profile; }
    public void setProfile(String profile) { this.profile = profile; }
}