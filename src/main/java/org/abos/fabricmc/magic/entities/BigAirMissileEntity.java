package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.utils.MissileSize;

public class BigAirMissileEntity extends MagicMissileEntity {

    public BigAirMissileEntity(EntityType<BigAirMissileEntity> type, World world) {
        super(type, world);
        finishConstructor();
    }

    public BigAirMissileEntity(EntityType<BigAirMissileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
        finishConstructor();
    }

    private void finishConstructor() {
        setMaxAge(20*60);
        this.setDamage(3d);
    }

    public static BigAirMissileEntity create(World world, PlayerEntity user) {
        BigAirMissileEntity entity = new BigAirMissileEntity(MagicContent.BIG_AIR_MISSILE_ENTITY_TYPE, user, world);
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, MissileSize.BIG.getSpeed(), MissileSize.BIG.getDivergence());
        return entity;
    }

}
