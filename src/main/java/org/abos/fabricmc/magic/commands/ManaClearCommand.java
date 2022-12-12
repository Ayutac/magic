package org.abos.fabricmc.magic.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import org.abos.fabricmc.magic.Magic;
import org.abos.fabricmc.magic.cca.NatMaxComponent;

import static net.minecraft.server.command.CommandManager.literal;

public class ManaClearCommand {

    private ManaClearCommand() {
        /* No instantiation. */
    }
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher){
        dispatcher.register(
                literal("mana").then(literal("clear")
                        .requires(source -> source.hasPermissionLevel(3))
                        .executes(ManaClearCommand::run)));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        PlayerEntity player = context.getSource().getPlayer();
        if (player == null) {
            return 0;
        }
        NatMaxComponent mana = Magic.MANA.get(player);
        final int value = mana.getValue();
        mana.clear();
        return value;
    }
}
