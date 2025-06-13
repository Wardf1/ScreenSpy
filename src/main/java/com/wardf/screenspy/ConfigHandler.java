
package com.wardf.screenspy;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class ConfigHandler {
    public static int retentionSeconds = 180;

    public static void loadConfig(FMLPreInitializationEvent event) {
        File configFile = new File(event.getModConfigurationDirectory(), "screenspy.cfg");
        Configuration config = new Configuration(configFile);
        config.load();

        retentionSeconds = config.getInt("retentionSeconds", "general", 180, 10, 600, "Czas w sekundach, ile screenów ma być trzymanych");

        if (config.hasChanged()) config.save();
    }
}
