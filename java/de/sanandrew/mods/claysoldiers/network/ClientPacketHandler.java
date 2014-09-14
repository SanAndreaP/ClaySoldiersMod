/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import net.minecraft.client.network.NetHandlerPlayClient;

import java.io.IOException;

public class ClientPacketHandler
        extends ServerPacketHandler
{
    @SubscribeEvent
    public void onClientPacket(ClientCustomPacketEvent event) throws IOException {
        NetHandlerPlayClient netHandlerPlayClient = (NetHandlerPlayClient) event.handler;

        if( event.packet.channel().equals(CSM_Main.MOD_CHANNEL) ) {
            PacketProcessor.processPacket(event.packet.payload(), event.packet.getTarget(), netHandlerPlayClient);
        }
    }
}
