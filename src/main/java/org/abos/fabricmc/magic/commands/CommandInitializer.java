package org.abos.fabricmc.magic.commands;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CommandInitializer {

    private CommandInitializer() {
        /* No instantiation. */
    }

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> ManaFillCommand.register(dispatcher));
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> ManaClearCommand.register(dispatcher));
    }

}
