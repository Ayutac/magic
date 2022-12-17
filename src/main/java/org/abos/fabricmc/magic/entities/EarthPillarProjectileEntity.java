package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.Spell;
import org.abos.fabricmc.magic.utils.WorldUtils;

public class EarthPillarProjectileEntity extends MagicProjectileEntity {

    public EarthPillarProjectileEntity(EntityType<MagicProjectileEntity> type, World world) {
        super(type, world);
    }

    public EarthPillarProjectileEntity(EntityType<MagicProjectileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
    }

    public EarthPillarProjectileEntity(World world, PlayerEntity user) {
        this(Spell.EARTH_PILLAR.getEntityType(), user, world);
    }

    @Override
    protected void applyBlockEffects(BlockHitResult blockHitResult) {
        super.applyBlockEffects(blockHitResult);
        BlockPos blockPos = blockHitResult.getBlockPos();
        if (world.getBlockState(blockPos).isIn(MagicContent.EARTH_TAG)) {
            WorldUtils.raiseBlock(world, blockPos, 3);
        }
    }

}
