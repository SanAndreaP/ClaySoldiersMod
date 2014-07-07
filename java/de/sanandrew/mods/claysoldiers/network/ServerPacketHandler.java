package de.sanandrew.mods.claysoldiers.network;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import net.minecraft.network.NetHandlerPlayServer;

import java.io.IOException;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class ServerPacketHandler
{
    @SubscribeEvent
    public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event) throws IOException {
        NetHandlerPlayServer netHandlerPlayServer = (NetHandlerPlayServer)event.handler;

        if( event.packet.channel().equals(CSM_Main.MOD_CHANNEL) ) {
            PacketProcessor.processPacket(event.packet.payload(), event.packet.getTarget(), netHandlerPlayServer);
        }
    }
}
