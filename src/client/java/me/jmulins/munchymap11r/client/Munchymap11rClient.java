package me.jmulins.munchymap11r.client;

import me.jmulins.munchymap11r.commands.PhelperCommand;
import me.jmulins.munchymap11r.commands.PhelperWaypointsCommand;
import me.jmulins.munchymap11r.config.PhelperConfig;
import me.jmulins.munchymap11r.utils.TitleUtils;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.ActionResult;


public class Munchymap11rClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        // Initialize config
        PhelperConfig config = PhelperConfig.INSTANCE;
        System.out.println("Vigilance config loaded!");

        PhelperCommand.execute();
        PhelperWaypointsCommand.execute();

        registerPickaxeHitEvent();

    }


    public void registerPickaxeHitEvent() {
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            ItemStack handItem = player.getMainHandStack();
            if (PhelperConfig.INSTANCE.enablePickaxeBreakingWarning &&
                    handItem.isIn(ItemTags.PICKAXES) &&
                    handItem.getMaxDamage() - handItem.getDamage() <= PhelperConfig.INSTANCE.getPickaxeAlertDurabilityThreshold()) {
                TitleUtils.showTitleClientSimple(PhelperConfig.INSTANCE.pickaxeAlertText);
                player.playSound(PhelperConfig.INSTANCE.getPickaxeAlertSelectedSound(), PhelperConfig.INSTANCE.pickaxeAlertVolume, 1f);
            }

            return ActionResult.PASS;
        });
    }
}
