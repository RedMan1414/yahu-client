package com.yahu.account;

import java.awt.*;

public class Account {
    public enum Type { MICROSOFT, OFFLINE, CRACKED, SESSION }

    private final Type type;
    private final String username;
    private final String uuid;
    private final String accessToken;
    private final String refreshToken;
    private boolean active;

    public Account(Type type, String username, String uuid, String accessToken, String refreshToken) {
        this.type = type;
        this.username = username;
        this.uuid = uuid;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Type getType() { return type; }
    public String getUsername() { return username; }
    public String getUuid() { return uuid; }
    public String getAccessToken() { return accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}