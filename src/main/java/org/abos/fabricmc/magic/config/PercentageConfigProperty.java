package org.abos.fabricmc.magic.config;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class PercentageConfigProperty extends IntConfigProperty {

    protected PercentageConfigProperty(String name, Integer defaultValue, int minValue, int maxValue, boolean withGameRule, GameRules.Category ruleCategory) {
        super(name, defaultValue, minValue, maxValue, withGameRule, ruleCategory);
    }

    public PercentageConfigProperty(String name, Integer defaultValue, int minValue, int maxValue) {
        this(name, defaultValue, minValue, maxValue, false, null);
    }

    public PercentageConfigProperty(String name, Integer defaultValue) {
        this(name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public PercentageConfigProperty(String name, Integer defaultValue, int minValue, int maxValue, GameRules.Category ruleCategory) {
        this(name, defaultValue, minValue, maxValue, true, ruleCategory);
    }

    public PercentageConfigProperty(String name, Integer defaultValue, GameRules.Category ruleCategory) {
        this(name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE, ruleCategory);
    }

    /**
     * @return {@link #getValue(World)}<code>/100d</code>
     */
    public double getDecimalValue(World world) {
        return getValue(world) / 100d;
    }

    /**
     * @see #getDecimalValue(World)
     */
    public double getDecimalValue() {
        return getDecimalValue(null);
    }
}
