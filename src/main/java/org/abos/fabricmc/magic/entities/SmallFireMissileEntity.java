package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.utils.MissileSize;

public class SmallFireMissileEntity extends MagicMissileEntity {

    public SmallFireMissileEntity(EntityType<SmallFireMissileEntity> type, World world) {
        super(type, world);
        finishConstructor();
    }

    public SmallFireMissileEntity(EntityType<SmallFireMissileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
        finishConstructor();
    }

    private void finishConstructor() {
        setMaxAge(20*60);
        this.setDamage(0d);
    }

    public static SmallFireMissileEntity create(World world, PlayerEntity user) {
        SmallFireMissileEntity entity = new SmallFireMissileEntity(MagicContent.SMALL_FIRE_MISSILE_ENTITY_TYPE, user, world);
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, MissileSize.SMALL.getSpeed(), MissileSize.SMALL.getDivergence());
        return entity;
    }

}
