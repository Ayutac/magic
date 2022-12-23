package org.abos.fabricmc.magic.config;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.Objects;

public abstract class ConfigProperty<T, R extends GameRules.Rule<R>> {

    private final String name;

    private T value;

    private final T defaultValue;

    private final boolean withGameRule;

    private final GameRules.Key<R> ruleKey;

    private final GameRules.Category ruleCategory;

    /**
     * @param defaultValue Note that this can only be validated against <code>null</code>.
     */
    protected ConfigProperty(String name, T defaultValue, boolean withGameRule, GameRules.Category ruleCategory) {
        this.name = Objects.requireNonNull(name);
        this.defaultValue = Objects.requireNonNull(defaultValue);
        resetValue();
        this.withGameRule = withGameRule;
        this.ruleCategory = ruleCategory;
        if (withGameRule) {
            ruleKey = null;
        }
        else {
            ruleKey = registerRule();
        }
    }

    public ConfigProperty(String name, T defaultValue) {
        this(name, defaultValue, false, null);
    }

    public ConfigProperty(String name, T defaultValue, GameRules.Category ruleCategory) {
        this(name, defaultValue, true, Objects.requireNonNull(ruleCategory));
    }

    public String getName() {
        return name;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    /**
     * Returns the value from the given rule. Use {@link #getValue(World)} instead of this method.
     * @throws IllegalStateException If this method is called but {@link #isWithGameRule()} returns <code>false</code>.
     */
    protected abstract T getRuleValue(World world) throws IllegalStateException;

    public T getValue(World world) {
        if (isWithGameRule() && world != null) {
            T rVal = getRuleValue(world);
            if (rVal == null) {
                return value;
            }
            return value = rVal;
        }
        return value;
    }

    public T getValue() {
        return getValue(null);
    }

    /**
     * Sets the rule to the given value. Use {@link #setValue(Object, MinecraftServer)} instead of this method.
     * @throws IllegalStateException  If this method is called but {@link #isWithGameRule()} returns <code>false</code>.
     */
    protected abstract void setRuleValue(T value, MinecraftServer server) throws IllegalStateException;

    public void setValue(T value, MinecraftServer server) {
        this.value = validate(value);
        if (isWithGameRule() && server != null) {
            setRuleValue(value, server);
        }
    }

    public void setValue(T value) {
        setValue(value, null);
    }

    public void resetValue() {
        value = getDefaultValue();
    }

    /**
     * Validates the input parameter or throws an {@link IllegalArgumentException}l
     * @param value the value to be validated
     * @return the input parameter
     * @throws IllegalArgumentException If the given value is invalid.
     */
    public abstract T validate(T value) throws IllegalArgumentException;

    public boolean isWithGameRule() {
        return withGameRule;
    }

    public GameRules.Key<R> getRuleKey() {
        return ruleKey;
    }

    /**
     * Returns the rule or <code>null</code> if there is none.
     */
    public R getRule(World world) {
        Objects.requireNonNull(world);
        if (!isWithGameRule()) {
            return null;
        }
        return world.getGameRules().get(ruleKey);
    }

    protected abstract GameRules.Key<R> registerRule();

    public GameRules.Category getRuleCategory() {
        return ruleCategory;
    }
}
