package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class SmallEarthMissileEntity extends MagicMissileEntity {

    public SmallEarthMissileEntity(EntityType<SmallEarthMissileEntity> type, World world) {
        super(type, world);
        finishConstructor();
    }

    public SmallEarthMissileEntity(EntityType<SmallEarthMissileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
        finishConstructor();
    }

    private void finishConstructor() {
        setMaxAge(20*60);
        this.setDamage(1d);
    }

}
