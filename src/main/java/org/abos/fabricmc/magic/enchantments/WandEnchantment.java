package org.abos.fabricmc.magic.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import org.abos.fabricmc.magic.items.WandItem;

public class WandEnchantment extends Enchantment {

    public WandEnchantment(Rarity weight) {
        super(weight, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof WandItem;
    }

    @Override
    public int getMinPower(int level) {
        return Math.max(1, getRarity().ordinal() * 15);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        return !(other instanceof WandEnchantment);
    }
}
