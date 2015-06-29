/*******************************************************************************************************************
 * Name:      EntityClayCam.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.client.entity;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.src.*;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

public class EntityClayCam extends EntityPlayer {
	
	public EntityClayCam(World world, Minecraft game) {
		super(world, "ClayCam");
		yOffset = -1.2F;
		setSize(0.4F, 0.4F);
//		username = "coolie";
//		skinUrl = "";
		noClip = true;
	}
	
//	@Override
//	public void updateCloak()
//    {
//    }
	
	public static void resetCam() {
		Minecraft game = Minecraft.getMinecraft();
		
		if (CSMModRegistry.prevPlayer != null) {
			game.gameSettings.hideGUI = true;
			game.gameSettings.thirdPersonView = 0;
			if (CSMModRegistry.prevPlayer.isSneaking()) {
				CSMModRegistry.proxy.cameraReset(game);
			} else if (game.renderViewEntity instanceof EntityPlayerSP && !(game.renderViewEntity instanceof EntityClayCam)) {
				CSMModRegistry.prevPlayer = (EntityPlayerSP)game.renderViewEntity;
				CSMModRegistry.proxy.cameraReset(game);
			}
		}
	}
	
	@Override
	public boolean isSneaking()
    {
        return false;
    }
	
	public EntityClayCam(World world, EntityLiving entityliving) {
		this(world, FMLClientHandler.instance().getClient());
		entityAnchor = entityliving;
		setPosition(entityAnchor.posX, entityAnchor.posY, entityAnchor.posZ);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float i) {
		return false;
	}
	
//	@Override
//	protected boolean canDespawn()
//    {
//        return false;
//    }
	
	@Override
	protected boolean canTriggerWalking()
    {
        return false;
    }
	
	@Override
	public void onLivingUpdate() {
	}
	
	@Override
	public void updateEntityActionState() {
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if (entityAnchor == null || entityAnchor.isDead || entityAnchor.getHealth() <= 0) {
			CSMModRegistry.proxy.cameraReset();
			setDead();
		} else {
			try {
				Entity jimmy = Minecraft.getMinecraft().renderViewEntity;
				if (jimmy != this) {
					CSMModRegistry.proxy.cameraReset();
					setDead();
				} else {
					yOffset = 1.1F;
					double angle = entityAnchor.renderYawOffset * gnarliness;
					double d1 = entityAnchor.posX - (Math.sin(angle) * 0.8D);//0.075D
					double d2 = entityAnchor.posZ - (Math.cos(angle) * 0.8D);
					setPosition(d1, entityAnchor.posY + 0.7D, d2);
//					faceEntity(entityAnchor, 180F, 180F);
				}
			} catch(NoSuchMethodError e) {
				
			}
		}
	}
	
	@Override
	public void addVelocity(double d, double d1, double d2)
    {
    }
	
	public static final double gnarliness = (-3.141593D / 180D);
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
    }

    @Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
    }
	
	public EntityLiving entityAnchor;

//	@Override
//	public void sendChatToPlayer(String var1) {
//		
//	}

	@Override
	public boolean canCommandSenderUseCommand(int var1, String var2) {
		return false;
	}

	@Override
	public ChunkCoordinates getPlayerCoordinates() {
		return null;
	}

    @Override
    public void sendChatToPlayer(ChatMessageComponent chatmessagecomponent) {
        
    }
}