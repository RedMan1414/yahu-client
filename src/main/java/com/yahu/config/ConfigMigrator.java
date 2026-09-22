package com.yahu.config;

public class ConfigMigrator {
    public static Config migrate(Config config) {
        int version = config.getVersion();
        if (version < 4) {
            // Migration logic for older versions
            config.setVersion(4);
        }
        return config;
    }
}