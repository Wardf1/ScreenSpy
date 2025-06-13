package com.wardf.screenspy;

import net.minecraftforge.common.MinecraftForge;

public class ClientProxy {
    public void init() {
        MinecraftForge.EVENT_BUS.register(ScreenSpyHandler.class);
        MinecraftForge.EVENT_BUS.register(new GuiIngameMenuMixin()); // ← to musi być!
    }
}
