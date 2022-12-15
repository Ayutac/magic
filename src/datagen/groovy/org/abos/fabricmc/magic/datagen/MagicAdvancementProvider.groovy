package org.abos.fabricmc.magic.datagen

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider
import net.minecraft.advancement.Advancement

import java.util.function.Consumer

class MagicAdvancementProvider extends FabricAdvancementProvider {

    protected MagicAdvancementProvider(FabricDataOutput output) {
        super(output)
    }

    @Override
    void generateAdvancement(Consumer<Advancement> consumer) {

    }
}
