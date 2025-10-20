package me.jmulins.munchmap11r.client;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.io.File;

public class PhelperConfig extends Vigilant {
    public static final PhelperConfig INSTANCE = new PhelperConfig();

    @Property(
            type = PropertyType.SWITCH,
            name = "Enable Feature",
            description = "Toggle the main feature on or off",
            category = "General"
    )
    public boolean enableFeature = true;

    @Property(
            type = PropertyType.TEXT,
            name = "Player Name",
            description = "Enter the player name",
            category = "General"
    )
    public String playerName = "";

    @Property(
            type = PropertyType.SLIDER,
            name = "Scale",
            description = "Adjust the scale value",
            category = "General",
            min = 0,
            max = 100
    )
    public int scale = 50;

    @Property(
            type = PropertyType.SWITCH,
            name = "Show Notifications",
            description = "Toggle notifications on or off",
            category = "General"
    )
    public boolean showNotifications = false;

    @Property(
            type = PropertyType.NUMBER,
            name = "Durability Threshold",
            description = "Durability threshold for tool warnings",
            category = "Alerts",
            min = 1,
            max = 500
    )
    public int durabilityThreshold = 69;

    private PhelperConfig() {
        super(new File("./config/phelper.toml"));
        initialize();
    }

    // Getters (optional, you can access fields directly)
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

    public int getDurabilityThreshold() {
        return durabilityThreshold;
    }
    // In PhelperConfig.java
    public void openGui() {
        net.minecraft.client.MinecraftClient.getInstance().send(() -> {
            net.minecraft.client.MinecraftClient.getInstance().setScreen(gui());
        });
    }

}