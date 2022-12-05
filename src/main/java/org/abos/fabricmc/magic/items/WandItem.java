package org.abos.fabricmc.magic.items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.entities.*;

public class WandItem extends ToolItem {

    public WandItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!world.isClient) {
            ItemStack stack = user.getStackInHand(hand);
            MagicMissileEntity ballEntity;
            if (EnchantmentHelper.getLevel(MagicContent.SMALL_AIR_MISSILE_ENCHANTMENT, stack) > 0) {
                ballEntity = SmallAirMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.MEDIUM_AIR_MISSILE_ENCHANTMENT, stack) > 0) {
                ballEntity = MediumAirMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.BIG_AIR_MISSILE_ENCHANTMENT, stack) > 0) {
                ballEntity = BigAirMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.SMALL_EARTH_MISSILE_ENCHANTMENT, stack) > 0) {
                ballEntity = BigEarthMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.MEDIUM_EARTH_MISSILE_ENCHANTMENT, stack) > 0) {
                ballEntity = MediumEarthMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.BIG_EARTH_MISSILE_ENCHANTMENT, stack) > 0) {
                ballEntity = BigEarthMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.SMALL_FIRE_MISSILE_ENCHANTMENT, stack) > 0) {
                ballEntity = BigFireMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.MEDIUM_FIRE_MISSILE_ENCHANTMENT, stack) > 0) {
                ballEntity = MediumFireMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.BIG_FIRE_MISSILE_ENCHANTMENT, stack) > 0) {
                ballEntity = BigFireMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.SMALL_WATER_MISSILE_ENCHANTMENT, stack) > 0) {
                ballEntity = BigWaterMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.MEDIUM_WATER_MISSILE_ENCHANTMENT, stack) > 0) {
                ballEntity = MediumWaterMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.BIG_WATER_MISSILE_ENCHANTMENT, stack) > 0) {
                ballEntity = BigWaterMissileEntity.create(world, user);
            }
            else {
                return TypedActionResult.pass(user.getStackInHand(hand));
            }
            world.spawnEntity(ballEntity);
            stack.damage(1, user, p -> p.sendToolBreakStatus(hand));
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
