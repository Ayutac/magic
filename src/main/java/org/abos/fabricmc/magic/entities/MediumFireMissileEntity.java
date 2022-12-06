package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.abos.fabricmc.magic.MagicContent;
import org.abos.fabricmc.magic.utils.MissileSize;

public class MediumFireMissileEntity extends MagicMissileEntity {

    public MediumFireMissileEntity(EntityType<MediumFireMissileEntity> type, World world) {
        super(type, world);
        finishConstructor();
    }

    public MediumFireMissileEntity(EntityType<MediumFireMissileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
        finishConstructor();
    }

    private void finishConstructor() {
        setMaxAge(20*60);
        setDamage(4d);
        setFireTicks(5);
        setNoGravity(true);
        setSound(SoundEvents.ENTITY_BLAZE_SHOOT);
    }

    public static MediumFireMissileEntity create(World world, PlayerEntity user) {
        MediumFireMissileEntity entity = new MediumFireMissileEntity(MagicContent.MEDIUM_FIRE_MISSILE_ENTITY_TYPE, user, world);
        entity.setVelocity(user, user.getPitch(), user.getYaw(), 0f, MissileSize.MEDIUM.getSpeed(), MissileSize.MEDIUM.getDivergence());
        return entity;
    }

}
