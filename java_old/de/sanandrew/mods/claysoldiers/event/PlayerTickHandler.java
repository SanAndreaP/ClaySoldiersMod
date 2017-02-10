/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.relauncher.Side;
import de.sanandrew.mods.claysoldiers.util.CsmPlayerProperties;

public class PlayerTickHandler
{
    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event) {
        CsmPlayerProperties prop;
        if( event.side == Side.SERVER && event.phase == Phase.END && (prop = CsmPlayerProperties.get(event.player)) != null ) {
            prop.decrDisruptDelay();
        }
    }
}
