package me.jmulins.munchymap11r.commands;

import me.jmulins.munchymap11r.gui.JavaTestGui;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;

public class PhelperWaypointsCommand implements ICommand{
    public static void execute() {
        // Register command to open Vigilance GUI
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("phleperwaypoints")
                    .executes(context -> {
                        MinecraftClient.getInstance().send(() -> {
                            MinecraftClient.getInstance().setScreen(new JavaTestGui());
                        });
                        return 1;
                    }));
        });
    }

}
