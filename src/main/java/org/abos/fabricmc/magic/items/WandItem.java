package org.abos.fabricmc.magic.items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.entities.MagicMissileEntity;
import org.abos.fabricmc.magic.entities.SmallEarthMissileEntity;

public class WandItem extends Item {

    public WandItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!world.isClient) {
            ItemStack stack = user.getStackInHand(hand);
            MagicMissileEntity ballEntity;
            if (EnchantmentHelper.getLevel(MagicContent.SMALL_EARTH_MISSILE_ENCHANTMENT, stack) > 0) {
                ballEntity = SmallEarthMissileEntity.create(world, user);
            }
            else {
                return TypedActionResult.pass(user.getStackInHand(hand));
            }
            world.spawnEntity(ballEntity);
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
