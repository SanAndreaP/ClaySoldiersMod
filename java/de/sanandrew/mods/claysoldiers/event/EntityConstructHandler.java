/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.claysoldiers.util.CsmPlayerProperties;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

public class EntityConstructHandler
{
    @SubscribeEvent
    public void onEntityConstruct(EntityConstructing event) {
        if( event.entity instanceof EntityPlayer && CsmPlayerProperties.get((EntityPlayer) event.entity) == null ) {
            CsmPlayerProperties.register((EntityPlayer) event.entity);
        }
    }
}
