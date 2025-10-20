package me.jmulins.munchmap11r.client;

import me.jmulins.munchmap11r.utils.TitleUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;


public class Munchmap11rClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("LAUNCHED!!!!!!");

        // Initialize config
        PhelperConfig config = PhelperConfig.INSTANCE;
        System.out.println("Vigilance config loaded!");

        // Register command to open Vigilance GUI
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("phleper")
                    .executes(context -> {
                        // Open Vigilance GUI
                        PhelperConfig.INSTANCE.openGui();
                        return 1;
                    }));
        });

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            BlockState state = world.getBlockState(pos);
            ItemStack handItem = player.getMainHandStack();

            /* Manual spectator check is necessary because AttackBlockCallbacks
               fire before the spectator check */
            if (handItem.isIn(ItemTags.PICKAXES) &&
                    handItem.getMaxDamage() - handItem.getDamage() <= PhelperConfig.INSTANCE.getDurabilityThreshold()) {
                TitleUtils.showTitleClientSimple("YOOOO");
                player.playSound(SoundEvents.ENTITY_GHAST_HURT, 2f, 1f);
            }

            return ActionResult.PASS;
        });
    }
}
