package me.jmulins.munchymap11r.gui.components;

import gg.essential.elementa.UIComponent;
import gg.essential.elementa.components.UIBlock;
import gg.essential.elementa.components.UIContainer;
import gg.essential.elementa.components.UIText;
import gg.essential.elementa.components.input.UITextInput;
import gg.essential.elementa.constraints.*;
import gg.essential.elementa.effects.OutlineEffect;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Reusable waypoint category component that can hold multiple waypoints
 */
public class WaypointCategoryComponent extends UIComponent {

    private final String categoryName;
    private final List<WaypointComponent> waypoints = new ArrayList<>();
    private UITextInput titleText;
    private boolean isExpanded = true;
    private UIContainer headerContainer, waypointsContainer;

    public WaypointCategoryComponent(String categoryName) {
        this(categoryName, Color.ORANGE);
    }

    public WaypointCategoryComponent(String categoryName, Color borderColor) {
        super();

        this.categoryName = categoryName;

        // Configure the container
        this.setX(new CenterConstraint());
        this.setY(new SiblingConstraint(5f));
        this.setWidth(new RelativeConstraint(0.9f));
        this.setHeight(new ChildBasedSizeConstraint());
        this.setColor(new ConstantColorConstraint(new Color(0, 0, 0, 100)));
        this.enableEffect(new OutlineEffect(borderColor, 2f));

        headerContainer = (UIContainer) new UIContainer()
                .setX(new PixelConstraint(0f))
                .setY(new PixelConstraint(0f))
                .setWidth(new RelativeConstraint(1f))
                .setHeight(new PixelConstraint(20f))
                .enableEffect(new OutlineEffect(borderColor, 2f))
                .setChildOf(this);

        titleText = (UITextInput) new UITextInput(categoryName)
                .setX(new PixelConstraint(5f))
                .setY(new CenterConstraint())
                .setWidth(new PixelConstraint(20f))
                .setChildOf(headerContainer);


        waypointsContainer = (UIContainer) new UIContainer()
                .setX(new PixelConstraint(0f))
                .setY(new PixelConstraint(24f))
                .setWidth(new RelativeConstraint(1f))
                .setHeight(new PixelConstraint(33f))
                .setChildOf(this);


//
//    /**
//     * Add a waypoint to this category
//     */
//    public WaypointCategoryComponent addWaypoint(String name, int x, int y, int z) {
//        WaypointComponent waypoint = new WaypointComponent(name, x, y, z);
//        waypoint.setChildOf(this);
//        waypoints.add(waypoint);
//        return this;
//    }
//
    }
    /**
     * Add a waypoint with custom color
     */
    public WaypointCategoryComponent addWaypoint(String waypointName, int x, int y, int z, Color color) {
        WaypointComponent waypoint = new WaypointComponent(waypointName, x, y, z, color);
        waypoint.setChildOf(waypointsContainer);
        waypoints.add(waypoint);
        if (waypoints.size() == 1) {
            waypointsContainer.setHeight(new PixelConstraint(waypoint.getHeight()+16f));
        } else {
            waypointsContainer.setHeight(new PixelConstraint((waypoints.size()*30f)+11f));
        }

        return this;
    }


    public WaypointCategoryComponent addWaypoint(WaypointComponent waypoint) {
        addWaypoint(waypoint.getWaypointName(), waypoint.getWaypointX(), waypoint.getWaypointY(), waypoint.getWaypointZ(), Color.ORANGE);
        return this;
    }


//
//    /**
//     * Remove a waypoint by name
//     */
//    public void removeWaypoint(String name) {
//        waypoints.removeIf(waypoint -> {
//            if (waypoint.getWaypointName().equals(name)) {
//                this.removeChild(waypoint);
//                return true;
//            }
//            return false;
//        });
//    }
//
//    /**
//     * Toggle expand/collapse
//     */
//    public void toggleExpanded() {
//        isExpanded = !isExpanded;
//        titleText.setText((isExpanded ? "▼ " : "▶ ") + categoryName);
//
//        // Show or hide waypoints
//        for (WaypointComponent waypoint : waypoints) {
//            if (isExpanded) {
//                waypoint.setHeight(new PixelConstraint(50f));
//                waypoint.setY(new SiblingConstraint(5f));
//            } else {
//                waypoint.setHeight(new PixelConstraint(0f));
//                waypoint.setY(new PixelConstraint(0f));
//            }
//        }
//    }

//    /**
//     * Get all waypoints in this category
//     */
//    public List<WaypointComponent> getWaypoints() {
//        return new ArrayList<>(waypoints);
//    }
//
//    /**
//     * Clear all waypoints
//     */
//    public void clearWaypoints() {
//        for (WaypointComponent waypoint : waypoints) {
//            this.removeChild(waypoint);
//        }
//        waypoints.clear();
//    }
    }