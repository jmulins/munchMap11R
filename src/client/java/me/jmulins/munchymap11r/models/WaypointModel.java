package me.jmulins.munchymap11r.models;

import me.jmulins.munchymap11r.gui.components.WaypointComponent;
import me.jmulins.munchymap11r.utils.WaypointMapper;

import java.util.UUID;

public class WaypointModel {
    public String uuid;
    public String name;
    public int x;
    public int y;
    public int z;

    public WaypointModel() {
        this.uuid = UUID.randomUUID().toString();
    }

    public WaypointModel(String uuid, String name, int x, int y, int z) {
        this.uuid = uuid;
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public WaypointModel(String name, int x, int y, int z) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // Component constructor - uses mapper internally
    public WaypointModel(WaypointComponent comp) {
        WaypointModel mapped = WaypointMapper.toModel(
                comp.getUuid(),
                comp.getWaypointName(),
                comp.getWaypointX(),
                comp.getWaypointY(),
                comp.getWaypointZ()
        );
        this.uuid = mapped.uuid;
        this.name = mapped.name;
        this.x = mapped.x;
        this.y = mapped.y;
        this.z = mapped.z;
    }
}