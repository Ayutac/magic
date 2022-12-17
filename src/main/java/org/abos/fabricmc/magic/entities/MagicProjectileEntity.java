package org.abos.fabricmc.magic.entities;

import net.minecraft.block.AbstractCandleBlock;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.utils.ProjectileSettings;

public class MagicProjectileEntity extends PersistentProjectileEntity {

    private int maxAge = 20*60;

    private ProjectileSettings settings;

    public MagicProjectileEntity(EntityType<MagicProjectileEntity> type, World world) {
        super(type, world);
    }

    public MagicProjectileEntity(EntityType<MagicProjectileEntity> type, LivingEntity owner, World world) {
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

    public ProjectileSettings getSettings() {
        return settings;
    }

    public void setSettings(ProjectileSettings settings) {
        this.settings = settings;
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
    public int getFireTicks() {
        if (settings != null) {
            return settings.getFireTicks();
        }
        return super.getFireTicks();
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
        if (entity.getType() == EntityType.BLAZE && settings.isExtinguishing()) {
            return (float)(getDamage()*2);
        }
        return (float)getDamage();
    }

    protected void applyEntityEffects(Entity target) {
        if (settings != null && settings.isExtinguishing() & target.getFireTicks() > 0) {
            target.extinguishWithSound();
        }
        if (getFireTicks() > target.getFireTicks()) {
            target.setFireTicks(getFireTicks());
        }
        if (settings == null) {
            return;
        }
        if (settings.getParalysisTicks() > 0 && target instanceof LivingEntity entity) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, settings.getParalysisTicks(), 4));
        }
        if (settings.getKnockUpSpeed() > 0f) {
            target.addVelocity(new Vec3d(0d, settings.getKnockUpSpeed(), 0d));
        }
    }

    protected void applyBlockEffects(BlockHitResult blockHitResult) {
        if (settings != null && settings.isExtinguishing()) {
            BlockPos pos = blockHitResult.getBlockPos();
            BlockState blockState = world.getBlockState(pos);
            if (blockState.isIn(BlockTags.FIRE)) {
                this.world.removeBlock(blockHitResult.getBlockPos(), false);
            }
            else if (AbstractCandleBlock.isLitCandle(blockState)) {
                AbstractCandleBlock.extinguish(null, blockState, world, pos);
            } else if (CampfireBlock.isLitCampfire(blockState)) {
                this.world.syncWorldEvent(null, 1009, pos, 0);
                CampfireBlock.extinguish(getOwner(), world, pos, blockState);
                this.world.setBlockState(pos, blockState.with(CampfireBlock.LIT, false));
            }
        }
        if (getFireTicks() > 0) {
            BlockPos blockPos2 = blockHitResult.getBlockPos().offset(blockHitResult.getSide());
            if (AbstractFireBlock.canPlaceAt(world, blockPos2, blockHitResult.getSide())) {
                BlockState blockState2 = AbstractFireBlock.getState(world, blockPos2);
                world.setBlockState(blockPos2, blockState2, 11); // set on fire
            }
        }
        if (settings != null && settings.getKnockUpSpeed() != 0) {
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
