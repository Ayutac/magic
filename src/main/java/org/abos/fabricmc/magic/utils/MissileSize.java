package org.abos.fabricmc.magic.utils;

import org.abos.fabricmc.magic.MagicConfig;

public enum MissileSize {

    SMALL(0.8f, 0.8f, 6f, 0.05f, MagicConfig.SMALL_MISSILE_COST),
    MEDIUM(1f, 1f, 4f, 0.2f, MagicConfig.MEDIUM_MISSILE_COST),
    BIG(1.2f, 1.2f, 2f, 0.5f, MagicConfig.BIG_MISSILE_COST);

    private final float width;
    private final float height;
    private final float speed;
    private final float divergence;

    private final int manaCost;

    MissileSize(float width, float height, float speed, float divergence, int manaCost) {
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.divergence = divergence;
        this.manaCost = manaCost;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getSpeed() {
        return speed;
    }

    public float getDivergence() {
        return divergence;
    }

    public int getManaCost() {
        return manaCost;
    }
}
