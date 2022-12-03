package org.abos.fabricmc.magic.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class EarthMissileEntity extends MagicMissileEntity {

    protected EarthMissileEntity(EntityType<MagicMissileEntity> type, World world) {
        super(type, world);
        finishConstructor();
    }

    public EarthMissileEntity(EntityType<MagicMissileEntity> type, LivingEntity owner, World world) {
        super(type, owner, world);
        finishConstructor();
    }

    protected EarthMissileEntity(EntityType<MagicMissileEntity> type, double x, double y, double z, World world) {
        super(type, x, y, z, world);
        finishConstructor();
    }

    private void finishConstructor() {
        setMaxAge(80);
    }

}
