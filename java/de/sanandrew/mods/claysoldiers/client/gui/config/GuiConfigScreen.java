/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.config;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.ModConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

@SideOnly(Side.CLIENT)
public class GuiConfigScreen
        extends GuiConfig
{
    @SuppressWarnings("unchecked")
    public GuiConfigScreen(GuiScreen parent) {
        super(parent, new ConfigElement(ModConfig.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
              ClaySoldiersMod.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ModConfig.config.toString())
        );
    }
}
