package me.jmulins.munchymap11r.config;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;
import me.jmulins.munchymap11r.gui.components.WaypointCategoryComponent;
import me.jmulins.munchymap11r.gui.components.WaypointComponent;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class PhelperConfig extends Vigilant {
    public static final PhelperConfig INSTANCE = new PhelperConfig();

    @Property(
            type = PropertyType.SWITCH,
            name = "Pickaxe breaking warning",
            description = "Toggle pickaxe breaking warning on or off",
            category = "Alerts",
            subcategory = "Pickaxe durability warning"
    )
    public boolean enablePickaxeBreakingWarning = false;




    @Property(
            type = PropertyType.TEXT,
            name = "Durability Threshold",
            description = "Durability threshold for pickaxe breaking warning",
            category = "Alerts",
            subcategory = "Pickaxe durability warning"
    )
    public String durabilityThresholdText = "69";
    private int durabilityThresholdInt = 69;


    @Property(
            type = PropertyType.TEXT,
            name = "Pickaxe alert text",
            description = "Title text for pickaxe breaking warning",
            category = "Alerts",
            subcategory = "Pickaxe durability warning"
    )
    public String pickaxeAlertText = "Pickaxe alert";


    @Property(
            type = PropertyType.TEXT,
            name = "Pickaxe alert sound",
            description = "Sound for the pickaxe breaking alert",
            category = "Alerts",
            subcategory = "Pickaxe durability warning"
    )
    public String pickaxeAlertSoundText = "entity.ghast.hurt";
    public SoundEvent pickaxeAlertSoundSoundEvent = SoundEvents.ENTITY_GHAST_HURT;


    @Property(
            type = PropertyType.PERCENT_SLIDER,
            name = "Pickaxe alert volume",
            description = "Sound volume for the pickaxe breaking alert",
            category = "Alerts",
            subcategory = "Pickaxe durability warning"
    )
    public float pickaxeAlertVolume = 1f;


    private PhelperConfig() {
        super(new File("./config/phelper.toml"));
        initialize();
        checkAllCheckableValues();

    }

    /**
     * Get the selected sound event based on the selector index
     */
    public SoundEvent getPickaxeAlertSelectedSound() {
        Identifier soundId = Identifier.of(pickaxeAlertSoundText);
        SoundEvent sound = Registries.SOUND_EVENT.get(soundId);
        if (sound == null) {
            pickaxeAlertSoundText = pickaxeAlertSoundSoundEvent.id().getPath();
            return pickaxeAlertSoundSoundEvent;
        }
        pickaxeAlertSoundSoundEvent = sound;
        return sound;

    }


    /**
     * Get the validated durability threshold as an integer.
     * If the text value is invalid, returns a clamped value and updates the text field.
     */
    public int getPickaxeAlertDurabilityThreshold() {
        try {
            int value = Integer.parseInt(durabilityThresholdText.trim());
            // Clamp to valid range
            if (value < 1) {
                durabilityThresholdText = "1";
                durabilityThresholdInt = 1;
                return durabilityThresholdInt;
            }
            if (value > 2031) {
                durabilityThresholdText = "2031";
                durabilityThresholdInt = 2031;
                return durabilityThresholdInt;
            }
            durabilityThresholdInt = value;
            return value;
        } catch (NumberFormatException e) {
            // Invalid input, default to 69
            durabilityThresholdText = String.valueOf(durabilityThresholdInt);
            return durabilityThresholdInt;
        }
    }

    public void checkAllCheckableValues(){
        getPickaxeAlertDurabilityThreshold();
        getPickaxeAlertSelectedSound();
    }


    // In PhelperConfig.java
    public void openGui() {
        checkAllCheckableValues();
        net.minecraft.client.MinecraftClient.getInstance().send(() -> {
            try {
                net.minecraft.client.MinecraftClient.getInstance().setScreen(gui());
            } catch (Exception e) {
                System.err.println("Failed to open Vigilance GUI:");
                e.printStackTrace();

                // Send error to chat
                net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();
                if (client.player != null) {
                    client.player.sendMessage(
                            net.minecraft.text.Text.literal("§cError opening config: " + e.getMessage()),
                            false
                    );
                }
            }
        });
//        net.minecraft.client.MinecraftClient.getInstance().send(() -> {
//            net.minecraft.client.MinecraftClient.getInstance().setScreen(gui());
//        });
    }




}