package org.abos.fabricmc.magic.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.abos.fabricmc.magic.Spell;

public class MagicClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        for (Spell spell : Spell.values()) {
            if (spell.isProjectile()) {
                EntityRendererRegistry.register(spell.getEntityType(), context -> new MagicMissileEntityRenderer<>(context, spell.getType().getTexture()));
            }
        }
    }

}
