package com.wardf.screenspy;

import net.minecraftforge.common.MinecraftForge;

public class ClientProxy {
    public void init() {
        MinecraftForge.EVENT_BUS.register(ScreenSpyHandler.class);
        MinecraftForge.EVENT_BUS.register(new KeyHandler());
        ScreenSpyKeybinds.register(); // Rejestracja klawiszy
        MinecraftForge.EVENT_BUS.register(new KeyHandler());
        MinecraftForge.EVENT_BUS.register(new ScreenSpyHandler());
        ScreenSpyHandler.deleteOldScreenshotsOnStartup();

    }
}
