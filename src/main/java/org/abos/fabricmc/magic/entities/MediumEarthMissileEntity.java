package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.utils.MissileSize;

public class MediumEarthMissileEntity extends MagicProjectileEntity {

    public MediumEarthMissileEntity(EntityType<MediumEarthMissileEntity> type, World world) {
        super(type, world);
        finishConstructor();
    }

    public MediumEarthMissileEntity(EntityType<MediumEarthMissileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
        finishConstructor();
    }

    private void finishConstructor() {
        setMaxAge(20*60);
        setDamage(6d);
        setSound(SoundEvents.BLOCK_ROOTED_DIRT_PLACE);
    }

    public static MediumEarthMissileEntity create(World world, PlayerEntity user) {
        MediumEarthMissileEntity entity = new MediumEarthMissileEntity(MagicContent.MEDIUM_EARTH_MISSILE_ENTITY_TYPE, user, world);
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, MissileSize.MEDIUM.getSpeed(), MissileSize.MEDIUM.getDivergence());
        return entity;
    }

}
