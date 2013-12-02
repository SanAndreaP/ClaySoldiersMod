package sanandreasp.mods.ClaySoldiersMod.client.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import sanandreasp.mods.ClaySoldiersMod.packet.PacketBase;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;

import com.google.common.collect.Maps;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandlerClient implements IPacketHandler
{
	private Map<Integer, PacketBase> packetTypes = Maps.newHashMap();
	
	public PacketHandlerClient() {
		this.packetTypes.put(0x100, new PacketRecvSldUpgrades());
		this.packetTypes.put(0x101, new PacketRecvParticle());
	}

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player par3Player) {
        EntityPlayer player = (EntityPlayer)par3Player;
        try {
        	DataInputStream iStream = new DataInputStream(new ByteArrayInputStream(packet.data));
			this.packetTypes.get(iStream.readInt()).handle(iStream, player);
		} catch (IOException e) {
			FMLLog.log(CSMModRegistry.modID, Level.WARNING, e, "Failed to handle client-packet!");
		} catch (NullPointerException e) {
            FMLLog.log(CSMModRegistry.modID, Level.WARNING, e, "Failed to handle client-packet!");
		}
	}

}
