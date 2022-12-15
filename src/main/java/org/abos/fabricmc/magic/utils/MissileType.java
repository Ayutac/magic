package org.abos.fabricmc.magic.utils;

public enum MissileType {

    AIR(false, "air_missile.png"),
    EARTH(true, "earth_missile.png"),
    FIRE(false, "fire_missile.png"),
    WATER(true, "water_missile.png");

    private final boolean affectedByGravity;
    private final String texture;

    MissileType(boolean affectedByGravity, String texture) {
        this.affectedByGravity = affectedByGravity;
        this.texture = texture;
    }

    public boolean isAffectedByGravity() {
        return affectedByGravity;
    }

    public String getTexture() {
        return texture;
    }
}
