package org.abos.fabricmc.magic.utils;

import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public enum MagicType {

    AIR(false, "air_missile.png", SoundEvents.ENTITY_PHANTOM_FLAP),
    EARTH(true, "earth_missile.png", SoundEvents.BLOCK_ROOTED_DIRT_PLACE),
    FIRE(false, "fire_missile.png", SoundEvents.ENTITY_BLAZE_SHOOT),
    LIFE(false, null, SoundEvents.BLOCK_AMETHYST_BLOCK_STEP),
    LIGHT(false, null, SoundEvents.BLOCK_AMETHYST_BLOCK_STEP),
    LIGHTNING(false, "lightning_missile.png", SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER),
    WATER(true, "water_missile.png", SoundEvents.ITEM_BUCKET_EMPTY);

    private final boolean affectedByGravity;
    private final String texture;
    private final SoundEvent sound;

    MagicType(boolean affectedByGravity, String texture, SoundEvent sound) {
        this.affectedByGravity = affectedByGravity;
        this.texture = texture;
        this.sound = sound;
    }

    public boolean isAffectedByGravity() {
        return affectedByGravity;
    }

    public String getTexture() {
        return texture;
    }

    public SoundEvent getSound() {
        return sound;
    }
}
