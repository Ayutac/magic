package org.abos.fabricmc.magic;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Magic implements ModInitializer {

    public final static String MOD_ID = "magic";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        LOGGER.info("Initializing the Magic Mod...");
    }
}
