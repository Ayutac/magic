package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class MagicMissileEntity extends PersistentProjectileEntity {

    private int maxAge = 50;

    public MagicMissileEntity(EntityType<? extends MagicMissileEntity> type, World world) {
        super(type, world);
    }

    public MagicMissileEntity(EntityType<? extends MagicMissileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
    }

    public MagicMissileEntity(EntityType<? extends MagicMissileEntity> type, double x, double y, double z, World world) {
        super(type, x, y, z, world);
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        if (maxAge <= 0) {
            throw new IllegalArgumentException("Max age must be positive!");
        }
        this.maxAge = maxAge;
    }

    @Override
    public void tick() {
        super.tick();
        if (age > getMaxAge()) {
            kill();
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        kill();
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (!world.isClient && (inGround || isNoClip()) && shake <= 0) {
            discard();
        }
    }

    @Override
    protected ItemStack asItemStack() {
        return ItemStack.EMPTY;
    }
}
