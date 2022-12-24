package org.abos.fabricmc.magic.utils;

import org.abos.fabricmc.magic.config.IntConfigProperty;

import java.util.Objects;

public class ProjectileSettings {

    private ProjectileSize size;

    private IntConfigProperty damage = null;

    private int fireTicks = 0;

    private float knockUpSpeed = 0f;

    private boolean extinguishing = false;

    private int paralysisTicks = 0;

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

    public IntConfigProperty getDamage() {
        return damage;
    }

    public ProjectileSettings setDamage(IntConfigProperty damage) {
        this.damage = Objects.requireNonNull(damage);
        return this;
    }

    public int getFireTicks() {
        return fireTicks;
    }

    public ProjectileSettings setFireTicks(int fireTicks) {
        if (fireTicks < 0) {
            throw new IllegalArgumentException("Fire ticks cannot be negative!");
        }
        this.fireTicks = fireTicks;
        return this;
    }

    public float getKnockUpSpeed() {
        return knockUpSpeed;
    }

    public ProjectileSettings setKnockUpSpeed(float knockUpSpeed) {
        if (knockUpSpeed < 0f) {
            throw new IllegalArgumentException("Knock up speed cannot be negative!");
        }
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

    public ProjectileSettings setParalysisTicks(int paralysisTicks) {
        if (paralysisTicks < 0) {
            throw new IllegalArgumentException("Paralysis ticks cannot be negative!");
        }
        this.paralysisTicks = paralysisTicks;
        return this;
    }

    public int getParalysisTicks() {
        return paralysisTicks;
    }
}
