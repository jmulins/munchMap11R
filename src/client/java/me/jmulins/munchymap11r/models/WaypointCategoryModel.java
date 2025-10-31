package me.jmulins.munchymap11r.models;

import me.jmulins.munchymap11r.gui.components.WaypointCategoryComponent;
import me.jmulins.munchymap11r.gui.components.WaypointComponent;
import me.jmulins.munchymap11r.utils.WaypointMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WaypointCategoryModel {
    public String uuid;
    public String name;
    public List<WaypointModel> waypoints;
    public boolean collapsed = false;

    public WaypointCategoryModel() {
        this.uuid = UUID.randomUUID().toString();
        this.waypoints = new ArrayList<>();
        this.name = "New category";
    }

    public WaypointCategoryModel(UUID uuid, String name,boolean collapsed, List<WaypointModel> waypoints) {
        this.uuid = uuid.toString();
        this.name = name;
        this.collapsed = collapsed;
        this.waypoints = waypoints;
    }

    public WaypointCategoryModel(String name) {
        this.uuid = UUID.randomUUID().toString();
        this.waypoints = new ArrayList<>();
        this.name = name;
    }

    // Component constructor - uses mapper internally
    public WaypointCategoryModel(WaypointCategoryComponent comp) {
        List<WaypointModel> waypointModels = new ArrayList<>();
        // convert all waypoints
        for (WaypointComponent waypointComp : comp.getWaypoints()) {
            waypointModels.add(new WaypointModel(waypointComp));
//            System.out.println(waypointComp.getWaypointName());
        }

        WaypointCategoryModel mapped = WaypointMapper.toCategoryModel(
                comp.getUuid(),
                comp.getCategoryName(),
                comp.isCollapsed(),
                waypointModels
        );

        this.uuid = mapped.uuid;
        this.name = mapped.name;
        this.collapsed = mapped.collapsed;
        this.waypoints = mapped.waypoints;
    }
}