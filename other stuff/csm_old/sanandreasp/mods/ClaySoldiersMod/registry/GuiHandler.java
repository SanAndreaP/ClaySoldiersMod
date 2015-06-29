/*******************************************************************************************************************
* Name: Handlers.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.registry;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import sanandreasp.mods.ClaySoldiersMod.client.gui.GuiNexus;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayNexus;
import sanandreasp.mods.ClaySoldiersMod.inventory.ContainerNexus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		EntityClayNexus entitySrv = null;
		if (world instanceof WorldServer) entitySrv = (EntityClayNexus)((WorldServer)world).getEntityByID(x);
		if (entitySrv != null && ID == 0)
			return new ContainerNexus(player.inventory, entitySrv, ID == 0);
		else
			return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		EntityClayNexus entityClt = null;
		if (world instanceof WorldClient) entityClt = (EntityClayNexus)((WorldClient)world).getEntityByID(x);
		if (entityClt != null)
			return new GuiNexus(player, entityClt, ID == 0);
		else
			return null;
	}
	
//	@Override
//	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
//		byte packetID = packet.data[4];
//		
//		if (packetID == 2 && FMLCommonHandler.instance().getSide().isClient()) {
//			CSMModRegistry.proxy.spawnEntityParticles(packet.data, player);			
//		} else if (packetID == 3 && FMLCommonHandler.instance().getSide().isClient()) {
//			CSMModRegistry.proxy.swingSoldierArm(packet.data, player);
//		} else {
//			EntityClayNexus nexus = ((EntityClayNexus)((WorldServer)((EntityPlayer)player).worldObj).getEntityByID(getEntityIdFromByteArray(packet.data)));
//			
//			switch(packetID) {
//				case 0:
//					nexus.setWaveDurSec((int)packet.data[5] & 255);
//					nexus.setMaxSpwnSoldiers((int)packet.data[6] & 255);
//					nexus.setMaxLvngSoldiers((int)packet.data[7] & 255);
//					nexus.setChanceGetNone((int)packet.data[8] & 255);
//					nexus.setHealth((int)packet.data[9] & 255);
//					nexus.setSrvMaxHealth((int)packet.data[10] & 255);
//					break;
//				case 1:
//					nexus.setHealth((int)packet.data[5] & 255);
//					nexus.setDestroyed(packet.data[6] == (byte)1);
//					nexus.setRandItems(packet.data[7] == (byte)1);
//					break;
//			}
//		}
//	}
	
//	private int getEntityIdFromByteArray(byte... bt) {
//		int id = 0;
//		id |= (int)bt[0] & 255;
//		id |= ((int)bt[1] & 255) << 8;
//		id |= ((int)bt[2] & 255) << 16;
//		id |= ((int)bt[3] & 255) << 24;
//		return id;
//	}
}
