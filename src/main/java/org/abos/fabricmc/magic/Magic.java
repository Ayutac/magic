package org.abos.fabricmc.magic;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.abos.fabricmc.magic.cca.NatMaxComponent;
import org.abos.fabricmc.magic.commands.CommandInitializer;
import org.abos.fabricmc.magic.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Magic implements ModInitializer {

    public final static String MOD_ID = "magic";

    public final static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public final static Identifier MANA_ID = new Identifier(MOD_ID, "mana");

    public final static Identifier MAX_MANA_ID = new Identifier(MOD_ID, "max_mana");

    public final static ComponentKey<NatMaxComponent> MANA = ComponentRegistry.getOrCreate(MANA_ID, NatMaxComponent.class);

    public final static Config CONFIG = new Config();

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing the Magic Mod...");
        MagicContent.init();
        //CONFIG.init();
        CommandInitializer.init();
        EntitySleepEvents.STOP_SLEEPING.register((livingEntity, blockPos) -> {
            if (livingEntity instanceof PlayerEntity && livingEntity.world.isDay()) {
                MANA.get(livingEntity).fill();
            }
        });
        LOGGER.info("Magic Mod has been initialized!");
    }
}
