package org.abos.fabricmc.magic.config;

import net.minecraft.world.GameRules;

public final class Config {

    private final PercentageConfigProperty beginnerManaFactor = createPercentageProperty("beginner_mana_factor", 200);
    private final PercentageConfigProperty noviceManaFactor = createPercentageProperty("novice_mana_factor", 150);
    private final PercentageConfigProperty expertManaFactor = createPercentageProperty("expert_mana_factor", 100);
    private final PercentageConfigProperty masterManaFactor = createPercentageProperty("master_mana_factor", 50);

    // in ticks
    private final IntConfigProperty wandCoolDown = createTimeProperty("wand_cool_down", 20);


    private final IntConfigProperty smallMissileCost = createCostProperty("small_missile_cost", 2);
    private final IntConfigProperty mediumMissileCost = createCostProperty("medium_missile_cost", 5);
    private final IntConfigProperty bigMissileCost = createCostProperty("big_missile_cost", 12);
    private final IntConfigProperty smallAirMissileDamage = createDamageProperty("small_air_missile_damage", 0);
    private final IntConfigProperty mediumAirMissileDamage = createDamageProperty("medium_air_missile_damage", 1);
    private final IntConfigProperty bigAirMissileDamage = createDamageProperty("big_air_missile_damage", 3);
    private final IntConfigProperty smallEarthMissileDamage = createDamageProperty("small_earth_missile_damage", 1);
    private final IntConfigProperty mediumEarthMissileDamage = createDamageProperty("medium_earth_missile_damage", 6);
    private final IntConfigProperty bigEarthMissileDamage = createDamageProperty("big_earth_missile_damage", 15);
    private final IntConfigProperty smallFireMissileDamage = createDamageProperty("small_fire_missile_damage", 0);
    private final IntConfigProperty mediumFireMissileDamage = createDamageProperty("medium_fire_missile_damage", 4);
    private final IntConfigProperty bigFireMissileDamage = createDamageProperty("big_fire_missile_damage", 10);
    private final IntConfigProperty smallLightningMissileDamage = createDamageProperty("small_lightning_missile_damage", 0);
    private final IntConfigProperty mediumLightningMissileDamage = createDamageProperty("medium_lightning_missile_damage", 3);
    private final IntConfigProperty bigLightningMissileDamage = createDamageProperty("big_lightning_missile_damage", 8);
    private final IntConfigProperty smallWaterMissileDamage = createDamageProperty("small_water_missile_damage", 0);
    private final IntConfigProperty mediumWaterMissileDamage = createDamageProperty("medium_water_missile_damage", 4);
    private final IntConfigProperty bigWaterMissileDamage = createDamageProperty("big_water_missile_damage", 10);
    private final IntConfigProperty accelerateGrowthCost = createCostProperty("accelerate_growth_cost", 10);
    private final IntConfigProperty instantHealCost = createCostProperty("instant_heal_cost", 25);
    private final IntConfigProperty shieldCost = createCostProperty("shield_cost", 25);
    // in ticks
    private final IntConfigProperty shieldDuration = createTimeProperty("shield_duration", 60*20);
    private final IntConfigProperty nightVisionCost = createCostProperty("night_vision_cost", 8);
    // in ticks
    private final IntConfigProperty nightVisionDuration = createTimeProperty("night_vision_duration", 60*20);
    private final IntConfigProperty gillsCost = createCostProperty("gills_cost", 10);
    // in ticks
    private final IntConfigProperty gillsDuration = createTimeProperty("gills_duration", 30*20);
    private final IntConfigProperty oceansFriendCost = createCostProperty("oceans_friend_cost", 30);
    // in ticks
    private final IntConfigProperty oceansFriendDuration = createTimeProperty("oceans_friend_duration", 60*20);
    private final IntConfigProperty levitateCost = createCostProperty("levitate_cost", 30);
    // in ticks
    private final IntConfigProperty levitateDuration = createTimeProperty("levitate_duration", 5*20);
    private final IntConfigProperty featherFallCost = createCostProperty("feather_fall_cost", 10);
    // in ticks
    private final IntConfigProperty featherFallDuration = createTimeProperty("feather_fall_duration", 5*20);
    private final IntConfigProperty fireImmunityCost = createCostProperty("fire_immunity_cost", 15);
    // in ticks
    private final IntConfigProperty fireImmunityDuration = createTimeProperty("fire_immunity_duration", 15*20);
    private final IntConfigProperty earthPillarCost = createCostProperty("earth_pillar_cost", 20);
    private final IntConfigProperty earthCircleCost = createCostProperty("earth_circle_cost", 50);
    private final IntConfigProperty earthRemovalCost = createCostProperty("earth_removal_cost", 25);
    private final IntConfigProperty earthRemovalRadius = createDistanceProperty("earth_removal_radius", 4);
    private final IntConfigProperty fireCircleCost = createCostProperty("fire_circle_cost", 40);
    private final IntConfigProperty lavaRemovalCost = createCostProperty("lava_removal_cost", 18);
    private final IntConfigProperty lavaRemovalRadius = createDistanceProperty("lava_removal_radius", 3);
    private final IntConfigProperty waterDomeCost = createCostProperty("water_dome_cost", 30);
    private final IntConfigProperty waterDomeRadius = createDistanceProperty("water_dome_radius", 3);
    private final IntConfigProperty waterRemovalCost = createCostProperty("water_removal_cost", 15);
    private final IntConfigProperty waterRemovalRadius = createDistanceProperty("water_removal_radius", 4);
    private final IntConfigProperty charmCost = createCostProperty("charm_cost", 15);
    // in ticks
    private final IntConfigProperty charmDuration = createTimeProperty("charm_duration", 10*20);

