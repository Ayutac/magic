package org.abos.fabricmc.magic.entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.utils.MissileSize;

import java.util.List;

public class EarthPillarProjectileEntity extends MagicProjectileEntity {

    public EarthPillarProjectileEntity(EntityType<EarthPillarProjectileEntity> type, World world) {
        super(type, world);
        finishConstructor();
    }

    public EarthPillarProjectileEntity(EntityType<EarthPillarProjectileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
        finishConstructor();
    }

    private void finishConstructor() {
        setMaxAge(20*60);
        setDamage(0d);
        setSound(SoundEvents.BLOCK_ROOTED_DIRT_PLACE);
    }

    @Override
    protected void applyBlockEffects(BlockHitResult blockHitResult) {
        super.applyBlockEffects(blockHitResult);
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        BlockPos targetPos = blockPos;
        if (blockState.isIn(MagicContent.EARTH_PILLAR_TARGETS)) {
            if (isReplaceableByPillar(targetPos.up())) {
                targetPos = targetPos.up();
                world.setBlockState(targetPos, blockState);
                if (isReplaceableByPillar(targetPos.up())) {
                    targetPos = targetPos.up();
                    world.setBlockState(targetPos, blockState);
                    if (isReplaceableByPillar(targetPos.up())) {
                        targetPos = targetPos.up();
                        world.setBlockState(targetPos, blockState);
                    }
                }
            }
        }
        targetPos = targetPos.up();
        if (!targetPos.equals(blockPos.up())) {
            List<Entity> entities = world.getNonSpectatingEntities(Entity.class, new Box(0d,1d,0d,1d,2d,1d).offset(blockPos));
            for (Entity entity : entities) {
                BlockPos diff = targetPos.subtract(blockPos.up());
                entity.setPos(entity.getX(), entity.getY() + diff.getY(), entity.getZ());
            }
        }
    }

    private boolean isReplaceableByPillar(BlockPos blockPos) {
        return world.getBlockState(blockPos).isAir() || !world.getFluidState(blockPos).isEmpty();
    }

    public static EarthPillarProjectileEntity create(World world, PlayerEntity user) {
        EarthPillarProjectileEntity entity = new EarthPillarProjectileEntity(MagicContent.EARTH_PILLAR_PROJECTILE_ENTITY_TYPE, user, world);
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, MissileSize.SMALL.getSpeed(), MissileSize.SMALL.getDivergence());
        return entity;
    }

}
