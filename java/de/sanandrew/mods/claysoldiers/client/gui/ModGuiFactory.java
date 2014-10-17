/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.client.gui.config.GuiConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.Set;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class ModGuiFactory
        implements IModGuiFactory
{
    @Override
    public void initialize(Minecraft minecraftInstance) {
    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return GuiConfigScreen.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }
}
