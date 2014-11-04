/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import cpw.mods.fml.common.registry.GameRegistry;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.claysoldiers.block.BlockClayNexus;
import de.sanandrew.mods.claysoldiers.tileentity.TileEntityClayNexus;
import net.minecraft.block.Block;

public final class RegistryBlocks
{
    public static Block clayNexus;

    public static void initialize() {
        clayNexus = new BlockClayNexus();

        clayNexus.setCreativeTab(ClaySoldiersMod.clayTab);
        clayNexus.setBlockName(ClaySoldiersMod.MOD_ID + ":nexus");
        GameRegistry.registerTileEntity(TileEntityClayNexus.class, ClaySoldiersMod.MOD_ID + ":nexus_te");

        SAPUtils.registerBlocks(clayNexus);
    }
}
