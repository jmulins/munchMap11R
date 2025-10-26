package me.jmulins.munchymap11r.gui;

import gg.essential.elementa.ElementaVersion;
import gg.essential.elementa.UIComponent;
import gg.essential.elementa.WindowScreen;
import gg.essential.elementa.components.ScrollComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.input.UITextInput;
import gg.essential.elementa.constraints.*;
import gg.essential.elementa.dsl.ConstraintsKt;
import gg.essential.elementa.effects.OutlineEffect;
import me.jmulins.munchymap11r.gui.components.WaypointCategoryComponent;
import me.jmulins.munchymap11r.gui.components.WaypointComponent;

import java.awt.*;

public class JavaTestGui extends WindowScreen {

    static final Color BORDERS_COLOR = Color.ORANGE;

//    UIComponent box = new UIBlock()
//            .setX(new CenterConstraint())
//            .setY(new PixelConstraint(100f))
//            .setWidth(new PixelConstraint(300f))
//            .setHeight(new PixelConstraint(36f))
//            .setChildOf(getWindow())
//            .enableEffect(new ScissorEffect());

//    UIComponent nameText = new UITextInput()
//            .setX(new PixelConstraint(3f))
//            .setY(new PixelConstraint(3f))
//            .setWidth(new PixelConstraint(300f))
//            .setHeight(new PixelConstraint(36f))
//            .setChildOf(getWindow());

//    UIComponent textContainer = new UIBlock()
//            .setX(new CenterConstraint())
//            .setY(new PixelConstraint(300f))
//            .setWidth(new PixelConstraint(300f))
//            .setHeight(new PixelConstraint(36f))
//            .setColor(Color.BLACK)
//            .setChildOf(getWindow());



//    UIComponent inputNameBox = new UIBlock()
//            .setX(new PixelConstraint(20f))
//            .setY(new PixelConstraint(20f))
//            .setWidth(new RelativeConstraint(0.15f))
//            .setHeight(new RelativeConstraint(0.05f))
//            .setColor(new Color(0,0,0, 100))
//            .setChildOf(getWindow());
//
//    UIComponent inputNameText = new UITextInput()
//            .setX(new CenterConstraint())
//            .setY(new CenterConstraint())
//            .setWidth(new RelativeConstraint(1f))
//            .setHeight(new RelativeConstraint(1f))
//            .setChildOf(inputNameBox);


    UIComponent waypointsListBox = new UIBlock()
            .setX(new CenterConstraint())
            .setY(new CenterConstraint())
            .setWidth(new RelativeConstraint(0.75f))
            .setHeight(new RelativeConstraint(0.75f))
            .setColor(new Color(0,0,0, 100))
            .setChildOf(getWindow());

    UIComponent waypointsList = new ScrollComponent("", 4f)
            .setX(new CenterConstraint())
            .setY(new CenterConstraint())
            .setWidth(new RelativeConstraint(1f))
            .setHeight(new RelativeConstraint(1f))
            .setChildOf(waypointsListBox);



//    UIComponent exampleWaypointBox = new UIBlock()
//            .setX(new CenterConstraint())
//            .setY(new SiblingConstraint())
//            .setWidth(new RelativeConstraint(0.9f))
//            .setHeight(new RelativeConstraint(0.1f))
//            .setColor(new Color(255,0 ,0, 100))
//            .setChildOf(waypointsList);

//    UIComponent exampleWaypointBox = new UIBlock()
//            .setX(new CenterConstraint())
//            .setY(new SiblingConstraint())
//            .setWidth(new PixelConstraint(0f))
//            .setHeight(new PixelConstraint(0f))
//            .setColor(new Color(255,0 ,0, 100))
//            .setChildOf(waypointsList);

//    UIComponent waypointCategory = new UIBlock()
//            .setX(ConstraintsKt.minus(new CenterConstraint(), new PixelConstraint(2f)))
//            .setY(new SiblingConstraint(5f))
//            .setWidth(new RelativeConstraint(0.9f))
//            .setHeight(new ChildBasedSizeConstraint())
//            .setColor(new Color(0,0,0, 100))
//            .setChildOf(waypointsList)
//            .enableEffect(new OutlineEffect(BORDERS_COLOR, 2f));


    WaypointCategoryComponent waypointCategory = (WaypointCategoryComponent) new WaypointCategoryComponent("test123")
            .setChildOf(waypointsList);

