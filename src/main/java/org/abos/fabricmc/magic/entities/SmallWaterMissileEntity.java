package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.utils.MissileSize;

public class SmallWaterMissileEntity extends MagicMissileEntity {

    public SmallWaterMissileEntity(EntityType<SmallWaterMissileEntity> type, World world) {
        super(type, world);
        finishConstructor();
    }

    public SmallWaterMissileEntity(EntityType<SmallWaterMissileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
        finishConstructor();
    }

    private void finishConstructor() {
        setMaxAge(20*60);
        this.setDamage(0d);
    }

    public static SmallWaterMissileEntity create(World world, PlayerEntity user) {
        SmallWaterMissileEntity entity = new SmallWaterMissileEntity(MagicContent.SMALL_WATER_MISSILE_ENTITY_TYPE, user, world);
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, MissileSize.SMALL.getSpeed(), MissileSize.SMALL.getDivergence());
        return entity;
    }

}
