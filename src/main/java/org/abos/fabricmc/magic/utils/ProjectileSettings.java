package org.abos.fabricmc.magic.utils;

public class ProjectileSettings {

    private ProjectileSize size;

    private double damage = 0;

    private int fireTicks = 0;

    private float knockUpSpeed = 0f;

    private boolean extinguishing = false;

    public ProjectileSettings(ProjectileSize size) {
        this.size = size;
    }

    public static ProjectileSettings small() {
        return new ProjectileSettings(ProjectileSize.SMALL);
    }

    public static ProjectileSettings medium() {
        return new ProjectileSettings(ProjectileSize.MEDIUM);
    }

    public static ProjectileSettings big() {
        return new ProjectileSettings(ProjectileSize.BIG);
    }

    public ProjectileSize getSize() {
        return size;
    }

    public ProjectileSettings setSize(ProjectileSize size) {
        this.size = size;
        return this;
    }

    public double getDamage() {
        return damage;
    }

    public ProjectileSettings setDamage(double damage) {
        this.damage = damage;
        return this;
    }

    public int getFireTicks() {
        return fireTicks;
    }

    public ProjectileSettings setFireTicks(int fireTicks) {
        this.fireTicks = fireTicks;
        return this;
    }

    public float getKnockUpSpeed() {
        return knockUpSpeed;
    }

    public ProjectileSettings setKnockUpSpeed(float knockUpSpeed) {
        this.knockUpSpeed = knockUpSpeed;
        return this;
    }

    public boolean isExtinguishing() {
        return extinguishing;
    }

    public ProjectileSettings setExtinguishing(boolean extinguishing) {
        this.extinguishing = extinguishing;
        return this;
    }
}
