package org.abos.fabricmc.magic.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.abos.fabricmc.magic.Magic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    private static final int HUD_WIDTH = 81;
    private static final int TEXTURE_WIDTH = HUD_WIDTH;
    private static final int HUD_HEIGHT = 9;
    private static final int TEXTURE_HEIGHT = HUD_HEIGHT * 2;

    @Inject(method = "renderStatusBars(Lnet/minecraft/client/util/math/MatrixStack;)V", at = @At("RETURN"))
    private void magic$renderManaBar(MatrixStack matrixStack, CallbackInfo callbackInfo) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && !player.isCreative()) {
            final float manaPercent = (float) Magic.MANA.get(player).getValue() / Magic.MANA.get(player).getMax();
            final int manaWidth = Math.round((HUD_WIDTH - 2) * manaPercent);
            final int manaOffset = (HUD_WIDTH - 2) - manaWidth;
            final int rightStartPoint = MinecraftClient.getInstance().getWindow().getScaledWidth() / 2 + 91 - HUD_WIDTH;
            final int yOffset = player.isSubmergedInWater() ? 30 : 10;
            final int downStartPoint = MinecraftClient.getInstance().getWindow().getScaledHeight() - 39 - yOffset;

            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShaderTexture(0, new Identifier(Magic.MOD_ID, "textures/gui/hud_elements.png"));
            DrawableHelper.drawTexture(matrixStack, rightStartPoint, downStartPoint, 0, 0, 0, HUD_WIDTH, HUD_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
            DrawableHelper.drawTexture(matrixStack, rightStartPoint + manaOffset + 1, downStartPoint, 0, manaOffset + 1, HUD_HEIGHT, manaWidth, HUD_HEIGHT, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        }
    }

}
