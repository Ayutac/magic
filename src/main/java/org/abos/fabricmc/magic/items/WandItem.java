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
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.Magic;
import org.abos.fabricmc.magic.MagicConfig;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.cca.NatMaxComponent;
import org.abos.fabricmc.magic.Spell;
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
            if (EnchantmentHelper.getLevel(Spell.SMALL_AIR_MISSILE.getEnchantment(), stack) > 0) {
                enchantment = Spell.SMALL_AIR_MISSILE.getEnchantment();
                ballEntity = SmallAirMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(Spell.MEDIUM_AIR_MISSILE.getEnchantment(), stack) > 0) {
                enchantment = Spell.MEDIUM_AIR_MISSILE.getEnchantment();
                ballEntity = MediumAirMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(Spell.BIG_AIR_MISSILE.getEnchantment(), stack) > 0) {
                enchantment = Spell.BIG_AIR_MISSILE.getEnchantment();
                ballEntity = BigAirMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(Spell.SMALL_EARTH_MISSILE.getEnchantment(), stack) > 0) {
                enchantment = Spell.SMALL_EARTH_MISSILE.getEnchantment();
                ballEntity = BigEarthMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(Spell.MEDIUM_EARTH_MISSILE.getEnchantment(), stack) > 0) {
                enchantment = Spell.MEDIUM_EARTH_MISSILE.getEnchantment();
                ballEntity = MediumEarthMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(Spell.BIG_EARTH_MISSILE.getEnchantment(), stack) > 0) {
                enchantment = Spell.BIG_EARTH_MISSILE.getEnchantment();
                ballEntity = BigEarthMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(Spell.SMALL_FIRE_MISSILE.getEnchantment(), stack) > 0) {
                enchantment = Spell.SMALL_FIRE_MISSILE.getEnchantment();
                ballEntity = BigFireMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(Spell.MEDIUM_FIRE_MISSILE.getEnchantment(), stack) > 0) {
                enchantment = Spell.MEDIUM_FIRE_MISSILE.getEnchantment();
                ballEntity = MediumFireMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(Spell.BIG_FIRE_MISSILE.getEnchantment(), stack) > 0) {
                enchantment = Spell.BIG_FIRE_MISSILE.getEnchantment();
                ballEntity = BigFireMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(Spell.SMALL_WATER_MISSILE.getEnchantment(), stack) > 0) {
                enchantment = Spell.SMALL_WATER_MISSILE.getEnchantment();
                ballEntity = BigWaterMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(Spell.MEDIUM_WATER_MISSILE.getEnchantment(), stack) > 0) {
                enchantment = Spell.MEDIUM_WATER_MISSILE.getEnchantment();
                ballEntity = MediumWaterMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(Spell.BIG_WATER_MISSILE.getEnchantment(), stack) > 0) {
                enchantment = Spell.BIG_WATER_MISSILE.getEnchantment();
                ballEntity = BigWaterMissileEntity.create(world, user);
            }
            else if (EnchantmentHelper.getLevel(Spell.EARTH_PILLAR.getEnchantment(), stack) > 0) {
                enchantment = Spell.EARTH_PILLAR.getEnchantment();
                ballEntity = EarthPillarProjectileEntity.create(world, user);
            }
            else {
                Spell spell = Spell.getEnchantedSpell(stack);
                if (spell == null) {
                    return TypedActionResult.pass(user.getStackInHand(hand));
                }
                enchantment = spell.getEnchantment();
                ballEntity = null;
            }

            // check if can cast
            int cost = (int)Math.max(1,enchantment.getManaCost()*manaFactor);
            if (!mana.canSubtract(cost) && !user.isCreative()) {
                user.sendMessage(Text.translatable("messages.magic.not_enough_mana"), true);
                return TypedActionResult.pass(user.getStackInHand(hand));
            }

            // apply effect
            if (ballEntity != null) {
                world.spawnEntity(ballEntity);
            }
            else if (enchantment == Spell.INSTANT_HEAL.getEnchantment()) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1));
                //user.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 1f, 1f);
            }
            else if (enchantment == Spell.SHIELD.getEnchantment()) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, MagicConfig.SHIELD_DURATION, 1));
                //user.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1f, 1f);
            }
            else if (enchantment == Spell.NIGHT_VISION.getEnchantment()) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, MagicConfig.NIGHT_VISION_DURATION));
            }
            else if (enchantment == Spell.GILLS.getEnchantment()) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, MagicConfig.GILLS_DURATION));
            }
            else if (enchantment == Spell.OCEANS_FRIEND.getEnchantment()) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, MagicConfig.OCEANS_FRIEND_DURATION));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, MagicConfig.OCEANS_FRIEND_DURATION));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, MagicConfig.OCEANS_FRIEND_DURATION));
            }
            else if (enchantment == Spell.LEVITATE.getEnchantment()) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, MagicConfig.LEVITATE_DURATION));
            }
            else if (enchantment == Spell.FEATHER_FALL.getEnchantment()) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, MagicConfig.FEATHER_DURATION));
            }
            else if (enchantment == Spell.FIRE_IMMUNITY.getEnchantment()) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, MagicConfig.FIRE_IMMUNITY_DURATION));
            }
            else if (enchantment == Spell.EARTH_CIRCLE.getEnchantment()) {
                for (BlockPos pos : WorldUtils.circleAroundGround(world, user.getBlockPos().down(),3)) {
                    if (world.getBlockState(pos).isIn(MagicContent.EARTH_TAG)) {
                        WorldUtils.raiseBlock(world, pos, 3);
                    }
                }
            }
            else if (enchantment == Spell.FIRE_CIRCLE.getEnchantment()) {
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
