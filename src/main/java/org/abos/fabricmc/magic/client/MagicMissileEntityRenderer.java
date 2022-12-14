package org.abos.fabricmc.magic.client;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;
import org.abos.fabricmc.magic.Magic;
import org.abos.fabricmc.magic.entities.MagicProjectileEntity;

public class MagicMissileEntityRenderer<T extends MagicProjectileEntity> extends ProjectileEntityRenderer<T> {

    private final String fileName;

    public MagicMissileEntityRenderer(EntityRendererFactory.Context context, String fileName) {
        super(context);
        this.fileName = fileName;
    }

    @Override
    public Identifier getTexture(T entity) {
        return new Identifier(Magic.MOD_ID, "textures/entities/projectiles/" + fileName);
    }
}
