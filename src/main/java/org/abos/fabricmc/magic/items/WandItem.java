package org.abos.fabricmc.magic.items;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.Magic;
import org.abos.fabricmc.magic.MagicConfig;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.cca.NatMaxComponent;
import org.abos.fabricmc.magic.enchantments.WandEnchantment;
import org.abos.fabricmc.magic.entities.*;
import org.abos.fabricmc.magic.utils.WorldUtils;

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
            final MagicProjectileEntity ballEntity;
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
            else if (EnchantmentHelper.getLevel(MagicContent.EARTH_PILLAR_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.EARTH_PILLAR_ENCHANTMENT;
                ballEntity = EarthPillarProjectileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(MagicContent.INSTANT_HEAL_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.INSTANT_HEAL_ENCHANTMENT;
                ballEntity = null;
            }
            else if (EnchantmentHelper.getLevel(MagicContent.SHIELD_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.SHIELD_ENCHANTMENT;
                ballEntity = null;
            }
            else if (EnchantmentHelper.getLevel(MagicContent.NIGHT_VISION_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.NIGHT_VISION_ENCHANTMENT;
                ballEntity = null;
            }
            else if (EnchantmentHelper.getLevel(MagicContent.GILLS_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.GILLS_ENCHANTMENT;
                ballEntity = null;
            }
            else if (EnchantmentHelper.getLevel(MagicContent.OCEANS_FRIEND_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.OCEANS_FRIEND_ENCHANTMENT;
                ballEntity = null;
            }
            else if (EnchantmentHelper.getLevel(MagicContent.LEVITATE_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.LEVITATE_ENCHANTMENT;
                ballEntity = null;
            }
            else if (EnchantmentHelper.getLevel(MagicContent.FEATHER_FALL_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.FEATHER_FALL_ENCHANTMENT;
                ballEntity = null;
            }
            else if (EnchantmentHelper.getLevel(MagicContent.FIRE_IMMUNITY_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.FIRE_IMMUNITY_ENCHANTMENT;
                ballEntity = null;
            }
            else if (EnchantmentHelper.getLevel(MagicContent.EARTH_CIRCLE_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.EARTH_CIRCLE_ENCHANTMENT;
                ballEntity = null;
            }
            else if (EnchantmentHelper.getLevel(MagicContent.FIRE_CIRCLE_ENCHANTMENT, stack) > 0) {
                enchantment = MagicContent.FIRE_CIRCLE_ENCHANTMENT;
                ballEntity = null;
            }
            else {
                return TypedActionResult.pass(user.getStackInHand(hand));
            }

            // check if can cast
            int cost = (int)Math.max(1,enchantment.getManaCost()*manaFactor);
            if (!mana.canSubtract(cost) && !user.isCreative()) {
                return TypedActionResult.pass(user.getStackInHand(hand));
            }

            // apply effect
            if (ballEntity != null) {
                world.spawnEntity(ballEntity);
            }
            else if (enchantment == MagicContent.INSTANT_HEAL_ENCHANTMENT) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1));
                //user.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 1f, 1f);
            }
            else if (enchantment == MagicContent.SHIELD_ENCHANTMENT) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, MagicConfig.SHIELD_DURATION, 1));
                //user.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1f, 1f);
            }
            else if (enchantment == MagicContent.NIGHT_VISION_ENCHANTMENT) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, MagicConfig.NIGHT_VISION_DURATION));
            }
            else if (enchantment == MagicContent.GILLS_ENCHANTMENT) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, MagicConfig.GILLS_DURATION));
            }
            else if (enchantment == MagicContent.OCEANS_FRIEND_ENCHANTMENT) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, MagicConfig.OCEANS_FRIEND_DURATION));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, MagicConfig.OCEANS_FRIEND_DURATION));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, MagicConfig.OCEANS_FRIEND_DURATION));
            }
            else if (enchantment == MagicContent.LEVITATE_ENCHANTMENT) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, MagicConfig.LEVITATE_DURATION));
            }
            else if (enchantment == MagicContent.FEATHER_FALL_ENCHANTMENT) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, MagicConfig.FEATHER_DURATION));
            }
            else if (enchantment == MagicContent.FIRE_IMMUNITY_ENCHANTMENT) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, MagicConfig.FIRE_IMMUNITY_DURATION));
            }
            else if (enchantment == MagicContent.EARTH_CIRCLE_ENCHANTMENT) {
                for (BlockPos pos : WorldUtils.circleAroundGround(world, user.getBlockPos().down(),3)) {
                    if (world.getBlockState(pos).isIn(MagicContent.EARTH_TAG)) {
                        WorldUtils.raiseBlock(world, pos, 3);
                    }
                }
            }
            else if (enchantment == MagicContent.FIRE_CIRCLE_ENCHANTMENT) {
                for (BlockPos pos : WorldUtils.circleAroundGround(world, user.getBlockPos().down(),4)) {
                    BlockPos blockPos2 = pos.up();
                    if (AbstractFireBlock.canPlaceAt(world, blockPos2, Direction.UP)) {
                        BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
                        world.setBlockState(blockPos2, blockState2, 11); // set on fire
                    }
                }
            }
            else {
                return TypedActionResult.pass(user.getStackInHand(hand));
            }

            // subtract cost
            if (!user.isCreative()) {
                mana.subtract(cost);
                user.getItemCooldownManager().set(MagicContent.BEGINNER_WAND, MagicConfig.WAND_COOL_DOWN);
                user.getItemCooldownManager().set(MagicContent.NOVICE_WAND, MagicConfig.WAND_COOL_DOWN);
                user.getItemCooldownManager().set(MagicContent.EXPERT_WAND, MagicConfig.WAND_COOL_DOWN);
                user.getItemCooldownManager().set(MagicContent.MASTER_WAND, MagicConfig.WAND_COOL_DOWN);
            }
            stack.damage(1, user, p -> p.sendToolBreakStatus(hand));
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
