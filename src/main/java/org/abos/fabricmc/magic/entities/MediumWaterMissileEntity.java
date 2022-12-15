package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicConfig;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.utils.MissileSize;

public class MediumWaterMissileEntity extends MagicProjectileEntity {

    public MediumWaterMissileEntity(EntityType<MediumWaterMissileEntity> type, World world) {
        super(type, world);
        finishConstructor();
    }

    public MediumWaterMissileEntity(EntityType<MediumWaterMissileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
        finishConstructor();
    }

    private void finishConstructor() {
        setMaxAge(20*60);
        setDamage(MagicConfig.MEDIUM_WATER_MISSILE_DAMAGE);
        setExtinguishing(true);
        setSound(SoundEvents.ITEM_BUCKET_EMPTY);
    }

    public static MediumWaterMissileEntity create(World world, PlayerEntity user) {
        MediumWaterMissileEntity entity = new MediumWaterMissileEntity(MagicContent.MEDIUM_WATER_MISSILE_ENTITY_TYPE, user, world);
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, MissileSize.MEDIUM.getSpeed(), MissileSize.MEDIUM.getDivergence());
        return entity;
    }

}
