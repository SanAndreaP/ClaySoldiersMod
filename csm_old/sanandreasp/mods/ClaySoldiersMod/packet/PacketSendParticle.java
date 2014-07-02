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

public class PacketSendParticle {
    
	public static void sendParticle(int i, int dimID, double posX, double posY, double posZ, Double dataI, Double dataII, Double dataIII) {
		ByteArrayOutputStream bos = null;
		DataOutputStream dos = null;
		try {
			bos = new ByteArrayOutputStream();
			dos = new DataOutputStream(bos);
			
			dos.writeInt(0x101);
			dos.writeShort((short) i);
			dos.writeFloat((float) posX);
			dos.writeFloat((float) posY);
			dos.writeFloat((float) posZ);
			
			if( dataI != null ) {
			    dos.writeDouble(dataI.doubleValue());
    			if( dataII != null ){
    			    dos.writeDouble(dataII.doubleValue());
    			    if( dataIII != null ) dos.writeDouble(dataIII.doubleValue());
    			}
			}
			
			PacketDispatcher.sendPacketToAllAround(posX, posY, posZ, 128D, dimID, new Packet250CustomPayload(CSMModRegistry.channelID, bos.toByteArray()));
			
			dos.close();
			bos.close();
		} catch(IOException e) {
			FMLLog.log(CSMModRegistry.modID, Level.WARNING, e, "Failed to send packet PacketSendParticle to players!");
		}
	}
}
