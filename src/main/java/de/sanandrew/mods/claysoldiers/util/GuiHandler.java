/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.mods.claysoldiers.client.util.ClientProxy;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiHandler
        implements IGuiHandler
{
    public static final int GUI_LEXICON = 0;

    public static final GuiHandler INSTANCE = new GuiHandler();

    private GuiHandler() { }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return ClaySoldiersMod.proxy.getClientGui(id, player, world, x, y, z);
    }

    @SideOnly(Side.CLIENT)
    public Gui getClientGui(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch( id ) {
            case GUI_LEXICON:
                return ClientProxy.lexiconInstance.getGui();
            default:
                return null;
        }
    }
}
