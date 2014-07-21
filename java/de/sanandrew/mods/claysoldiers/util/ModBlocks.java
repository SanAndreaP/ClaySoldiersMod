/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import cpw.mods.fml.common.registry.GameRegistry;
import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.claysoldiers.block.BlockClayNexus;
import de.sanandrew.mods.claysoldiers.tileentity.TileEntityClayNexus;
import net.minecraft.block.Block;

public class ModBlocks
{
    public static Block clayNexus = new BlockClayNexus();


    public static void register() {
        clayNexus.setCreativeTab(CSM_Main.clayTab);
        clayNexus.setBlockName(CSM_Main.MOD_ID + ":nexus");
        GameRegistry.registerTileEntity(TileEntityClayNexus.class, CSM_Main.MOD_ID + ":nexus_te");

        SAPUtils.registerBlocks(clayNexus);
    }
}
