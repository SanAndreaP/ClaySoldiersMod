/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.config;

import cpw.mods.fml.client.config.GuiConfig;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import de.sanandrew.mods.claysoldiers.util.ModConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

public class GuiConfigScreen
    extends GuiConfig
{
    @SuppressWarnings("unchecked")
    public GuiConfigScreen(GuiScreen parent) {
        super(parent, new ConfigElement(ModConfig.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
              CSM_Main.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ModConfig.config.toString()));
    }
}
