package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.utils.MissileSize;

public class MediumAirMissileEntity extends MagicMissileEntity {

    public MediumAirMissileEntity(EntityType<MediumAirMissileEntity> type, World world) {
        super(type, world);
        finishConstructor();
    }

    public MediumAirMissileEntity(EntityType<MediumAirMissileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
        finishConstructor();
    }

    private void finishConstructor() {
        setMaxAge(20*60);
        setDamage(1d);
        setSound(SoundEvents.ENTITY_PHANTOM_SWOOP);
    }

    public static MediumAirMissileEntity create(World world, PlayerEntity user) {
        MediumAirMissileEntity entity = new MediumAirMissileEntity(MagicContent.MEDIUM_AIR_MISSILE_ENTITY_TYPE, user, world);
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, MissileSize.MEDIUM.getSpeed(), MissileSize.MEDIUM.getDivergence());
        return entity;
    }

}