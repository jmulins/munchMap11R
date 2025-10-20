package me.jmulins.munchmap11r.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class PhelperConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final PhelperConfig INSTANCE = new PhelperConfig();

    public boolean enableFeature = true;
    public String playerName = "";
    public int scale = 50;
    public boolean showNotifications = false;

    private PhelperConfig() {
        load();
    }

    private File getConfigFile() {
        Path configDir = FabricLoader.getInstance().getConfigDir();
        return configDir.resolve("phelper.json").toFile();
    }

    public void openGui() {
        MinecraftClient.getInstance().send(() -> {
            MinecraftClient.getInstance().setScreen(new ConfigScreen(MinecraftClient.getInstance().currentScreen));
        });
    }

    public void load() {
        try {
            File file = getConfigFile();
            if (!file.exists()) {
                save();
                return;
            }

            try (FileReader reader = new FileReader(file)) {
                PhelperConfig loaded = GSON.fromJson(reader, PhelperConfig.class);
                if (loaded != null) {
                    this.enableFeature = loaded.enableFeature;
                    this.playerName = loaded.playerName;
                    this.scale = loaded.scale;
                    this.showNotifications = loaded.showNotifications;
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to load config: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            File file = getConfigFile();
            file.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(file)) {
                GSON.toJson(this, writer);
            }
        } catch (Exception e) {
            System.err.println("Failed to save config: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Getters
    public boolean isFeatureEnabled() {
        return enableFeature;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScale() {
        return scale;
    }

    public boolean shouldShowNotifications() {
        return showNotifications;
    }
}