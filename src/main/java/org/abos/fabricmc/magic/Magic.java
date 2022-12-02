package org.abos.fabricmc.magic;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Magic implements ModInitializer {

    public final static String MOD_ID = "magic";

    public final static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public final static Wand BEGINNER_WAND = new Wand(new FabricItemSettings());

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, new Identifier(MOD_ID, "beginner_wand"), BEGINNER_WAND);
        LOGGER.info("Initializing the Magic Mod...");
    }
}
