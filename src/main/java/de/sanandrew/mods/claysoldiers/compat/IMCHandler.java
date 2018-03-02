/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.compat;

import net.minecraftforge.fml.common.event.FMLInterModComms;

public class IMCHandler
{
    public static void sendIMC() {
        FMLInterModComms.sendMessage("waila", "register", "de.sanandrew.mods.claysoldiers.compat.waila.WailaEntityProvider.register");
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "de.sanandrew.mods.claysoldiers.compat.top.TOPProvider");
    }
}
