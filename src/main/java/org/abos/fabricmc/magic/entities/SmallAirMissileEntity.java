package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.utils.MissileSize;

public class SmallAirMissileEntity extends MagicMissileEntity {

    public SmallAirMissileEntity(EntityType<SmallAirMissileEntity> type, World world) {
        super(type, world);
        finishConstructor();
    }

    public SmallAirMissileEntity(EntityType<SmallAirMissileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
        finishConstructor();
    }

    private void finishConstructor() {
        setMaxAge(20*60);
        setDamage(0d);
        setNoGravity(true);
        setSound(SoundEvents.ENTITY_PHANTOM_SWOOP);
    }

    public static SmallAirMissileEntity create(World world, PlayerEntity user) {
        SmallAirMissileEntity entity = new SmallAirMissileEntity(MagicContent.SMALL_AIR_MISSILE_ENTITY_TYPE, user, world);
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, MissileSize.SMALL.getSpeed(), MissileSize.SMALL.getDivergence());
        return entity;
    }

}
