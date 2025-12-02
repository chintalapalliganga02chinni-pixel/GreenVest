package com.greenvest.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.greenvest.common.JsonFileUtil;
import com.greenvest.common.Preconditions;

public class SystemConfig {

    private static final String CONFIG_FILE = "config.json";

    @JsonIgnore
    private final Path configPath;

    @JsonIgnore
    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    private double baseMinimumPrice = 1.0;

    public SystemConfig(JsonFileUtil fileUtil) {
        Preconditions.requireNonNull(fileUtil, "fileUtil is required");
        fileUtil.ensureDataDirectoryExists();
        this.configPath = fileUtil.resolve(CONFIG_FILE);
        load();
    }

    private void load() {
        try {
            if (!Files.exists(configPath)) {
                save();
                return;
            }
            SystemConfig stored = mapper.readValue(configPath.toFile(), SystemConfig.class);
            this.baseMinimumPrice = stored.baseMinimumPrice;
        } catch (IOException e) {
            System.err.println("Warning: could not load config.json, using defaults. " + e.getMessage());
        }
    }

    public synchronized void setBaseMinimumPrice(double price) {
        Preconditions.require(price >= 0, "Base minimum price cannot be negative");
        this.baseMinimumPrice = price;
        save();
    }

    public double getBaseMinimumPrice() {
        return baseMinimumPrice;
    }

    private void save() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(configPath.toFile(), this);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to save config.json at " + configPath, e);
        }
    }
}
