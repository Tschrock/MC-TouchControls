package net.tschrock.minecraft.touchcontrols;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class TouchControlsModConfigGUI extends GuiConfig {
    public TouchControlsModConfigGUI(GuiScreen parent) {
        super(parent,
                new ConfigElement(TouchControlsMod.configFile.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                "touchcontrols", false, false, GuiConfig.getAbridgedConfigPath(TouchControlsMod.configFile.toString()));
    }
}
