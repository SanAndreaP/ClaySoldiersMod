/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderWorldEventHandler
{
    public static float partTicks;

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        partTicks = event.getPartialTicks();
    }
}
