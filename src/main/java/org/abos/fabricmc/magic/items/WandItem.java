package org.abos.fabricmc.magic.items;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.Magic;
import org.abos.fabricmc.magic.config.Config;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.Spell;
import org.abos.fabricmc.magic.cca.NatMaxComponent;
import org.abos.fabricmc.magic.entities.MagicProjectileEntity;
import org.abos.fabricmc.magic.utils.MagicType;
import org.abos.fabricmc.magic.utils.WorldUtils;

import java.util.EnumMap;
import java.util.Map;

public class WandItem extends ToolItem {

    private final double defaultManaFactor;

    private final Map<MagicType, Double> manaFactor;

    public WandItem(ToolMaterial material, double defaultManaFactor, Settings settings) {
        super(material, settings);
        if (defaultManaFactor <= 0d) {
            throw new IllegalArgumentException("Mana factor must be positive!");
        }
        this.defaultManaFactor = defaultManaFactor;
        this.manaFactor = new EnumMap<>(MagicType.class);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            final ItemStack stack = user.getStackInHand(hand);
            final NatMaxComponent mana = Magic.MANA.get(user);
            final Spell spell = Spell.getEnchantedSpell(stack);
            if (spell == null) {
                return TypedActionResult.pass(user.getStackInHand(hand));
            }
            final MagicProjectileEntity ballEntity = spell.createProjectile(world, user);

            // check if can cast
            int cost = (int)Math.max(1, spell.getManaCost() * manaFactor.getOrDefault(spell.getType(), defaultManaFactor));
            if (!mana.canSubtract(cost) && !user.isCreative()) {
                user.sendMessage(Text.translatable("messages.magic.not_enough_mana"), true);
                return TypedActionResult.pass(user.getStackInHand(hand));
            }

            // apply effect
            spell.playSound(world, user);
            // this takes care of all projectiles
            if (ballEntity != null) {
                world.spawnEntity(ballEntity);
            }
            else if (spell == Spell.INSTANT_HEAL) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1));
            }
            else if (spell == Spell.SHIELD) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, Config.SHIELD_DURATION, 1));
            }
            else if (spell == Spell.NIGHT_VISION) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Config.NIGHT_VISION_DURATION));
            }
            else if (spell == Spell.GILLS) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, Config.GILLS_DURATION));
            }
            else if (spell == Spell.OCEANS_FRIEND) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, Config.OCEANS_FRIEND_DURATION));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, Config.OCEANS_FRIEND_DURATION));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, Config.OCEANS_FRIEND_DURATION));
            }
            else if (spell == Spell.LEVITATE) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, Config.LEVITATE_DURATION));
            }
            else if (spell == Spell.FEATHER_FALL) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, Config.FEATHER_DURATION));
            }
            else if (spell == Spell.FIRE_IMMUNITY) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, Config.FIRE_IMMUNITY_DURATION));
            }
            else if (spell == Spell.EARTH_CIRCLE) {
                for (BlockPos pos : WorldUtils.circleGround(world, user.getBlockPos().down(),3)) {
                    if (world.getBlockState(pos).isIn(MagicContent.EARTH_TAG)) {
                        WorldUtils.raiseBlock(world, pos, 3);
                    }
                }
            }
            else if (spell == Spell.EARTH_REMOVAL) {
                for (BlockPos pos : WorldUtils.filledDome(user.getBlockPos(), Config.EARTH_REMOVAL_RADIUS)) {
                    if (world.getBlockState(pos).isIn(MagicContent.EARTH_TAG)) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                }
            }
            else if (spell == Spell.FIRE_CIRCLE) {
                for (BlockPos pos : WorldUtils.circleGround(world, user.getBlockPos().down(),4)) {
                    BlockPos blockPos2 = pos.up();
                    if (AbstractFireBlock.canPlaceAt(world, blockPos2, Direction.UP)) {
                        BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
                        world.setBlockState(blockPos2, blockState2, 11); // set on fire
                    }
                }
            }
            else if (spell == Spell.LAVA_REMOVAL) {
                for (BlockPos pos : WorldUtils.filledSphere(user.getBlockPos(), Config.LAVA_REMOVAL_RADIUS)) {
                    if (world.getBlockState(pos).isOf(Blocks.LAVA)) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                }
            }
            else if (spell == Spell.WATER_DOME) {
                for (BlockPos pos : WorldUtils.filledDome(user.getBlockPos(), Config.WATER_DOME_RADIUS)) {
                    if (world.getBlockState(pos).canBucketPlace(Fluids.WATER)) {
                        world.setBlockState(pos, Blocks.WATER.getDefaultState(), 11);
                    }
                }
            }
            else if (spell == Spell.WATER_REMOVAL) {
                for (BlockPos pos : WorldUtils.filledSphere(user.getBlockPos(), Config.WATER_REMOVAL_RADIUS)) {
                    if (world.getBlockState(pos).isOf(Blocks.WATER)) {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState());
                    }
                }
            }
            else if (spell == Spell.CHARM) {
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.HERO_OF_THE_VILLAGE, Config.CHARM_DURATION));
            }
            else {
                return TypedActionResult.pass(user.getStackInHand(hand));
            }

            // subtract cost
            if (!user.isCreative()) {
                mana.subtract(cost);
                for (Wand wand : Wand.values()) {
                    user.getItemCooldownManager().set(wand.asItem(), Config.WAND_COOL_DOWN);
                }
            }
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            user.incrementStat(spell.getId());
            stack.damage(1, user, p -> p.sendToolBreakStatus(hand));
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
