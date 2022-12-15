package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.utils.MissileSize;
import org.abos.fabricmc.magic.utils.WorldUtils;

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
        if (world.getBlockState(blockPos).isIn(MagicContent.EARTH_TAG)) {
            WorldUtils.raiseBlock(world, blockPos, 3);
        }
    }

    public static EarthPillarProjectileEntity create(World world, PlayerEntity user) {
        EarthPillarProjectileEntity entity = new EarthPillarProjectileEntity(MagicContent.EARTH_PILLAR_PROJECTILE_ENTITY_TYPE, user, world);
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, MissileSize.SMALL.getSpeed(), MissileSize.SMALL.getDivergence());
        return entity;
    }

}
