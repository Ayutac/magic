package org.abos.fabricmc.magic.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;
import org.abos.fabricmc.magic.entities.MagicMissileEntity;

public class MagicMissileEntityRenderer<T extends MagicMissileEntity> extends ProjectileEntityRenderer<T> {

    public MagicMissileEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(T entity) {
        return new Identifier("textures/entity/projectiles/arrow.png");
    }
}
