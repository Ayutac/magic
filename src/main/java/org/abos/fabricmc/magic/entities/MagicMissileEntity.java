package org.abos.fabricmc.magic.entities;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public abstract class MagicMissileEntity extends ProjectileEntity {

    private int maxAge = 50;

    protected MagicMissileEntity(EntityType<? extends MagicMissileEntity> type, World world) {
        super(type, world);
    }

    protected MagicMissileEntity(EntityType<? extends MagicMissileEntity> type, LivingEntity owner, World world) {
        this(type, world);
        setOwner(owner);
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
    protected void initDataTracker() {}

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

    protected abstract float getDamage();

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        DamageSource damageSource = getDamageSource(getOwner() == null ? this : getOwner());
        if (getOwner() instanceof LivingEntity) {
            ((LivingEntity)getOwner()).onAttacking(entityHitResult.getEntity());
        }

        Entity entity = entityHitResult.getEntity();

        if (entity.damage(damageSource, getDamage())) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity livingEntity) {

                if (!this.world.isClient && getOwner() instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity, getOwner());
                    EnchantmentHelper.onTargetDamaged((LivingEntity)getOwner(), livingEntity);
                }

                //this.onHit(livingEntity);
                if (getOwner() != null && livingEntity != getOwner() && livingEntity instanceof PlayerEntity && getOwner() instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)getOwner()).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
                }
            }

            // this.playSound(this.sound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        } else {
            // entity.setFireTicks(j);
            this.setVelocity(this.getVelocity().multiply(-0.1));
            this.setYaw(this.getYaw() + 180.0F);
            this.prevYaw += 180.0F;
            if (!this.world.isClient && this.getVelocity().lengthSquared() < 1.0E-7) {
                this.discard();
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        kill();
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (!world.isClient) {
            discard();
        }
    }
}
