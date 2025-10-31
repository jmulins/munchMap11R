package me.jmulins.munchymap11r.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.jmulins.munchymap11r.models.WaypointCategoryModel;
import me.jmulins.munchymap11r.models.WaypointModel;
import net.fabricmc.loader.api.FabricLoader;

import java.awt.Color;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages waypoint data storage and persistence
 */
public class PhelperWaypointConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File WAYPOINTS_FILE = new File(FabricLoader.getInstance().getConfigDir() + "/phelper_waypoints.json");
    public static final PhelperWaypointConfig INSTANCE = new PhelperWaypointConfig();

    public List<WaypointCategoryModel> waypointCategories = new ArrayList<>();

    private PhelperWaypointConfig() {
        load();
    }

    /**
     * Load waypoints from JSON file
     */
    public void load() {
        if (!WAYPOINTS_FILE.exists()) {
            // Create default categories if file doesn't exist
            save();
            return;
        }

        try (FileReader reader = new FileReader(WAYPOINTS_FILE)) {
            Type listType = new TypeToken<List<WaypointCategoryModel>>(){}.getType();
            List<WaypointCategoryModel> loaded = GSON.fromJson(reader, listType);
            if (loaded != null) {
                waypointCategories = loaded;
            }
        } catch (Exception e) {
            System.err.println("Failed to load waypoints: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Save waypoints to JSON file
     */
    public void save() {
        try {
            WAYPOINTS_FILE.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(WAYPOINTS_FILE)) {
                GSON.toJson(waypointCategories, writer);
            }
        } catch (Exception e) {
            System.err.println("Failed to save waypoints: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Add a new waypoint category
     */
    public WaypointCategoryModel addCategory(String name) {
        WaypointCategoryModel category = new WaypointCategoryModel(name);
        waypointCategories.add(category);
        save();
        return category;
    }

    /**
     * Remove a category by UUID
     */
    public void removeCategory(String uuid) {
        waypointCategories.removeIf(cat -> cat.uuid.equals(uuid));
        save();
    }

    /**
     * Find category by UUID
     */
    public WaypointCategoryModel findCategory(String uuid) {
        for (WaypointCategoryModel category : waypointCategories) {
            if (category.uuid.equals(uuid)) {
                return category;
            }
        }
        return null;
    }

    /**
     * Update category name
     */
    public void updateCategoryName(String uuid, String newName) {
        WaypointCategoryModel category = findCategory(uuid);
        if (category != null) {
            category.name = newName;
            save();
        }
    }



    /**
     * Add waypoint to a category
     */
    public WaypointModel addWaypointToCategory(String categoryUuid, String name, int x, int y, int z) {
        WaypointCategoryModel category = findCategory(categoryUuid);
        if (category != null) {
            WaypointModel waypoint = new WaypointModel(name, x, y, z);
            category.waypoints.add(waypoint);
            save();
            return waypoint;
        }
        return null;
    }

    /**
     * Remove waypoint from category
     */
    public void removeWaypoint(String categoryUuid, String waypointUuid) {
        WaypointCategoryModel category = findCategory(categoryUuid);
        if (category != null) {
            category.waypoints.removeIf(wp -> wp.uuid.equals(waypointUuid));
            save();
        }
    }

    /**
     * Find waypoint by UUID across all categories
     */
    public WaypointModel findWaypoint(String waypointUuid) {
        for (WaypointCategoryModel category : waypointCategories) {
            for (WaypointModel waypoint : category.waypoints) {
                if (waypoint.uuid.equals(waypointUuid)) {
                    return waypoint;
                }
            }
        }
        return null;
    }

    /**
     * Update waypoint data
     */
    public void updateWaypoint(String waypointUuid, String name, int x, int y, int z) {
        WaypointModel waypoint = findWaypoint(waypointUuid);
        if (waypoint != null) {
            waypoint.name = name;
            waypoint.x = x;
            waypoint.y = y;
            waypoint.z = z;
            save();
        }
    }

    /**
     * Get all categories
     */
    public List<WaypointCategoryModel> getAllCategories() {
        return new ArrayList<>(waypointCategories);
    }

    /**
     * Get total waypoint count
     */
    public int getTotalWaypointCount() {
        int count = 0;
        for (WaypointCategoryModel category : waypointCategories) {
            count += category.waypoints.size();
        }
        return count;
    }
}