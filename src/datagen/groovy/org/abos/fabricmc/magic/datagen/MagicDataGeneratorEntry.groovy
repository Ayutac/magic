package org.abos.fabricmc.magic.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class MagicDataGeneratorEntry implements DataGeneratorEntrypoint {

    @Override
    void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        def pack = fabricDataGenerator.createPack()

        def add = { FabricDataGenerator.Pack.RegistryDependentFactory factory ->
            pack.addProvider factory
        }

        add MagicAdvancementProvider::new

    }

}
