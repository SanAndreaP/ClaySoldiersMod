/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.config;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.util.CsmConfiguration;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiConfigScreen
        extends GuiConfig
{
    public GuiConfigScreen(GuiScreen parentScreen) {
        super(parentScreen, getCfgElements(), CsmConstants.ID, "configuration", false, false, "Clay Soldiers Configuration");
    }

    @SuppressWarnings("serial")
    private static List<IConfigElement> getCfgElements() {
        return new ArrayList<IConfigElement>() {{
            this.add(new ConfigElement(CsmConfiguration.getCategory(CsmConfiguration.CAT_ENTITY_VALS)));
        }};
    }
}