    WaypointComponent waypoint11 = (WaypointComponent) new WaypointComponent("test name1", 1, 2, 3, Color.GREEN);
    WaypointComponent waypoint12 = (WaypointComponent) new WaypointComponent("test name2", 1, 2, 3, Color.GREEN);

    WaypointComponent waypoint13 = (WaypointComponent) new WaypointComponent("test name2", 1, 2, 3, Color.GREEN);
//            .setChildOf(waypointCategory);


//    UIComponent waypoint1 = new UIBlock()
//            .setX(new CenterConstraint())
//            .setY(ConstraintsKt.plus(new SiblingConstraint(), new PixelConstraint(5f)))
//            .setWidth(new PixelConstraint(150f))
//            .setHeight(new PixelConstraint(32f))
//            .setColor(new Color(0,0,0, 100))
//            .setChildOf(waypointCategory)
//            .enableEffect(new OutlineEffect(Color.GREEN, 2f));
//
//
//    UIComponent waypoint2 = new UIBlock()
//            .setX(new CenterConstraint())
//            .setY(ConstraintsKt.plus(new SiblingConstraint(), new PixelConstraint(5f)))
//            .setWidth(new PixelConstraint(150f))
//            .setHeight(new PixelConstraint(32f))
//            .setColor(new Color(0,0,0, 100))
//            .setChildOf(waypointCategory)
//            .enableEffect(new OutlineEffect(Color.GREEN, 2f));
//
//
//    UIComponent waypoint3 = new UIBlock()
//            .setX(new CenterConstraint())
//            .setY(ConstraintsKt.plus(new SiblingConstraint(), new PixelConstraint(5f)))
//            .setWidth(new PixelConstraint(150f))
//            .setHeight(new PixelConstraint(32f))
//            .setColor(new Color(0,0,0, 100))
//            .setChildOf(waypointCategory)
//            .enableEffect(new OutlineEffect(Color.GREEN, 2f));
//
//    UIComponent waypoint4 = new UIBlock()
//            .setX(new CenterConstraint())
//            .setY(ConstraintsKt.plus(new SiblingConstraint(), new PixelConstraint(5f)))
//            .setWidth(new PixelConstraint(150f))
//            .setHeight(new PixelConstraint(32f))
//            .setColor(new Color(0,0,0, 100))
//            .setChildOf(waypointCategory)
//            .enableEffect(new OutlineEffect(Color.GREEN, 2f));


//    UIComponent exampleWaypointBox = new UIBlock()
//            .setX(new PixelConstraint(5f))
//            .setY(new PixelConstraint(5f))
//            .setWidth(new RelativeConstraint(0.9f))
//            .setHeight(new RelativeConstraint(0.1f))
//            .setColor(new Color(255,0,0, 100))
//            .setChildOf(waypointsList);



    public JavaTestGui() {
        super(ElementaVersion.V10);
//        box.onMouseEnterRunnable(() -> {
//            // Animate, set color, etc.
//            AnimatingConstraints anim = box.makeAnimation();
//            anim.setWidthAnimation(Animations.OUT_EXP, 2.5f, new ChildBasedSizeConstraint(2f));
//            anim.onCompleteRunnable(() -> {
//                // Trigger new animation or anything.
//            });
//            box.animateTo(anim);
//        });


//        for (int i = 0; i < 4; i++) {
//            Random r= new Random();
//            UIComponent exampleWaypointBox = new UIBlock()
//                    .setX(new CenterConstraint())
//                    .setY(new SiblingConstraint())
//                    .setWidth(new RelativeConstraint(0.9f))
//                    .setHeight(new RelativeConstraint(0.1f))
//                    .setColor(new Color(r.nextInt(0, 256),r.nextInt(0, 256),r.nextInt(0, 256), 100))
//                    .setChildOf(waypointsList);
//        }
        waypointCategory.addWaypoint(waypoint11);
        waypointCategory.addWaypoint(waypoint12);
//        waypointCategory.addWaypoint(waypoint13);
//        float nChildrenInCategory = waypointCategory.getChildren().size();

//        waypointCategory.setHeight(new PixelConstraint(((nChildrenInCategory*37f)+5f)));
//        inputNameBox.onMouseClick((uiComponent, uiClickEvent) -> {
//            inputNameText.grabWindowFocus();
//            return null;
//        });
    }
}