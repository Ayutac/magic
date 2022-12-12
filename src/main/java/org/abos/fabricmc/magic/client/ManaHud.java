package org.abos.fabricmc.magic.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.abos.fabricmc.magic.Magic;

class ManaHud implements HudRenderCallback {

    public static final int HUD_WIDTH = 81;
    public static final int TEXTURE_WIDTH = HUD_WIDTH;
    public static final int HUD_HEIGHT = 9;
    public static final int TEXTURE_HEIGHT = HUD_HEIGHT * 2;

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && !player.isCreative()) {
            final float manaPercent = (float) Magic.MANA.get(player).getValue() / Magic.MANA.get(player).getMax();
            final int manaWidth = Math.round((HUD_WIDTH - 2) * manaPercent);
            final int manaOffset = (HUD_WIDTH - 2) - manaWidth;
            final int rightStartPoint = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2 + 91 - HUD_WIDTH;
            final int downStartPoint = MinecraftClient.getInstance().getWindow().getScaledHeight() - 39 - 3 * 10;

            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, new Identifier(Magic.MOD_ID, "textures/gui/hud_elements.png"));
            DrawableHelper.drawTexture(matrixStack, rightStartPoint, downStartPoint, 0, 0, 0, HUD_WIDTH, HUD_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
            DrawableHelper.drawTexture(matrixStack, rightStartPoint + manaOffset + 1, downStartPoint, 0, manaOffset + 1, HUD_HEIGHT, manaWidth, HUD_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }
    }
}
