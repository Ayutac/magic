package org.abos.fabricmc.magic.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.abos.fabricmc.magic.MagicContent;

public class MagicClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(MagicContent.SMALL_AIR_MISSILE_ENTITY_TYPE, context -> new MagicMissileEntityRenderer<>(context, "air_missile.png"));
        EntityRendererRegistry.register(MagicContent.MEDIUM_AIR_MISSILE_ENTITY_TYPE, context -> new MagicMissileEntityRenderer<>(context, "air_missile.png"));
        EntityRendererRegistry.register(MagicContent.BIG_AIR_MISSILE_ENTITY_TYPE, context -> new MagicMissileEntityRenderer<>(context, "air_missile.png"));
        EntityRendererRegistry.register(MagicContent.SMALL_EARTH_MISSILE_ENTITY_TYPE, context -> new MagicMissileEntityRenderer<>(context, "earth_missile.png"));
        EntityRendererRegistry.register(MagicContent.MEDIUM_EARTH_MISSILE_ENTITY_TYPE, context -> new MagicMissileEntityRenderer<>(context, "earth_missile.png"));
        EntityRendererRegistry.register(MagicContent.BIG_EARTH_MISSILE_ENTITY_TYPE, context -> new MagicMissileEntityRenderer<>(context, "earth_missile.png"));
        EntityRendererRegistry.register(MagicContent.SMALL_FIRE_MISSILE_ENTITY_TYPE, context -> new MagicMissileEntityRenderer<>(context, "fire_missile.png"));
        EntityRendererRegistry.register(MagicContent.MEDIUM_FIRE_MISSILE_ENTITY_TYPE, context -> new MagicMissileEntityRenderer<>(context, "fire_missile.png"));
        EntityRendererRegistry.register(MagicContent.BIG_FIRE_MISSILE_ENTITY_TYPE, context -> new MagicMissileEntityRenderer<>(context, "fire_missile.png"));
        EntityRendererRegistry.register(MagicContent.SMALL_WATER_MISSILE_ENTITY_TYPE, context -> new MagicMissileEntityRenderer<>(context, "water_missile.png"));
        EntityRendererRegistry.register(MagicContent.MEDIUM_WATER_MISSILE_ENTITY_TYPE, context -> new MagicMissileEntityRenderer<>(context, "water_missile.png"));
        EntityRendererRegistry.register(MagicContent.BIG_WATER_MISSILE_ENTITY_TYPE, context -> new MagicMissileEntityRenderer<>(context, "water_missile.png"));
    }
}
