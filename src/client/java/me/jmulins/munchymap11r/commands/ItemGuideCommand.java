package me.jmulins.munchymap11r.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import me.jmulins.munchymap11r.config.PhelperConfig;
import me.jmulins.munchymap11r.gui.SingleItemGuideGui;
import me.jmulins.munchymap11r.gui.WaypointsGui;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minidev.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;


public class ItemGuideCommand implements ICommand{
    // View the itemguide page for the specified id
    public static void execute() {

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {

            JsonArray jsonItemsArray = queryAPI();
            dispatcher.register(ClientCommandManager.literal("itemguide")
                    .then(ClientCommandManager.argument("itemId", IntegerArgumentType.integer())
                            .executes(context -> {

                                int itemId = IntegerArgumentType.getInteger(context, "itemId");

                                context.getSource().sendFeedback(Text.literal("Opening item guide for ID: " + itemId));


                                JsonObject jsonItem = null;
                                if (jsonItemsArray == null){
                                    JsonArray fallbackJsonItemsArray = queryAPI();
                                    if (fallbackJsonItemsArray == null){
                                        context.getSource().sendFeedback(Text.literal("Failed to open item-guide GUI for ID: " + itemId));
                                    } else {

                                        for (JsonElement element : fallbackJsonItemsArray) {
                                            JsonObject obj = element.getAsJsonObject();
                                            if (obj.has("id") && obj.get("id").getAsInt() == itemId) {
                                                jsonItem = obj;
                                                break;
                                            }
                                        }
                                    }
                                } else {


                                    for (JsonElement element : jsonItemsArray) {
                                        JsonObject obj = element.getAsJsonObject();
                                        if (obj.has("id") && obj.get("id").getAsInt() == itemId) {
                                            jsonItem = obj;
                                            break;
                                        }
                                    }
                                }

                                        final JsonObject foundItem = jsonItem;

                                        if (foundItem != null) {
                                            // Item found - Use it
                                            String name = foundItem.get("name").getAsString();
                                            String rarity = foundItem.get("rarity").getAsString();
                                            System.out.println("Found: " + name + " (" + rarity + ")");

                                            MinecraftClient.getInstance().send(() -> {
                                                MinecraftClient.getInstance().setScreen(new SingleItemGuideGui(foundItem));
                                            });
                                        } else {
                                            context.getSource().sendFeedback(Text.literal("Failed to open item-guide GUI for ID: " + itemId));
                                        }


                                return 1;
                            })
                    )
            );
        });
    }

    private static JsonArray queryAPI() {
        JsonArray jsonItemsArray = null;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://item-guide.com/api/items.php"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            jsonItemsArray = JsonParser.parseString(response.body())
                    .getAsJsonArray();
        } catch (Exception e) {
            System.out.println("failed to fetch item-guide API");
            e.printStackTrace();
        }
        return jsonItemsArray;
    }
}
