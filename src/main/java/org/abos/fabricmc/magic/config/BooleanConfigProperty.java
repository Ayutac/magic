package org.abos.fabricmc.magic.config;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class BooleanConfigProperty extends ConfigProperty<Boolean, GameRules.BooleanRule> {

    public BooleanConfigProperty(String name, Boolean defaultValue) {
        super(name, defaultValue);
    }

    public BooleanConfigProperty(String name, Boolean defaultValue, GameRules.Category ruleCategory) {
        super(name, defaultValue, ruleCategory);
    }

    @Override
    protected Boolean getRuleValue(World world) throws IllegalStateException {
        if (!isWithGameRule()) {
            throw new IllegalStateException("This property doesn't have a rule!");
        }
        return world.getGameRules().get(getRuleKey()).get();
    }

    @Override
    protected void setRuleValue(Boolean value, MinecraftServer server) throws IllegalStateException {
        getRule(server.getOverworld()).set(value, server);
    }

    @Override
    public Boolean validate(Boolean value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException("Value for "+getName()+" must be true or false!");
        }
        return value;
    }

    @Override
    protected GameRules.Key<GameRules.BooleanRule> registerRule() {
        return GameRuleRegistry.register(getName(), getRuleCategory(), GameRuleFactory.createBooleanRule(getDefaultValue()));
    }
}
