package com.wardf.screenspy;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiIngameMenuMixin {
    @SubscribeEvent
    public void onInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiIngameMenu) {
            event.getButtonList().add(new GuiButton(9999, 10, 10, 100, 20,
                    "ScreenSpy: " + (ScreenSpyHandler.isEnabled() ? "ON" : "OFF")));
        }
    }

    @SubscribeEvent
    public void onActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.getGui() instanceof GuiIngameMenu && event.getButton().id == 9999) {
            ScreenSpyHandler.toggleEnabled();
            event.getButton().displayString = "ScreenSpy: " + (ScreenSpyHandler.isEnabled() ? "ON" : "OFF");
        }
    }
}

