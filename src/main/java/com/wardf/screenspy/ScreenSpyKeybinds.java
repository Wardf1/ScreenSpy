package com.wardf.screenspy;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class ScreenSpyKeybinds {
    public static final KeyBinding TOGGLE = new KeyBinding(
            "key.screenspy.toggle",
            Keyboard.KEY_F9,
            "key.categories.screenspy"
    );

    public static void register() {
        ClientRegistry.registerKeyBinding(TOGGLE);
    }
}
