package me.jmulins.munchymap11r.commands;

import me.jmulins.munchymap11r.client.PhelperConfig;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class PhelperCommand implements ICommand{
    public static void execute() {
        // Register command to open Vigilance GUI
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("phleper")
                    .executes(context -> {
                        // Open Vigilance GUI
                        PhelperConfig.INSTANCE.openGui();
                        return 1;
                    }));
        });
    }
}
