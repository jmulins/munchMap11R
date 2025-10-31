package me.jmulins.munchymap11r.gui.components;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UIContainer;
import gg.essential.elementa.components.input.UITextInput;
import gg.essential.elementa.constraints.*;
import gg.essential.elementa.dsl.ConstraintsKt;
import gg.essential.elementa.effects.OutlineEffect;

import java.awt.Color;
import java.util.UUID;

public class WaypointComponent extends UIContainer {

    private UITextInput nameText;
    private UITextInput xText, yText, zText;
    private UIContainer coordinatesContainer;
    private ButtonComponent removeButton;
    private UUID uuid = UUID.randomUUID();
    private Runnable onRemoveCallback;

    // Store current values that get updated on change
    private String currentName;
    private int currentX, currentY, currentZ;

    public UUID getUuid() {
        return uuid;
    }

    public WaypointComponent(String name, int x, int y, int z) {
        this(name, x, y, z, Color.GREEN);
    }

    public WaypointComponent(UUID uuid, String name, int x, int y, int z) {
        this(name, x, y, z, Color.GREEN);
        this.uuid = uuid;
    }


    public WaypointComponent(String name, int x, int y, int z, Color outlineColor) {
        super();


        this.currentName = name;
        this.currentX = x;
        this.currentY = y;
        this.currentZ = z;

        // Configure the container
        this.setX(new CenterConstraint());
        this.setY(ConstraintsKt.plus(new SiblingConstraint(), new PixelConstraint(5f)));
        this.setWidth(new RelativeConstraint(0.9f));
        this.setHeight(new PixelConstraint(25f));
        this.setColor(new Color(0, 0, 0, 100));
        this.enableEffect(new OutlineEffect(outlineColor, 2f));

        // Add waypoint name
        nameText = (UITextInput) new UITextInput(name)
                .setX(new PixelConstraint(5f))
                .setY(new CenterConstraint())
                .setWidth(new RelativeConstraint(0.25f))
                .setChildOf(this);


        nameText.onUpdate(newText -> {
            currentName = newText;
            return null;
        });

        coordinatesContainer = (UIContainer) new UIContainer()
                .setX(new RelativeConstraint(0.3f))
                .setY(new CenterConstraint())
                .setHeight(new PixelConstraint(10f))
                .setWidth(new RelativeConstraint(0.4f))
                .setChildOf(this);

        xText = (UITextInput) new UITextInput(String.valueOf(x))
                .setX(new RelativeConstraint(0f))
                .setY(new PixelConstraint(0f, true))
                .setWidth(new RelativeConstraint(0.3f))
                .setChildOf(coordinatesContainer);


        // Update X when text changes
        xText.onUpdate(newText -> {
            try {
                currentX = Integer.parseInt(newText.trim());
            } catch (NumberFormatException e) {
                // Keep old value if invalid
            }
            return null;
        });

        yText = (UITextInput) new UITextInput(String.valueOf(y))
                .setX(new RelativeConstraint(0.35f))
                .setY(new PixelConstraint(0f, true))
                .setWidth(new RelativeConstraint(0.3f))
                .setChildOf(coordinatesContainer);

        // Update Y when text changes
        yText.onUpdate(newText -> {
            try {
                currentY = Integer.parseInt(newText.trim());
            } catch (NumberFormatException e) {
                // Keep old value if invalid
            }
            return null;
        });

        zText = (UITextInput) new UITextInput(String.valueOf(z))
                .setX(new RelativeConstraint(0.7f))
                .setY(new PixelConstraint(0f, true))
                .setWidth(new RelativeConstraint(0.3f))
                .setChildOf(coordinatesContainer);

        // Update Z when text changes
        zText.onUpdate(newText -> {
            try {
                currentZ = Integer.parseInt(newText.trim());
            } catch (NumberFormatException e) {
                // Keep old value if invalid
            }
            return null;
        });

        removeButton = (ButtonComponent) new ButtonComponent("Remove")
                .setX(new SiblingConstraint(10f))
                .setY(new CenterConstraint())
                .setHeight(new PixelConstraint(10f))
                .setChildOf(this);


        removeButton.onMouseClick((comp, event) -> {
            if (onRemoveCallback != null) {
                onRemoveCallback.run();
            }
            return null;
        });

        UITextInput[] coords = {xText, yText, zText};
        for (UITextInput singleCoord : coords) {
            singleCoord.onMouseClick((comp, event) -> {
                comp.grabWindowFocus();
                return null;
            });
        }

        nameText.onMouseClick((comp, event) -> {
            comp.grabWindowFocus();
            return null;
        });
    }

    public void setOnRemove(Runnable callback) {
        this.onRemoveCallback = callback;
    }


    public String getWaypointName() {
        return currentName;
    }

    public int getWaypointX() {
        return currentX;
    }

    public int getWaypointY() {
        return currentY;
    }

    public int getWaypointZ() {
        return currentZ;
    }


}