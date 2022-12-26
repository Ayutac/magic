package org.abos.fabricmc.magic.utils;

import org.abos.fabricmc.magic.Magic;
import org.abos.fabricmc.ayusimpleconfig.IntConfigProperty;

public enum ProjectileSize {

    SMALL(0.8f, 0.8f, 6f, 0.05f, Magic.CONFIG.getSmallMissileCost()),
    MEDIUM(1f, 1f, 4f, 0.2f, Magic.CONFIG.getMediumMissileCost()),
    BIG(1.2f, 1.2f, 2f, 0.5f, Magic.CONFIG.getBigMissileCost());

    private final float width;
    private final float height;
    private final float speed;
    private final float divergence;
    private final IntConfigProperty manaCost;

    ProjectileSize(float width, float height, float speed, float divergence, IntConfigProperty manaCost) {
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

    public IntConfigProperty getManaCost() {
        return manaCost;
    }
}
