/*******************************************************************************************************************
* Name: CommonProxy.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.registry;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public boolean isClient() {
		return false;
	}
	
	public void registerRenderInformation() { }
	
	public void showEffect(World worldObj, Entity entity, int particleFX) {
		if (!worldObj.isRemote && worldObj instanceof WorldServer) {
			byte[] entityID = getEntityIdAsByteArray(entity);
			byte[] packetData = new byte[] {
					entityID[0],
					entityID[1],
					entityID[2],
					entityID[3],
					(byte) 2,
					(byte) particleFX
			};
			Packet250CustomPayload packet = new Packet250CustomPayload("ClaySoldiers", packetData);
			PacketDispatcher.sendPacketToAllInDimension(packet, worldObj.provider.dimensionId);
		}
	}
	
	public void swingSoldierArm(World worldObj, EntityClayMan entity) {
		if (!worldObj.isRemote && worldObj instanceof WorldServer) {
			byte[] entityID = getEntityIdAsByteArray(entity);
			byte[] packetData = new byte[] {
					entityID[0],
					entityID[1],
					entityID[2],
					entityID[3],
					(byte) 3
			};
			Packet250CustomPayload packet = new Packet250CustomPayload("ClaySoldiers", packetData);
			PacketDispatcher.sendPacketToAllInDimension(packet, worldObj.provider.dimensionId);
		}
	}
	
	public void spawnEntityParticles(byte[] data, Player player) {  }
	
	public void swingSoldierArm(byte[] data, Player player) {  }
	
	public void spawnBlockParticles(int ID, Object[] data) {  }
    
    private byte[] getEntityIdAsByteArray(Entity entity) {
    	return new byte[] {
    			(byte)(entity.entityId & 255),
    			(byte)(((entity.entityId) >> 8) & 255),
    			(byte)(((entity.entityId) >> 16) & 255),
    			(byte)(((entity.entityId) >> 24) & 255)
    	};
    }
	
	public World getClientWorld() {
		return null;
	}

	public void cameraReset() {
	}
	
	public void cameraReset(Object game) {
	}

	public void sendToServer(Packet packet) {  }
}
