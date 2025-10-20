package me.jmulins.munchmap11r.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

public class CommandUtils {

    /**
     * Executes a command from the client side
     * @param command The command without the leading slash (e.g., "pay test 38")
     */
    public static void executeCommand(String command) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (player == null) {
            System.err.println("Cannot execute command: player is null");
            return;
        }

        // Add slash if not present
        if (!command.startsWith("/")) {
            command = "/" + command;
        }

        // Send the command as a chat message (which handles commands)
        player.networkHandler.sendChatCommand(command.substring(1));
    }

    /**
     * Sends a chat message from the client
     * @param message The message to send (without slash)
     */
    public static void sendChatMessage(String message) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        if (player == null) {
            System.err.println("Cannot send message: player is null");
            return;
        }

        player.networkHandler.sendChatMessage(message);
    }
}