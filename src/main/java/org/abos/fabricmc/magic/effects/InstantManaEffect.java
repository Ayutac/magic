package org.abos.fabricmc.magic.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import org.abos.fabricmc.magic.Magic;
import org.abos.fabricmc.magic.cca.NatMaxComponent;
import org.jetbrains.annotations.Nullable;

public class InstantManaEffect extends InstantStatusEffect {

    public InstantManaEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        if (target instanceof PlayerEntity player) {
            if (isBeneficial()) {
                int amount = (int) (proximity * (double) (25 << amplifier) + 0.5);
                NatMaxComponent mana = Magic.MANA.get(player);
                if (mana.canAdd(amount)) {
                    mana.add(amount);
                } else {
                    mana.fill();
                }
            }
            else {
                int amount = (int) (proximity * (double) (25 << amplifier) + 0.5);
                NatMaxComponent mana = Magic.MANA.get(player);
                if (mana.canSubtract(amount)) {
                    mana.subtract(amount);
                } else {
                    mana.clear();
                }
            }
        }
    }
}
