package com.yahu.account;

import com.google.gson.*;
import com.yahu.config.ConfigManager;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AccountManager {
    private static final AccountManager INSTANCE = new AccountManager();
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();
    private final Path file;
    private Account activeAccount;

    private AccountManager() {
        Path mcDir = Path.of(System.getProperty("user.home"), ".minecraft", "Yahu Client");
        this.file = mcDir.resolve("accounts.nbt");
        load();
    }

    public static AccountManager getInstance() {
        return INSTANCE;
    }

    public void addAccount(String typeStr, String data) {
        Account.Type type = Account.Type.valueOf(typeStr.toUpperCase());
        String[] parts = data.split(":");
        String username = parts[0];
        String uuid = parts.length > 1 ? parts[1] : "";
        String accessToken = parts.length > 2 ? parts[2] : "";
        String refreshToken = parts.length > 3 ? parts[3] : "";

        Account account = new Account(type, username, uuid, accessToken, refreshToken);
        accounts.put(username.toLowerCase(), account);
        save();
    }

    public void removeAccount(String username) {
        accounts.remove(username.toLowerCase());
        save();
    }

    public Account getAccount(String username) {
        return accounts.get(username.toLowerCase());
    }

    public void switchAccount(Account account) {
        if (activeAccount != null) activeAccount.setActive(false);
        activeAccount = account;
        account.setActive(true);
        // Trigger re-login
        save();
    }

    public Collection<Account> getAccounts() {
        return Collections.unmodifiableCollection(accounts.values());
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public void load() {
        if (!Files.exists(file)) return;
        try (Reader reader = Files.newBufferedReader(file)) {
            JsonObject obj = JsonParser.parseReader(reader).getAsJsonObject();
            if (obj.has("accounts")) {
                for (JsonElement elem : obj.getAsJsonArray("accounts")) {
                    JsonObject acc = elem.getAsJsonObject();
                    Account.Type type = Account.Type.valueOf(acc.get("type").getAsString());
                    String username = acc.get("username").getAsString();
                    String uuid = acc.has("uuid") ? acc.get("uuid").getAsString() : "";
                    String accessToken = acc.has("accessToken") ? acc.get("accessToken").getAsString() : "";
                    String refreshToken = acc.has("refreshToken") ? acc.get("refreshToken").getAsString() : "";
                    Account account = new Account(type, username, uuid, accessToken, refreshToken);
                    accounts.put(username.toLowerCase(), account);
                    if (acc.has("active") && acc.get("active").getAsBoolean()) {
                        activeAccount = account;
                        account.setActive(true);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Files.createDirectories(file.getParent());
            JsonObject obj = new JsonObject();
            JsonArray array = new JsonArray();
            for (Account acc : accounts.values()) {
                JsonObject a = new JsonObject();
                a.addProperty("type", acc.getType().name());
                a.addProperty("username", acc.getUsername());
                a.addProperty("uuid", acc.getUuid());
                a.addProperty("accessToken", acc.getAccessToken());
                a.addProperty("refreshToken", acc.getRefreshToken());
                a.addProperty("active", acc.isActive());
                array.add(a);
            }
            obj.add("accounts", array);
            try (Writer writer = Files.newBufferedWriter(file)) {
                ConfigManager.getInstance().getClass().getDeclaredField("gson").setAccessible(true);
                com.google.gson.Gson gson = (com.google.gson.Gson) ConfigManager.getInstance().getClass().getDeclaredField("gson").get(ConfigManager.getInstance());
                gson.toJson(obj, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}