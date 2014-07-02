package sanandreasp.mods.ClaySoldiersMod.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import net.minecraft.network.packet.Packet250CustomPayload;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.PacketDispatcher;

public class PacketSendSldUpgrades {
	public static void sendUpgrades(IUpgradeEntity soldier) {
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x100);
			dos.writeInt(soldier.getEntity().entityId);
			for( int id : soldier.getUpgrades() )
				dos.writeInt(id);
			
			PacketDispatcher.sendPacketToAllPlayers(new Packet250CustomPayload(CSMModRegistry.channelID, bos.toByteArray()));
			
			dos.close();
			bos.close();
		} catch(IOException e) {
			FMLLog.log(CSMModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketRecvSldUpgrades to players!");
		}
	}
	
	public static void requestUpgrades(EntityClayMan soldier) {
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x000);
			dos.writeInt(soldier.entityId);
			
			PacketDispatcher.sendPacketToServer(new Packet250CustomPayload(CSMModRegistry.channelID, bos.toByteArray()));
			
			dos.close();
			bos.close();
		} catch(IOException e) {
			FMLLog.log(CSMModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketRecvSldUpgrades to players!");
		}
	}
}
