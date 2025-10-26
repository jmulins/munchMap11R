package me.jmulins.munchymap11r.gui.components;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIContainer;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.components.input.UITextInput;
import gg.essential.elementa.constraints.*;
import gg.essential.elementa.dsl.ConstraintsKt;
import gg.essential.elementa.effects.OutlineEffect;

import java.awt.Color;

/**
 * Reusable waypoint component
 */
public class WaypointComponent extends UIComponent {

    private final String name;
    private final int x, y, z;
    private UITextInput nameText;
    private UITextInput xText, yText, zText;
    private UIContainer coordinatesContainer;
    private ButtonComponent removeButton;



    public WaypointComponent(String name, int x, int y, int z) {
        this(name, x, y, z, Color.GREEN);
    }

    public WaypointComponent(String name, int x, int y, int z, Color outlineColor) {
        // UIBlock constructor without color - we'll set it separately
        super();

        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;

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
//       .setTextScale(new ConstantColorConstraint(1.2f));
//        .setColor(Color.WHITE)
        .setChildOf(this);


        coordinatesContainer = (UIContainer) new UIContainer()
                .setX(new RelativeConstraint(0.3f))
                .setY(new CenterConstraint())
                .setHeight(new PixelConstraint(10f))
                .setWidth(new RelativeConstraint(0.4f))
//                .enableEffect(new OutlineEffect(outlineColor, 2f))
                .setChildOf(this);


        xText = (UITextInput) new UITextInput(String.valueOf(x))
                .setX(new RelativeConstraint(0f))
                .setY(new PixelConstraint(0f, true))
                .setWidth(new RelativeConstraint(0.3f))
//                .setColor(new ConstantColorConstraint(Color.WHITE))
                .setChildOf(coordinatesContainer);

        yText = (UITextInput) new UITextInput(String.valueOf(y))
                .setX(new RelativeConstraint(0.35f))
                .setY(new PixelConstraint(0f, true))
                .setWidth(new RelativeConstraint(0.3f))
//                .setColor(new ConstantColorConstraint(Color.WHITE))
                .setChildOf(coordinatesContainer);

        zText = (UITextInput) new UITextInput(String.valueOf(z))
                .setX(new RelativeConstraint(0.7f))
                .setY(new PixelConstraint(0f, true))
                .setWidth(new RelativeConstraint(0.3f))
//                .setColor(new ConstantColorConstraint(Color.WHITE))
                .setChildOf(coordinatesContainer);


        removeButton = (ButtonComponent) new ButtonComponent("Remove")
                .setX(new SiblingConstraint(10f))
                .setY(new CenterConstraint())
                .setHeight(new PixelConstraint(10f))
                .setChildOf(this);




        // Add coordinates
//        coordsText = new UIText(String.format("X: %d, Y: %d, Z: %d", x, y, z));
//        coordsText.setX(new PixelConstraint(5f));
//        coordsText.setY(new SiblingConstraint(3f));
////        coordsText.setTextScale(new ConstantColorConstraint(0.8f));
//        coordsText.setColor(new ConstantColorConstraint(Color.GRAY));
//        coordsText.setChildOf(this);

        // Add hover effect
//        this.onMouseEnter((comp) -> {
//            this.setColor(new ConstantColorConstraint(new Color(50, 50, 50, 150)));
//            return null;
//        });
//
//        this.onMouseLeave((comp) -> {
//            this.setColor(new ConstantColorConstraint(new Color(0, 0, 0, 100)));
//            return null;
//        });
//
//        // Add click handler
//        this.onMouseClick((comp, event) -> {
//            System.out.println("Clicked waypoint: " + name);
//            // Add your click logic here (teleport, copy coords, etc.)
//            return null;
//        });



        UITextInput[] coords = {xText, yText, zText};
        for (UITextInput singleCoord : coords){
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





    public String getWaypointName() {
        return name;
    }

    public int getWaypointX() {
        return x;
    }

    public int getWaypointY() {
        return y;
    }

    public int getWaypointZ() {
        return z;
    }

//    public void updateName(String newName) {
//        nameText.setText(newName);
//    }

//    public void updateCoordinates(int newX, int newY, int newZ) {
//        coordsText.setText(String.format("X: %d, Y: %d, Z: %d", newX, newY, newZ));
//    }
}