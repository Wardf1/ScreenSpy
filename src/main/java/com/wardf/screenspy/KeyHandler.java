package com.wardf.screenspy;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class KeyHandler {
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || Minecraft.getMinecraft().player == null) return;

        if (ScreenSpyKeybinds.TOGGLE.isPressed()) {
            ScreenSpyHandler.toggleEnabled();
            Minecraft.getMinecraft().player.sendMessage(
                    new TextComponentString("§6[ScreenSpy] §fTryb: " + (ScreenSpyHandler.isEnabled() ? "§aWŁĄCZONY" : "§cWYŁĄCZONY"))
            );
        }
    }
}
