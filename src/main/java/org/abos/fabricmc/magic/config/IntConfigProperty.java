package org.abos.fabricmc.magic.config;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class IntConfigProperty extends ConfigProperty<Integer, GameRules.IntRule> {

    private final int minValue;

    private final int maxValue;

    protected IntConfigProperty(String name, Integer defaultValue, int minValue, int maxValue, boolean withGameRule, GameRules.Category ruleCategory) {
        super(name, defaultValue, withGameRule, ruleCategory);
        if (maxValue > minValue) {
            throw new IllegalArgumentException("Min value must be smaller than or equal to max value!");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
        validate(defaultValue);
    }

    public IntConfigProperty(String name, Integer defaultValue, int minValue, int maxValue) {
        this(name, defaultValue, minValue, maxValue, false, null);
    }

    public IntConfigProperty(String name, Integer defaultValue) {
        this(name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public IntConfigProperty(String name, Integer defaultValue, int minValue, int maxValue, GameRules.Category ruleCategory) {
        this(name, defaultValue, minValue, maxValue, true, ruleCategory);
    }

    public IntConfigProperty(String name, Integer defaultValue, GameRules.Category ruleCategory) {
        this(name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE, ruleCategory);
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    @Override
    protected Integer getRuleValue(World world) throws IllegalStateException {
        if (!isWithGameRule()) {
            throw new IllegalStateException("This property doesn't have a rule!");
        }
        return world.getGameRules().get(getRuleKey()).get();
    }

    @Override
    protected void setRuleValue(Integer value, MinecraftServer server) throws IllegalStateException {
        getRule(server.getOverworld()).set(value, server);
    }

    @Override
    public Integer validate(Integer value) throws IllegalArgumentException {
        if (value < getMinValue()) {
            throw new IllegalArgumentException(getName()+" value must be greater than or equal to "+getMinValue()+"!");
        }
        if (value > getMaxValue()) {
            throw new IllegalArgumentException(getName()+" value must be smaller than or equal to "+getMaxValue()+"!");
        }
        return value;
    }

    @Override
    protected GameRules.Key<GameRules.IntRule> registerRule() {
        return GameRuleRegistry.register(getName(), getRuleCategory(), GameRuleFactory.createIntRule(getDefaultValue()));
    }
}
