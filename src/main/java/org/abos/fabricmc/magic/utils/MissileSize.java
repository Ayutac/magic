package org.abos.fabricmc.magic.utils;

public enum MissileSize {

    SMALL(0.8f, 0.8f, 6f, 0.05f),
    MEDIUM(1f, 1f, 4f, 0.2f),
    BIG(1.2f, 1.2f, 2f, 0.5f);

    private final float width;
    private final float height;
    private final float speed;
    private final float divergence;

    MissileSize(float width, float height, float speed, float divergence) {
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.divergence = divergence;
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
}
