/*******************************************************************************************************************
 * Name:      ClientProxy.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.client.registry;
import java.util.Random;

import sanandreasp.mods.ClaySoldiersMod.client.EntityNexusFX;
import sanandreasp.mods.ClaySoldiersMod.client.model.ModelBunny;
import sanandreasp.mods.ClaySoldiersMod.client.model.ModelClayMan;
import sanandreasp.mods.ClaySoldiersMod.client.model.ModelClayNexus;
import sanandreasp.mods.ClaySoldiersMod.client.model.ModelGecko;
import sanandreasp.mods.ClaySoldiersMod.client.model.ModelHorse;
import sanandreasp.mods.ClaySoldiersMod.client.model.ModelPegasus;
import sanandreasp.mods.ClaySoldiersMod.client.model.ModelTurtle;
import sanandreasp.mods.ClaySoldiersMod.client.render.RenderBunny;
import sanandreasp.mods.ClaySoldiersMod.client.render.RenderClayMan;
import sanandreasp.mods.ClaySoldiersMod.client.render.RenderClayNexus;
import sanandreasp.mods.ClaySoldiersMod.client.render.RenderGecko;
import sanandreasp.mods.ClaySoldiersMod.client.render.RenderGravelChunk;
import sanandreasp.mods.ClaySoldiersMod.client.render.RenderHorse;
import sanandreasp.mods.ClaySoldiersMod.client.render.RenderPegasus;
import sanandreasp.mods.ClaySoldiersMod.client.render.RenderSnowball;
import sanandreasp.mods.ClaySoldiersMod.client.render.RenderTurtle;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayNexus;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityBunny;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityGecko;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityHorse;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityPegasus;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntitySnowball;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityTurtle;
import sanandreasp.mods.ClaySoldiersMod.entity.projectile.EntityFireball;
import sanandreasp.mods.ClaySoldiersMod.entity.projectile.EntityGravelChunk;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import sanandreasp.mods.ClaySoldiersMod.registry.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityCrit2FX;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityNoteFX;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.network.packet.Packet;
import net.minecraft.src.*;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class ClientProxy extends CommonProxy {
		
	@Override
	public void registerRenderInformation() {
		RenderingRegistry.registerEntityRenderingHandler(EntityClayMan.class, new RenderClayMan(new ModelClayMan(0F, 13F), 0.125F));
		RenderingRegistry.registerEntityRenderingHandler(EntityGravelChunk.class, new RenderGravelChunk());
		RenderingRegistry.registerEntityRenderingHandler(EntitySnowball.class, new RenderSnowball(Item.snowball));
		RenderingRegistry.registerEntityRenderingHandler(EntityFireball.class, new RenderSnowball(Item.fireballCharge));
		RenderingRegistry.registerEntityRenderingHandler(EntityHorse.class, new RenderHorse(new ModelHorse(0F, 12.75F), 0.15F));
		RenderingRegistry.registerEntityRenderingHandler(EntityPegasus.class, new RenderPegasus(new ModelPegasus(0F, 12.75F), 0.15F));
		RenderingRegistry.registerEntityRenderingHandler(EntityBunny.class, new RenderBunny(new ModelBunny(), 0.15F));
		RenderingRegistry.registerEntityRenderingHandler(EntityTurtle.class, new RenderTurtle(new ModelTurtle(0F, 12.75F), 0.15F));
		RenderingRegistry.registerEntityRenderingHandler(EntityGecko.class, new RenderGecko(new ModelGecko(), 0.15F));
		RenderingRegistry.registerEntityRenderingHandler(EntityClayNexus.class, new RenderClayNexus(new ModelClayNexus(), 0.15F));
	}
	
	@Override
	public World getClientWorld() {
		return Minecraft.getMinecraft().theWorld;
	}
	
	@Override
	public boolean isClient() {
		return true;
	}
	
	@Override
	public void spawnBlockParticles(int ID, Object[] data) {
		WorldClient world = (WorldClient) this.getClientWorld();
		Random rand = new Random();
		Minecraft mc = Minecraft.getMinecraft();
		
		switch(ID) {
			case 0:
              mc.effectRenderer.addEffect(new EntityDiggingFX(CSMModRegistry.proxy.getClientWorld(), (Double)data[0], (Double)data[1], (Double)data[2], (Double)data[0] - (Double)data[3] - 0.5D, (Double)data[1] - (Double)data[4] - 0.5D, (Double)data[2] - (Double)data[5] - 0.5D, (Block)data[6], rand.nextInt(6), (Integer)data[7]));
              break;
		}
	}

	@Override
	public void swingSoldierArm(byte[] data, Player player) {
		WorldClient world = (WorldClient)((EntityPlayer)player).worldObj;
		Entity entity = world.getEntityByID(getEntityIdFromByteArray(data));
		if (entity != null && entity instanceof EntityClayMan)
			((EntityClayMan)entity).swingArm();
	}
	
	@Override
	public void spawnEntityParticles(byte[] data, Player player) {
		WorldClient world = (WorldClient)((EntityPlayer)player).worldObj;
		Entity entity = world.getEntityByID(getEntityIdFromByteArray(data));
		Random rand = new Random();
		Minecraft mc = Minecraft.getMinecraft();
		
		if (entity == null) return;
		
		switch(data[5]) {
			case 0:
				mc.effectRenderer.addEffect(new EntityNoteFX(world, entity.posX, entity.posY + 0.5D, entity.posZ, rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
				break;
			case 1:
				Vec3 vec3d = Vec3.createVectorHelper((rand.nextFloat() - 0.5D) * 0.10000000000000001D, Math.random() * 0.10000000000000001D + 0.10000000000000001D, 0.0D);
	            vec3d.rotateAroundX((-entity.rotationPitch * 3.141593F) / 180F);
	            vec3d.rotateAroundY((-entity.rotationYaw * 3.141593F) / 180F);
	            Vec3 vec3d1 = Vec3.createVectorHelper((rand.nextFloat() - 0.5D) * 0.29999999999999999D, (-rand.nextFloat()) * 0.59999999999999998D - 0.29999999999999999D, 0.59999999999999998D);
	            vec3d1.rotateAroundX((-entity.rotationPitch * 3.141593F) / 180F);
	            vec3d1.rotateAroundY((-entity.rotationYaw * 3.141593F) / 180F);
	            vec3d1 = vec3d1.addVector(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
                world.spawnParticle((new StringBuilder()).append("iconcrack_").append(Item.porkRaw.itemID).toString(), vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, vec3d.xCoord, vec3d.yCoord + 0.050000000000000003D, vec3d.zCoord);
                break;
			case 2:
//				int color = 0;
//				if (entity instanceof EntityClayMan)
//					color = ((EntityClayMan)entity).teamCloth(((EntityClayMan)entity).getClayTeam());
				
				double a = entity.posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				double b = entity.boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.25D);
				double c = entity.posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
//				
//				mc.effectRenderer.addEffect((new EntityDiggingFX(world, a, b, c, 0.0D, 0.0D, 0.0D, Block.cloth, 0, color)));
				break;
			case 3:
				a = entity.posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				b = entity.boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.25D);
				c = entity.posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				
				mc.effectRenderer.addEffect((new EntityDiggingFX(world, a, b, c, 0.0D, 0.0D, 0.0D, Block.cloth, 0, 0)));
				break;
			case 4:
				a = entity.posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				b = entity.boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.25D);
				c = entity.posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				mc.effectRenderer.addEffect(new EntityDiggingFX(world, a, b, c, 0.0D, 0.0D, 0.0D, Block.glowStone, 0, 0));
				break;
			case 5:
				a = entity.posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				b = entity.boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.25D);
				c = entity.posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				mc.effectRenderer.addEffect(new EntityDiggingFX(world, a, b, c, 0.0D, 0.0D, 0.0D, Block.planks, 0, 0));
				break;
			case 6:
				a = entity.posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				b = entity.boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.25D);
				c = entity.posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				mc.effectRenderer.addEffect(new EntityDiggingFX(world, a, b, c, 0.0D, 0.0D, 0.0D, Block.blockIron, 0, 0));
				break;
			case 7:
				a = entity.posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				b = entity.boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.25D);
				c = entity.posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				mc.effectRenderer.addEffect(new EntityDiggingFX(world, a, b, c, 0.0D, 0.0D, 0.0D, Block.glass, 0, 0));
				break;
			case 8:
				a = entity.posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				b = entity.boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.25D);
				c = entity.posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				mc.effectRenderer.addEffect(new EntityDiggingFX(world, a, b, c, 0.0D, 0.0D, 0.0D, Block.blockClay, 0, 0));
				break;
			case 9:
				mc.effectRenderer.addEffect(new EntityCrit2FX(world, entity));
				break;
			case 10:
				EntityClayNexus nexus = (EntityClayNexus)entity;
				mc.effectRenderer.addEffect(new EntityNexusFX(world, nexus.posX + (rand.nextDouble() - 0.5D) * nexus.width, (nexus.posY + rand.nextDouble() * nexus.height) - 0.25D, nexus.posZ + (rand.nextDouble() - 0.5D) * nexus.width, (rand.nextDouble() - 0.5D) * 2D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2D, nexus.getTeamColor()[0], nexus.getTeamColor()[1], nexus.getTeamColor()[2]));
				break;
			case 11:
				for (int var1 = 0; var1 < 20; ++var1)
		        {
		            double var2 = rand.nextGaussian() * 0.02D;
		            double var4 = rand.nextGaussian() * 0.02D;
		            double var6 = rand.nextGaussian() * 0.02D;
		            double var8 = 10.0D;
		            world.spawnParticle("explode", entity.posX + (double)(rand.nextFloat() * entity.width * 2.0F) - (double)entity.width - var2 * var8, entity.posY + (double)(rand.nextFloat() * entity.height) - var4 * var8, entity.posZ + (double)(rand.nextFloat() * entity.width * 2.0F) - (double)entity.width - var6 * var8, var2, var4, var6);
		        }
				break;
			case 12:
				a = entity.posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				b = entity.boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.25D);
				c = entity.posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				mc.effectRenderer.addEffect(new EntityDiggingFX(CSMModRegistry.proxy.getClientWorld(), a, b, c, 0.0D, 0.0D, 0.0D, Block.gravel, 0, 0));
				break;
			case 13:
				a = entity.posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				b = entity.boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.25D);
				c = entity.posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
				mc.effectRenderer.addEffect(new EntityDiggingFX(CSMModRegistry.proxy.getClientWorld(), a, b, c, 0.0D, 0.0D, 0.0D, Block.dirt, 0, 0));
				break;
			case 14:
				world.spawnParticle("reddust", entity.posX, entity.posY+0.5F, entity.posZ, -0.4F, 1F, 0F);
				break;
			case 15:
				world.spawnParticle("reddust", entity.posX, entity.posY+0.5F, entity.posZ, -1F, 0F, 0F);
				break;
			case 16:
				for (int j = 0; j < 4; j++) {
					a = entity.posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
					b = entity.boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
					c = entity.posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
					world.spawnParticle("reddust", a, b, c, 0.0D, 0.8D, 0.0D);
				}
				break;
			case 17:
				for (int j = 0; j < 4; j++) {
					a = entity.posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
					b = entity.boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
					c = entity.posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
					world.spawnParticle("reddust", a, b, c, 0.0D, 0.1D, 0.0D);
				}
				break;
			case 18:
				world.spawnParticle("flame", entity.posX, entity.posY+0.1D, entity.posZ, 0.0D, 0.01D, 0.0D);
				break;
			case 19:
				world.spawnParticle("heart", entity.posX, entity.posY+0.1D, entity.posZ, 0.0D, 0.01D, 0.0D);
				break;
			case 20:
				for (int j = 0; j < 4; j++) {
					a = entity.posX + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
					b = entity.boundingBox.minY + 0.125D + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
					c = entity.posZ + ((rand.nextFloat() - rand.nextFloat()) * 0.125D);
					world.spawnParticle("slime", a, b, c, 0.0D, 0.1D, 0.0D);
				}
				break;
			case 21:
				world.spawnParticle("portal", entity.posX + (rand.nextDouble() - 0.5D) * entity.width * 0.5D, (entity.posY + rand.nextDouble() * entity.height) - 0.05D, entity.posZ + (rand.nextDouble() - 0.5D) * entity.width * 0.5D, (rand.nextDouble() - 0.5D) * 0.75D, -rand.nextDouble() * 0.5D, (rand.nextDouble() - 0.5D) * 0.75D);
				break;
		}
	}
	
	private int getEntityIdFromByteArray(byte... bt) {
		int id = 0;
		id |= (int)bt[0] & 255;
		id |= ((int)bt[1] & 255) << 8;
		id |= ((int)bt[2] & 255) << 16;
		id |= ((int)bt[3] & 255) << 24;
		return id;
	}
	
	@Override
	public void cameraReset() {
		cameraReset(FMLClientHandler.instance().getClient());
	}
	
	@Override
	public void cameraReset(Object par1Game) {
		Minecraft game = null;
		if (par1Game instanceof Minecraft) {
			game = (Minecraft)par1Game;
		} else return;
		
		if (CSMModRegistry.prevPlayer != null) {
			game.renderViewEntity = CSMModRegistry.prevPlayer;
			CSMModRegistry.prevPlayer = null;
			game.gameSettings.hideGUI = CSMModRegistry.showTheHUD;
			game.gameSettings.thirdPersonView = (CSMModRegistry.showTheGUY ? 0 : 1);
			CSMModRegistry.claycam.setDead();
			for (int i = 0; i < game.theWorld.playerEntities.size(); i++) {
				if (game.theWorld.playerEntities.get(i).equals(CSMModRegistry.claycam))
					game.theWorld.playerEntities.remove(i);
			}
			CSMModRegistry.claycam = null;
		}
	}
	
	@Override
	public void sendToServer(Packet packet) {
		PacketDispatcher.sendPacketToServer(packet);
	}
}