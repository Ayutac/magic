package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class BigEarthMissileEntity extends MagicMissileEntity {

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
        this.setDamage(15d);
    }

}
