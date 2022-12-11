package org.abos.fabricmc.magic.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.abos.fabricmc.magic.Magic;
import org.abos.fabricmc.magic.cca.NatMaxComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "wakeUp()V", at = @At("HEAD"))
    public void wakeUp(CallbackInfo callbackInfo) {
        NatMaxComponent mana = Magic.MANA.get((PlayerEntity)(Object)this);
        mana.fill();
    }

}
