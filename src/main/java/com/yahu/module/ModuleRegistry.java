package com.yahu.module;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleRegistry {
    private static final ModuleRegistry INSTANCE = new ModuleRegistry();
    private final Map<String, Module> modules = new ConcurrentHashMap<>();
    private final Map<Category, List<Module>> byCategory = new ConcurrentHashMap<>();

    private ModuleRegistry() {
        for (Category c : Category.values()) {
            byCategory.put(c, new ArrayList<>());
        }
    }

    public static ModuleRegistry getInstance() {
        return INSTANCE;
    }

    public void register(Module module) {
        String key = module.getName().toLowerCase();
        if (modules.containsKey(key)) {
            throw new IllegalStateException("Module already registered: " + module.getName());
        }
        modules.put(key, module);
        byCategory.get(module.getCategory()).add(module);

        for (String alias : module.getAliases()) {
            modules.put(alias.toLowerCase(), module);
        }
    }

    public Module get(String name) {
        return modules.get(name.toLowerCase());
    }

    public Collection<Module> getAll() {
        return Collections.unmodifiableCollection(modules.values());
    }

    public List<Module> getByCategory(Category category) {
        return Collections.unmodifiableList(byCategory.get(category));
    }

    public List<Module> getEnabled() {
        return modules.values().stream().filter(Module::isEnabled).toList();
    }

    public void enableAll() {
        modules.values().forEach(m -> m.setEnabled(true));
    }

    public void disableAll() {
        modules.values().forEach(m -> m.setEnabled(false));
    }
}