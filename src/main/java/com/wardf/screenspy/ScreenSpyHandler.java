
package com.wardf.screenspy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScreenSpyHandler {
    private static final File SCREEN_DIR = new File(Minecraft.getMinecraft().mcDataDir, "screenshots/screenspy");
    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    private static final int SCREEN_INTERVAL_TICKS = 20;

    private static boolean isEnabled = true;
    private static int tickCounter = 0;
    private static final List<FileTimePair> storedScreenshots = new ArrayList<>();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !isEnabled || Minecraft.getMinecraft().isGamePaused()) return;

        tickCounter++;
        if (tickCounter >= SCREEN_INTERVAL_TICKS) {
            tickCounter = 0;
            new Thread(ScreenSpyHandler::takeScreenshot).start();
        }

        cleanupOldScreenshots();
    }

    private static void takeScreenshot() {
        try {
            Minecraft mc = Minecraft.getMinecraft();
            Framebuffer fb = mc.getFramebuffer();
            int width = fb.framebufferWidth;
            int height = fb.framebufferHeight;

            IntBuffer buffer = BufferUtils.createIntBuffer(width * height);
            GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
            GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
            int[] pixels = new int[width * height];
            buffer.get(pixels);

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int i = x + (height - y - 1) * width;
                    image.setRGB(x, y, pixels[i]);
                }
            }

            if (!SCREEN_DIR.exists()) SCREEN_DIR.mkdirs();
            File output = new File(SCREEN_DIR, FORMATTER.format(new Date()) + ".png");
            ImageIO.write(image, "png", output);
            storedScreenshots.add(new FileTimePair(output, System.currentTimeMillis()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void cleanupOldScreenshots() {
        long now = System.currentTimeMillis();
        int retention = ConfigHandler.retentionSeconds;
        storedScreenshots.removeIf(pair -> {
            if (now - pair.timestamp >= retention * 1000L) {
                pair.file.delete();
                return true;
            }
            return false;
        });
    }

    public static void toggleEnabled() {
        isEnabled = !isEnabled;
    }

    public static boolean isEnabled() {
        return isEnabled;
    }

    private static class FileTimePair {
        File file;
        long timestamp;
        FileTimePair(File file, long timestamp) {
            this.file = file;
            this.timestamp = timestamp;
        }
    }
}
