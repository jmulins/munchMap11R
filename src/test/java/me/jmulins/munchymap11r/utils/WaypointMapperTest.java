package me.jmulins.munchymap11r.utils;

import me.jmulins.munchymap11r.models.WaypointCategoryModel;
import me.jmulins.munchymap11r.models.WaypointModel;
import me.jmulins.munchymap11r.utils.WaypointMapper.WaypointData;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WaypointMapperTest {

    @Test
    void testMapWaypointToModel() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        String name = "Diamond Mine";
        int x = 100, y = 64, z = -200;

        // Act
        WaypointModel model = WaypointMapper.toModel(uuid, name, x, y, z);

        // Assert
        assertEquals(uuid.toString(), model.uuid);
        assertEquals(name, model.name);
        assertEquals(x, model.x);
        assertEquals(y, model.y);
        assertEquals(z, model.z);
    }

    @Test
    void testMapCategoryToModel() {
        // Arrange
        UUID categoryUuid = UUID.randomUUID();
        String categoryName = "Mining";

        UUID wp1Uuid = UUID.randomUUID();
        UUID wp2Uuid = UUID.randomUUID();

        boolean collapsed = true;

        List<WaypointModel> waypoints = Arrays.asList(
                WaypointMapper.toModel(wp1Uuid, "Mine 1", 100, 64, -200),
                WaypointMapper.toModel(wp2Uuid, "Mine 2", 150, 70, -250)
        );

        // Act
        WaypointCategoryModel category = WaypointMapper.toCategoryModel(
                categoryUuid,
                categoryName,
                collapsed,
                waypoints
        );

        // Assert
        assertEquals(categoryUuid.toString(), category.uuid);
        assertEquals(categoryName, category.name);
        assertEquals(2, category.waypoints.size());
        assertEquals(collapsed, category.collapsed);
        assertEquals("Mine 1", category.waypoints.get(0).name);
        assertEquals("Mine 2", category.waypoints.get(1).name);
    }

    @Test
    void testMapMultipleWaypoints() {
        // Arrange
        List<WaypointData> dataList = Arrays.asList(
                new WaypointData(UUID.randomUUID(), "WP1", 0, 64, 0),
                new WaypointData(UUID.randomUUID(), "WP2", 100, 70, -100),
                new WaypointData(UUID.randomUUID(), "WP3", 200, 65, -200)
        );

        // Act
        List<WaypointModel> models = WaypointMapper.toWaypointModelList(dataList);

        // Assert
        assertEquals(3, models.size());
        assertEquals("WP1", models.get(0).name);
        assertEquals("WP2", models.get(1).name);
        assertEquals("WP3", models.get(2).name);
    }

    @Test
    void testPreservesUUIDs() {
        // Arrange
        UUID originalUuid = UUID.randomUUID();

        // Act
        WaypointModel model = WaypointMapper.toModel(originalUuid, "Test", 0, 0, 0);

        // Assert
        assertEquals(originalUuid.toString(), model.uuid);
    }

    @Test
    void testEmptyCategoryWithNoWaypoints() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        List<WaypointModel> emptyList = Arrays.asList();
        boolean collapsed = false;

        // Act
        WaypointCategoryModel category = WaypointMapper.toCategoryModel(
                uuid,
                "Empty Category",
                collapsed,
                emptyList
        );

        // Assert
        assertNotNull(category);
        assertEquals(collapsed, category.collapsed);
        assertEquals(0, category.waypoints.size());
        assertEquals("Empty Category", category.name);
    }
}