    public Config() {
    }

    public static PercentageConfigProperty createPercentageProperty(String name, int defaultValue) {
        return new PercentageConfigProperty(name, defaultValue, 0, Integer.MAX_VALUE, GameRules.Category.MISC);
    }

    public static IntConfigProperty createTimeProperty(String name, int defaultValue) {
        return new IntConfigProperty(name, defaultValue, 1, Integer.MAX_VALUE, GameRules.Category.MISC);
    }

    public static IntConfigProperty createCostProperty(String name, int defaultValue) {
        return new IntConfigProperty(name, defaultValue, 1, Integer.MAX_VALUE, GameRules.Category.MISC);
    }

    public static IntConfigProperty createDistanceProperty(String name, int defaultValue) {
        return new IntConfigProperty(name, defaultValue, 1, Integer.MAX_VALUE, GameRules.Category.MISC);
    }

    public static IntConfigProperty createDamageProperty(String name, int defaultValue) {
        return new IntConfigProperty(name, defaultValue, 0, Integer.MAX_VALUE, GameRules.Category.MISC);
    }

    public PercentageConfigProperty getBeginnerManaFactor() {
        return beginnerManaFactor;
    }

    public PercentageConfigProperty getNoviceManaFactor() {
        return noviceManaFactor;
    }

    public PercentageConfigProperty getExpertManaFactor() {
        return expertManaFactor;
    }

    public PercentageConfigProperty getMasterManaFactor() {
        return masterManaFactor;
    }

    public IntConfigProperty getWandCoolDown() {
        return wandCoolDown;
    }

    public IntConfigProperty getSmallMissileCost() {
        return smallMissileCost;
    }

    public IntConfigProperty getMediumMissileCost() {
        return mediumMissileCost;
    }

    public IntConfigProperty getBigMissileCost() {
        return bigMissileCost;
    }

    public IntConfigProperty getSmallAirMissileDamage() {
        return smallAirMissileDamage;
    }

    public IntConfigProperty getMediumAirMissileDamage() {
        return mediumAirMissileDamage;
    }

    public IntConfigProperty getBigAirMissileDamage() {
        return bigAirMissileDamage;
    }

