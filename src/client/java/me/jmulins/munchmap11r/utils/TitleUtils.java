package me.jmulins.munchmap11r.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.text.Text;

public class TitleUtils {

    public static void showTitleClientSimple(String title) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc == null) return;

        mc.execute(() -> {
            ClientPlayNetworkHandler handler = mc.getNetworkHandler();
            if (handler == null) return;

            // Use the Text-only constructor (mapping that only exposes this will accept it)
            handler.onTitle(new TitleS2CPacket(Text.literal(title)));
        });
    }

}
