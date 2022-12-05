package org.abos.fabricmc.magic.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RotationAxis;
import org.abos.fabricmc.magic.entities.MagicMissileEntity;

public class MagicMissileEntityRenderer<T extends MagicMissileEntity> extends EntityRenderer<T> {

    private final boolean lit;

    public MagicMissileEntityRenderer(EntityRendererFactory.Context context, boolean lit) {
        super(context);
        this.lit = lit;
    }

    public MagicMissileEntityRenderer(EntityRendererFactory.Context context) {
        this(context, false);
    }

    protected int getBlockLight(T entity, BlockPos pos) {
        return this.lit ? 15 : super.getBlockLight(entity, pos);
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (entity.age >= 2 || !(this.dispatcher.camera.getFocusedEntity().squaredDistanceTo(entity) < 12.25)) {
            matrices.push();
            //matrices.scale(this.scale, this.scale, this.scale);
            matrices.multiply(this.dispatcher.getRotation());
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
            RenderSystem.setShaderTexture(0, new Identifier("textures/item/apple.png"));
            //DrawableHelper.drawTexture(matrices, );
            // this.itemRenderer.renderItem(Items.SNOWBALL.getDefaultStack(), ModelTransformation.Mode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getId());
            matrices.pop();
            super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        }
    }

    @Override
    public Identifier getTexture(T entity) {
        return new Identifier("textures/item/apple.png");
    }
}
