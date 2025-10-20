package me.jmulins.munchmap11r.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {
    private final Screen parent;
    private TextFieldWidget playerNameField;
    private ButtonWidget enableFeatureButton;
    private ButtonWidget showNotificationsButton;
    private int scaleValue;

    public ConfigScreen(Screen parent) {
        super(Text.literal("Phelper Config"));
        this.parent = parent;
        this.scaleValue = PhelperConfig.INSTANCE.scale;
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;
        int startY = this.height / 4;

        // Enable Feature Toggle
        this.enableFeatureButton = ButtonWidget.builder(
                Text.literal("Enable Feature: " + (PhelperConfig.INSTANCE.enableFeature ? "ON" : "OFF")),
                button -> {
                    PhelperConfig.INSTANCE.enableFeature = !PhelperConfig.INSTANCE.enableFeature;
                    button.setMessage(Text.literal("Enable Feature: " + (PhelperConfig.INSTANCE.enableFeature ? "ON" : "OFF")));
                }
        ).dimensions(centerX - 100, startY, 200, 20).build();
        this.addDrawableChild(this.enableFeatureButton);

        // Player Name Field
        this.playerNameField = new TextFieldWidget(
                this.textRenderer,
                centerX - 100,
                startY + 30,
                200,
                20,
                Text.literal("Player Name")
        );
        this.playerNameField.setMaxLength(32);
        this.playerNameField.setText(PhelperConfig.INSTANCE.playerName);
        this.playerNameField.setChangedListener(text -> {
            PhelperConfig.INSTANCE.playerName = text;
        });
        this.addDrawableChild(this.playerNameField);

        // Scale Decrease Button
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("-"),
                button -> {
                    if (this.scaleValue > 0) {
                        this.scaleValue--;
                        PhelperConfig.INSTANCE.scale = this.scaleValue;
                    }
                }
        ).dimensions(centerX - 100, startY + 60, 30, 20).build());

        // Scale Increase Button
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("+"),
                button -> {
                    if (this.scaleValue < 100) {
                        this.scaleValue++;
                        PhelperConfig.INSTANCE.scale = this.scaleValue;
                    }
                }
        ).dimensions(centerX + 70, startY + 60, 30, 20).build());

        // Show Notifications Toggle
        this.showNotificationsButton = ButtonWidget.builder(
                Text.literal("Notifications: " + (PhelperConfig.INSTANCE.showNotifications ? "ON" : "OFF")),
                button -> {
                    PhelperConfig.INSTANCE.showNotifications = !PhelperConfig.INSTANCE.showNotifications;
                    button.setMessage(Text.literal("Notifications: " + (PhelperConfig.INSTANCE.showNotifications ? "ON" : "OFF")));
                }
        ).dimensions(centerX - 100, startY + 90, 200, 20).build();
        this.addDrawableChild(this.showNotificationsButton);

        // Save Button
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Save & Close"),
                button -> {
                    PhelperConfig.INSTANCE.save();
                    this.close();
                }
        ).dimensions(centerX - 100, startY + 120, 200, 20).build());

        // Cancel Button
        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Cancel"),
                button -> {
                    PhelperConfig.INSTANCE.load();
                    this.close();
                }
        ).dimensions(centerX - 100, startY + 145, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        // Draw title
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                this.title,
                this.width / 2,
                20,
                0xFFFFFF
        );

        // Draw labels
        int centerX = this.width / 2;
        int startY = this.height / 4;

        context.drawTextWithShadow(
                this.textRenderer,
                "Player Name:",
                centerX - 100,
                startY + 20,
                0xFFFFFF
        );

        context.drawTextWithShadow(
                this.textRenderer,
                "Scale: " + this.scaleValue,
                centerX - 40,
                startY + 65,
                0xFFFFFF
        );
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}