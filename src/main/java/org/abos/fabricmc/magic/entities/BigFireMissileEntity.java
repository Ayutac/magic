package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.utils.MissileSize;

public class BigFireMissileEntity extends MagicMissileEntity {

    public BigFireMissileEntity(EntityType<BigFireMissileEntity> type, World world) {
        super(type, world);
        finishConstructor();
    }

    public BigFireMissileEntity(EntityType<BigFireMissileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
        finishConstructor();
    }

    private void finishConstructor() {
        setMaxAge(20*60);
        setDamage(10d);
        setSound(SoundEvents.ENTITY_BLAZE_SHOOT);
    }

    public static BigFireMissileEntity create(World world, PlayerEntity user) {
        BigFireMissileEntity entity = new BigFireMissileEntity(MagicContent.BIG_FIRE_MISSILE_ENTITY_TYPE, user, world);
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, MissileSize.BIG.getSpeed(), MissileSize.BIG.getDivergence());
        return entity;
    }

}
