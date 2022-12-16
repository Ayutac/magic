package org.abos.fabricmc.magic;

public class MagicConfig {

    public static double BEGINNER_MANA_FACTOR = 2d;
    public static double NOVICE_MANA_FACTOR = 1.5d;
    public static double EXPERT_MANA_FACTOR = 1d;
    public static double MASTER_MANA_FACTOR = 0.5d;

    public static int WAND_COOL_DOWN = 20; // in ticks

    public static int SMALL_MISSILE_COST = 2;
    public static int MEDIUM_MISSILE_COST = 5;
    public static int BIG_MISSILE_COST = 12;
    public static double SMALL_AIR_MISSILE_DAMAGE = 0d;
    public static double MEDIUM_AIR_MISSILE_DAMAGE = 1d;
    public static double BIG_AIR_MISSILE_DAMAGE = 3d;
    public static double SMALL_EARTH_MISSILE_DAMAGE = 1d;
    public static double MEDIUM_EARTH_MISSILE_DAMAGE = 6d;
    public static double BIG_EARTH_MISSILE_DAMAGE = 15d;
    public static double SMALL_FIRE_MISSILE_DAMAGE = 0d;
    public static double MEDIUM_FIRE_MISSILE_DAMAGE = 4d;
    public static double BIG_FIRE_MISSILE_DAMAGE = 10d;
    public static double SMALL_WATER_MISSILE_DAMAGE = 0d;
    public static double MEDIUM_WATER_MISSILE_DAMAGE = 4d;
    public static double BIG_WATER_MISSILE_DAMAGE = 10d;
    public static int INSTANT_HEAL_COST = 25;
    public static int SHIELD_COST = 25;
    public static int SHIELD_DURATION = 60*20; // in ticks
    public static int NIGHT_VISION_COST = 8;
    public static int NIGHT_VISION_DURATION = 60*20; // in ticks
    public static int GILLS_COST = 10;
    public static int GILLS_DURATION = 30*20; // in ticks
    public static int OCEANS_FRIEND_COST = 30;
    public static int OCEANS_FRIEND_DURATION = 60*20; // in ticks
    public static int LEVITATE_COST = 30;
    public static int LEVITATE_DURATION = 5*20; // in ticks
    public static int FEATHER_FALL_COST = 10;
    public static int FEATHER_DURATION = 5*20; // in ticks
    public static int FIRE_IMMUNITY_COST = 15;
    public static int FIRE_IMMUNITY_DURATION = 15*20; // in ticks
    public static int EARTH_PILLAR_COST = 20;
    public static int EARTH_CIRCLE_COST = 50;
    public static int EARTH_REMOVAL_COST = 25;
    public static int EARTH_REMOVAL_RADIUS = 4;
    public static int FIRE_CIRCLE_COST = 40;
    public static int WATER_DOME_COST = 30;
    public static int WATER_DOME_RADIUS = 3;
    public static int WATER_REMOVAL_COST = 15;
    public static int WATER_REMOVAL_RADIUS = 4;
    public static int CHARM_COST = 15;
    public static int CHARM_DURATION = 10*20; // in ticks

    private MagicConfig() {
        /* No instantiation. */
    }

}
