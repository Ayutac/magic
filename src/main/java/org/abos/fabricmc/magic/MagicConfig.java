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

    private MagicConfig() {
        /* No instantiation. */
    }

}
