/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class RenderPlayerEventInst
{
    public float origOffset;

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
//        if( Minecraft.getMinecraft().renderViewEntity instanceof EntityClayMan ) {
//            if (event.phase == TickEvent.Phase.START) {
//                ((EntityClayMan) Minecraft.getMinecraft().renderViewEntity).yOffset = 5.5F;
//            } else {
//                ((EntityClayMan) Minecraft.getMinecraft().renderViewEntity).yOffset = 0.001F;
//            }
//        }
    }
}
