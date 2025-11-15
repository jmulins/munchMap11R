package me.jmulins.munchymap11r.client;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexFormat;
import me.jmulins.munchymap11r.Munchymap11r;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3x2fStack;
import org.joml.Matrix4f;

public class WaypointRenderer {

    public static void register() {

        HudElementRegistry.attachElementBefore(VanillaHudElements.CHAT, Identifier.of(Munchymap11r.MOD_ID, "before_chat"), (context, tickCounter) -> {
//            int color = 0xFF0000FF; // Red
//            int targetColor = 0xFF00FF00; // Green

            int backgroundColor = 0x50000000;
            int textColor = 0xFFFFFFFF;

            double x =138.5, y = 72, z = -27.5;

            // You can use the Util.getMeasuringTimeMs() function to get the current time in milliseconds.
            // Divide by 1000 to get seconds.
//            double currentTime = Util.getMeasuringTimeMs() / 1000.0;
//
//            // "lerp" simply means "linear interpolation", which is a fancy way of saying "blend".
//            float lerpedAmount = MathHelper.abs(MathHelper.sin((float) currentTime));
//            int lerpedColor = ColorHelper.lerp(lerpedAmount, color, targetColor);
//
//            // Draw a square with the lerped color.
//            // x1, x2, y1, y2, color
//            context.fill(0, 0, 10, 10, lerpedColor);

            MinecraftClient client = MinecraftClient.getInstance();
            ClientPlayerEntity player = client.player;

            if (player == null)
                return;




            float yaw = client.gameRenderer.getCamera().getCameraYaw(); // -180 to 180
            float pitch = player.getPitch(); // -90 to 90

//            float borderedYaw = yaw > 0 ? ((yaw+180)%360) - 180 : ((yaw-180)%360) + 180;

            float yaw360 = yaw + 180;
//            if (yaw < 0) {
//
//                if (Math.floorDivExact((int) yaw, 180) %2 == -1 ){
//                    borderedYaw = (yaw%180);
//                } else {
//                    borderedYaw = 180 + (yaw%180);
//                }
//
//
//            } else {
//                if (Math.floorDiv((int) yaw, 180) %2 == 1 ) {
//                    borderedYaw =  -yaw%180;
//                }
//
//            }

            int windowWidth = client.getWindow().getScaledWidth();
            int windowHeight = client.getWindow().getScaledHeight();

//            System.out.println(yaw360);

            Camera cam = client.gameRenderer.getCamera();

            Vec3d vectorPlayerBlock = new Vec3d(x-player.getX(),y-player.getY(),z-player.getZ());

//            System.out.println(player.lastX);

            Vec3d playerVector = player.getRotationVecClient();


//Vec3d vecCalculated = new Vec3d(Math.sin((yaw360*2*Math.PI)/360)*Math.cos((pitch*2*Math.PI)/360), -Math.sin((pitch*2*Math.PI)/360), -Math.cos((yaw360*2*Math.PI)/360)*Math.cos((pitch*2*Math.PI)/360));

//            Vec3d caLa = playerVector.add(vecValentin.multiply(-1));

//            System.out.println(client.options.getFov().getValue());

            int fov = client.options.getFov().getValue();


            Vec3d vectorPlayerBlockZeroY = new Vec3d(vectorPlayerBlock.toVector3f()).multiply(1, 0, 1);
            Vec3d playerVectorZeroY = new Vec3d(playerVector.toVector3f()).multiply(1, 0, 1);



            double ingameWidthScreen = Math.tan(((double) fov /2)*(Math.PI/180)) * vectorPlayerBlockZeroY.dotProduct(playerVectorZeroY)*2*2;
            double ingameWidthBlock = vectorPlayerBlockZeroY.crossProduct(playerVectorZeroY).length();
            double windowWidthBlock = windowWidth*ingameWidthBlock/ingameWidthScreen;
            int signe = (int)(vectorPlayerBlockZeroY.crossProduct(playerVectorZeroY).normalize().getY());



//            System.out.println(ingameWidthScreen);

//            context.drawText(MinecraftClient.getInstance().textRenderer, playerVector.toString(),  0, windowHeight/2-10, textColor, false);
            context.drawText(MinecraftClient.getInstance().textRenderer, "test",  (int)((windowWidth/2) + (signe* windowWidthBlock)), (int) pitch, textColor, false);
        });


    }
}