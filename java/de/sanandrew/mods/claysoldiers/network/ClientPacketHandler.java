package de.sanandrew.mods.claysoldiers.network;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import net.minecraft.client.network.NetHandlerPlayClient;

import java.io.IOException;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class ClientPacketHandler
    extends ServerPacketHandler
{
    @SubscribeEvent
    public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent event) throws IOException {
        NetHandlerPlayClient netHandlerPlayClient = (NetHandlerPlayClient)event.handler;

        if( event.packet.channel().equals(CSM_Main.MOD_CHANNEL) ) {
            PacketProcessor.processPacket(event.packet.payload(), event.packet.getTarget(), netHandlerPlayClient);
        }
    }
}