    public IntConfigProperty getSmallEarthMissileDamage() {
        return smallEarthMissileDamage;
    }

    public IntConfigProperty getMediumEarthMissileDamage() {
        return mediumEarthMissileDamage;
    }

    public IntConfigProperty getBigEarthMissileDamage() {
        return bigEarthMissileDamage;
    }

    public IntConfigProperty getSmallFireMissileDamage() {
        return smallFireMissileDamage;
    }

    public IntConfigProperty getMediumFireMissileDamage() {
        return mediumFireMissileDamage;
    }

    public IntConfigProperty getBigFireMissileDamage() {
        return bigFireMissileDamage;
    }

    public IntConfigProperty getSmallLightningMissileDamage() {
        return smallLightningMissileDamage;
    }

    public IntConfigProperty getMediumLightningMissileDamage() {
        return mediumLightningMissileDamage;
    }

    public IntConfigProperty getBigLightningMissileDamage() {
        return bigLightningMissileDamage;
    }

    public IntConfigProperty getSmallWaterMissileDamage() {
        return smallWaterMissileDamage;
    }

    public IntConfigProperty getMediumWaterMissileDamage() {
        return mediumWaterMissileDamage;
    }

    public IntConfigProperty getBigWaterMissileDamage() {
        return bigWaterMissileDamage;
    }

    public IntConfigProperty getAccelerateGrowthCost() {
        return accelerateGrowthCost;
    }

    public IntConfigProperty getInstantHealCost() {
        return instantHealCost;
    }

    public IntConfigProperty getShieldCost() {
        return shieldCost;
    }

    public IntConfigProperty getShieldDuration() {
        return shieldDuration;
    }

    public IntConfigProperty getNightVisionCost() {
        return nightVisionCost;
    }

    public IntConfigProperty getNightVisionDuration() {
        return nightVisionDuration;
    }

    public IntConfigProperty getGillsCost() {
        return gillsCost;
    }

    public IntConfigProperty getGillsDuration() {
        return gillsDuration;
    }

    public IntConfigProperty getOceansFriendCost() {
        return oceansFriendCost;
    }

    public IntConfigProperty getOceansFriendDuration() {
        return oceansFriendDuration;
    }

    public IntConfigProperty getLevitateCost() {
        return levitateCost;
    }

    public IntConfigProperty getLevitateDuration() {
        return levitateDuration;
    }

    public IntConfigProperty getFeatherFallCost() {
        return featherFallCost;
    }

    public IntConfigProperty getFeatherFallDuration() {
        return featherFallDuration;
    }

    public IntConfigProperty getFireImmunityCost() {
        return fireImmunityCost;
    }

    public IntConfigProperty getFireImmunityDuration() {
        return fireImmunityDuration;
    }

    public IntConfigProperty getEarthPillarCost() {
        return earthPillarCost;
    }

    public IntConfigProperty getEarthCircleCost() {
        return earthCircleCost;
    }

    public IntConfigProperty getEarthRemovalCost() {
        return earthRemovalCost;
    }

    public IntConfigProperty getEarthRemovalRadius() {
        return earthRemovalRadius;
    }

    public IntConfigProperty getFireCircleCost() {
        return fireCircleCost;
    }

    public IntConfigProperty getLavaRemovalCost() {
        return lavaRemovalCost;
    }

    public IntConfigProperty getLavaRemovalRadius() {
        return lavaRemovalRadius;
    }

    public IntConfigProperty getWaterDomeCost() {
        return waterDomeCost;
    }

    public IntConfigProperty getWaterDomeRadius() {
        return waterDomeRadius;
    }

    public IntConfigProperty getWaterRemovalCost() {
        return waterRemovalCost;
    }

    public IntConfigProperty getWaterRemovalRadius() {
        return waterRemovalRadius;
    }

    public IntConfigProperty getCharmCost() {
        return charmCost;
    }

    public IntConfigProperty getCharmDuration() {
        return charmDuration;
    }
}