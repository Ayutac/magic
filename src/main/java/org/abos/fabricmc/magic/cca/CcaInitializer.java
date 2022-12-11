package org.abos.fabricmc.magic.cca;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import org.abos.fabricmc.magic.Magic;

public class CcaInitializer implements EntityComponentInitializer {

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(Magic.MANA, it -> new ManaComponent(), RespawnCopyStrategy.ALWAYS_COPY);
    }

}
