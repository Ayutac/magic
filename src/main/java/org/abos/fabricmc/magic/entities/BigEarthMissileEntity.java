package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicConfig;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.utils.MissileSize;

public class BigEarthMissileEntity extends MagicProjectileEntity {

    public BigEarthMissileEntity(EntityType<BigEarthMissileEntity> type, World world) {
        super(type, world);
        finishConstructor();
    }

    public BigEarthMissileEntity(EntityType<BigEarthMissileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
        finishConstructor();
    }

    private void finishConstructor() {
        setMaxAge(20*60);
        setDamage(MagicConfig.BIG_EARTH_MISSILE_DAMAGE);
        setSound(SoundEvents.BLOCK_ROOTED_DIRT_PLACE);
    }

    public static BigEarthMissileEntity create(World world, PlayerEntity user) {
        BigEarthMissileEntity entity = new BigEarthMissileEntity(MagicContent.BIG_EARTH_MISSILE_ENTITY_TYPE, user, world);
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, MissileSize.BIG.getSpeed(), MissileSize.BIG.getDivergence());
        return entity;
    }

}
