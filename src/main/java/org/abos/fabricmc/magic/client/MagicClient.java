package org.abos.fabricmc.magic.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.abos.fabricmc.magic.Magic;
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

        HudRenderCallback.EVENT.register(new HudRenderCallback() {
            @Override
            public void onHudRender(MatrixStack matrixStack, float tickDelta) {
                PlayerEntity player = MinecraftClient.getInstance().player;
                if (player != null) {
                    int manaMax = Magic.MANA.get(player).getMax() / 10;
                    int mana = Magic.MANA.get(player).getValue() / 10;
                    int k = MinecraftClient.getInstance().getWindow().getScaledHeight() - 39;
                    int l = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2 + 91;
                    int m = k;
                    int n = 0;

//                    RenderSystem.setShader(GameRenderer::getPositionTexShader); // The shader you want to use
//                    RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
//                    RenderSystem.setShaderTexture(0,YOUR_TEXTURE_IDENTIFIER);
                    for(boolean bl = false; manaMax > 0; n += 20) {
                        int o = Math.min(manaMax, 10);
                        manaMax -= o;

                        for(int p = 0; p < o; ++p) {
                            int r = 0;
                            int s = l - p * 8 - 9;
                            // draw the full stuff
                            DrawableHelper.drawTexture(matrixStack, s, m, 0, 52f, 9f, 9, 9, 256, 256);
                            if (p * 2 + 1 + n < mana) {
                                DrawableHelper.drawTexture(matrixStack, s, m, 0, 88f, 9f, 9, 9, 256, 256);
                            }

                            if (p * 2 + 1 + n == mana) {
                                DrawableHelper.drawTexture(matrixStack, s, m, 0, (float)97, (float)9, 9, 9, 256, 256);
                            }
                        }

                        m -= 10;
                    }
                }
            }
        });
    }
}
