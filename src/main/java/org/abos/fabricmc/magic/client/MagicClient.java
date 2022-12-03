package org.abos.fabricmc.magic.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.abos.fabricmc.magic.MagicContent;

public class MagicClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(MagicContent.SMALL_EARTH_MISSILE_ENTITY_TYPE, (context) -> new MagicMissileEntityRenderer<>(context));
        EntityRendererRegistry.register(MagicContent.MEDIUM_EARTH_MISSILE_ENTITY_TYPE, (context) -> new MagicMissileEntityRenderer<>(context));
        EntityRendererRegistry.register(MagicContent.BIG_EARTH_MISSILE_ENTITY_TYPE, (context) -> new MagicMissileEntityRenderer<>(context));
    }
}
