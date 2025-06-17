
package com.wardf.screenspy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

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
            takeScreenshot(); // BEZ new Thread tutaj
            cleanupOldScreenshots();
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

            new Thread(() -> {
                try {
                    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
                            int i = x + (height - y - 1) * width;
                            int rgba = pixels[i];
                            int rgb = ((rgba >> 16) & 0xFF) << 16 | ((rgba >> 8) & 0xFF) << 8 | (rgba & 0xFF);
                            image.setRGB(x, y, rgb);
                        }
                    }

                    // Skalowanie obrazu do 75%
                    int scaledWidth = (int) (width * 0.75);
                    int scaledHeight = (int) (height * 0.75);
                    Image scaled = image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                    BufferedImage resized = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = resized.createGraphics();
                    g2d.drawImage(scaled, 0, 0, null);
                    g2d.dispose();

                    if (!SCREEN_DIR.exists()) SCREEN_DIR.mkdirs();
                    File outputPng = new File(SCREEN_DIR, FORMATTER.format(new Date()) + ".png");
                    try {
                        ImageIO.write(resized, "png", outputPng);
                        storedScreenshots.add(new FileTimePair(outputPng, System.currentTimeMillis()));
                    } catch (Exception pngEx) {
                        pngEx.printStackTrace();
                        // Fallback na JPG
                        File outputJpg = new File(SCREEN_DIR, FORMATTER.format(new Date()) + ".jpg");
                        ImageIO.write(resized, "jpg", outputJpg);
                        storedScreenshots.add(new FileTimePair(outputJpg, System.currentTimeMillis()));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void deleteOldScreenshotsOnStartup() {
        if (!SCREEN_DIR.exists()) return;

        long now = System.currentTimeMillis();
        File[] files = SCREEN_DIR.listFiles((dir, name) -> name.endsWith(".png"));

        if (files != null) {
            for (File file : files) {
                long age = now - file.lastModified();
                if (age >= 1800 * 1000L) { // 30 minut
                    file.delete();
                }
            }
        }
    }

    private static void cleanupOldScreenshots() {
        long now = System.currentTimeMillis();
        int retention = ConfigHandler.retentionSeconds;

        // Usuń z pamięci i z dysku
        storedScreenshots.removeIf(pair -> {
            if (now - pair.timestamp >= retention * 1000L) {
                pair.file.delete();
                return true;
            }
            return false;
        });

        // Usuń pozostałe pliki z folderu
        if (SCREEN_DIR.exists()) {
            File[] files = SCREEN_DIR.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg"));
            if (files != null) {
                for (File file : files) {
                    long age = now - file.lastModified();
                    if (age >= retention * 1000L) {
                        file.delete();
                    }
                }
            }
        }
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
