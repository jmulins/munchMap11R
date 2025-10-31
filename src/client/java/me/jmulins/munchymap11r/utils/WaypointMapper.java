package me.jmulins.munchymap11r.utils;

import me.jmulins.munchymap11r.models.WaypointModel;
import me.jmulins.munchymap11r.models.WaypointCategoryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Maps between component data and model data
 * This class contains NO UI code, making it testable
 */
public class WaypointMapper {

    /**
     * Create a WaypointModel from component data
     */
    public static WaypointModel toModel(UUID uuid, String name, int x, int y, int z) {
        return new WaypointModel(uuid.toString(), name, x, y, z);
    }

    /**
     * Create a WaypointCategoryModel from component data
     */
    public static WaypointCategoryModel toCategoryModel(
            UUID uuid,
            String name,
            boolean collapsed,
            List<WaypointModel> waypoints
    ) {
        return new WaypointCategoryModel(uuid, name, collapsed, waypoints);
    }

    /**
     * Create waypoint models from a list of waypoint data
     */
    public static List<WaypointModel> toWaypointModelList(
            List<WaypointData> waypointDataList
    ) {
        List<WaypointModel> models = new ArrayList<>();
        for (WaypointData data : waypointDataList) {
            models.add(toModel(data.uuid, data.name, data.x, data.y, data.z));
        }
        return models;
    }

    /**
     * Simple data class for passing waypoint data
     */
    public static class WaypointData {
        public final UUID uuid;
        public final String name;
        public final int x, y, z;

        public WaypointData(UUID uuid, String name, int x, int y, int z) {
            this.uuid = uuid;
            this.name = name;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}