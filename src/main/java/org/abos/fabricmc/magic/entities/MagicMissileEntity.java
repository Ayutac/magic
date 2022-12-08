package org.abos.fabricmc.magic.entities;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class MagicMissileEntity extends PersistentProjectileEntity {

    private int maxAge = 50;
    private boolean extinguishing = false;
    private int fireTicks = 0;

    private float knockupSpeed = 0f;

    protected MagicMissileEntity(EntityType<? extends MagicMissileEntity> type, World world) {
        super(type, world);
    }

    protected MagicMissileEntity(EntityType<? extends MagicMissileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
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

    public boolean isExtinguishing() {
        return extinguishing;
    }

    public void setExtinguishing(boolean extinguishing) {
        this.extinguishing = extinguishing;
    }

    @Override
    public int getFireTicks() {
        return fireTicks;
    }

    @Override
    public void setFireTicks(int fireTicks) {
        if (fireTicks < 0) {
            throw new IllegalArgumentException("Fire ticks must be non-negative!");
        }
        this.fireTicks = fireTicks;
    }

    public float getKnockupSpeed() {
        return knockupSpeed;
    }

    public void setKnockupSpeed(float knockupSpeed) {
        if (knockupSpeed < 0) {
            throw new IllegalArgumentException("Knockup speed must be non-negative!");
        }
        this.knockupSpeed = knockupSpeed;
    }

    @Override
    public void tick() {
        super.tick();
        if (age > getMaxAge()) {
            kill();
        }
    }

    protected DamageSource getDamageSource(Entity attacker) {
        return new ProjectileDamageSource("magic", this, attacker).setProjectile();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        DamageSource damageSource = getDamageSource(getOwner() == null ? this : getOwner());
        if (getOwner() instanceof LivingEntity) {
            ((LivingEntity)getOwner()).onAttacking(entityHitResult.getEntity());
        }

        Entity entity = entityHitResult.getEntity();

        if (entity.damage(damageSource, calculateDamageOn(entity))) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }
            applyEntityEffects(entity);

            if (entity instanceof LivingEntity livingEntity) {

                if (!this.world.isClient && getOwner() instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity, getOwner());
                    EnchantmentHelper.onTargetDamaged((LivingEntity)getOwner(), livingEntity);
                }

                this.onHit(livingEntity);
                if (getOwner() != null && livingEntity != getOwner() && livingEntity instanceof PlayerEntity && getOwner() instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)getOwner()).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
                }
            }

            this.playSound(getSound(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.discard();
            }
        } else {
            this.setVelocity(this.getVelocity().multiply(-0.1));
            this.setYaw(this.getYaw() + 180.0F);
            this.prevYaw += 180.0F;
            if (!this.world.isClient && this.getVelocity().lengthSquared() < 1.0E-7) {
                this.discard();
            }
        }
    }

    protected float calculateDamageOn(Entity entity) {
        if (entity.getType() == EntityType.BLAZE && isExtinguishing()) {
            return (float)(getDamage()*2);
        }
        return (float)getDamage();
    }

    protected void applyEntityEffects(Entity target) {
        if (isExtinguishing() & target.getFireTicks() > 0) {
            target.extinguishWithSound();
        }
        if (getFireTicks() > target.getFireTicks()) {
            target.setFireTicks(getFireTicks());
        }
        if (getKnockupSpeed() != 0) {
            target.addVelocity(new Vec3d(0d, getKnockupSpeed(), 0d));
        }
    }

    protected void applyBlockEffects(BlockHitResult blockHitResult) {
        if (isExtinguishing()) {
            // TODO extinguish fires, including camp fires
        }
        if (getFireTicks() > 0) {
            BlockPos blockPos2 = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
            if (AbstractFireBlock.canPlaceAt(world, blockPos2, blockHitResult.getSide())) {
                BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
                world.setBlockState(blockPos2, blockState2, 11); // set on fire
            }
        }
        if (getKnockupSpeed() != 0) {
            // TODO detachable blocks like torches should fall off
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        applyBlockEffects(blockHitResult);
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

    @Override
    public boolean isShotFromCrossbow() {
        return false;
    }

    @Override
    public boolean isCritical() {
        return false;
    }
}
