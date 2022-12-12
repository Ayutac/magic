package org.abos.fabricmc.magic.items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.Magic;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.cca.NatMaxComponent;
import org.abos.fabricmc.magic.enchantments.WandEnchantment;
import org.abos.fabricmc.magic.entities.*;

public class WandItem extends ToolItem {

    private final double manaFactor;

    public WandItem(ToolMaterial material, double manaFactor, Settings settings) {
        super(material, settings);
        if (manaFactor <= 0d) {
            throw new IllegalArgumentException("Mana factor must be positive!");
        }
        this.manaFactor = manaFactor;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (!world.isClient) {
            final ItemStack stack = user.getStackInHand(hand);
            final MagicMissileEntity ballEntity;
            final WandEnchantment enchantment;
            final NatMaxComponent mana = Magic.MANA.get(user);
            if (EnchantmentHelper.getLevel(MagicContent.SMALL_AIR_MISSILE_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.SMALL_AIR_MISSILE_ENCHANTMENT;
                ballEntity = SmallAirMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.MEDIUM_AIR_MISSILE_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.MEDIUM_AIR_MISSILE_ENCHANTMENT;
                ballEntity = MediumAirMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.BIG_AIR_MISSILE_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.BIG_AIR_MISSILE_ENCHANTMENT;
                ballEntity = BigAirMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.SMALL_EARTH_MISSILE_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.SMALL_EARTH_MISSILE_ENCHANTMENT;
                ballEntity = BigEarthMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.MEDIUM_EARTH_MISSILE_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.MEDIUM_EARTH_MISSILE_ENCHANTMENT;
                ballEntity = MediumEarthMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.BIG_EARTH_MISSILE_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.BIG_EARTH_MISSILE_ENCHANTMENT;
                ballEntity = BigEarthMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.SMALL_FIRE_MISSILE_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.SMALL_FIRE_MISSILE_ENCHANTMENT;
                ballEntity = BigFireMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.MEDIUM_FIRE_MISSILE_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.MEDIUM_FIRE_MISSILE_ENCHANTMENT;
                ballEntity = MediumFireMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.BIG_FIRE_MISSILE_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.BIG_FIRE_MISSILE_ENCHANTMENT;
                ballEntity = BigFireMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.SMALL_WATER_MISSILE_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.SMALL_WATER_MISSILE_ENCHANTMENT;
                ballEntity = BigWaterMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.MEDIUM_WATER_MISSILE_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.MEDIUM_WATER_MISSILE_ENCHANTMENT;
                ballEntity = MediumWaterMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.BIG_WATER_MISSILE_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.BIG_WATER_MISSILE_ENCHANTMENT;
                ballEntity = BigWaterMissileEntity.create(world, user);
            }
            else {
                return TypedActionResult.pass(user.getStackInHand(hand));
            }
            int cost = (int)Math.max(1,enchantment.getManaCost()*manaFactor);
            if (!mana.canSubstract(cost) && !user.isCreative()) {
                return TypedActionResult.pass(user.getStackInHand(hand));
            }
            world.spawnEntity(ballEntity);
            if (!user.isCreative()) {
                mana.substract(cost);
            }
            stack.damage(1, user, p -> p.sendToolBreakStatus(hand));
            return TypedActionResult.consume(user.getStackInHand(hand));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
