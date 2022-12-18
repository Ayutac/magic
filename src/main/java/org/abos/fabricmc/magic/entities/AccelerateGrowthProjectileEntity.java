package org.abos.fabricmc.magic.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.Spell;

public class AccelerateGrowthProjectileEntity extends MagicProjectileEntity {

    public AccelerateGrowthProjectileEntity(EntityType<MagicProjectileEntity> type, World world) {
        super(type, world);
    }

    public AccelerateGrowthProjectileEntity(EntityType<MagicProjectileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
    }

    public AccelerateGrowthProjectileEntity(World world, PlayerEntity user) {
        this(Spell.ACCELERATE_GROWTH.getEntityType(), user, world);
    }

    @Override
    protected void applyBlockEffects(BlockHitResult blockHitResult) {
        super.applyBlockEffects(blockHitResult);
        BlockPos pos = blockHitResult.getBlockPos();
        BlockState blockState = world.getBlockState(pos);
        if (blockState.getBlock() instanceof Fertilizable fertilizable) {
            if (fertilizable.isFertilizable(world, pos, blockState, world.isClient)) {
                if (world instanceof ServerWorld) {
                    fertilizable.grow((ServerWorld)world, world.random, pos, blockState);
                }
            }
        }
        pos = pos.up();
        blockState = world.getBlockState(pos);
        if (blockState.getBlock() instanceof Fertilizable fertilizable) {
            if (fertilizable.isFertilizable(world, pos, blockState, world.isClient)) {
                if (world instanceof ServerWorld) {
                    fertilizable.grow((ServerWorld)world, world.random, pos, blockState);
                }
            }
        }
    }
}
