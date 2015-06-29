/*******************************************************************************************************************
 * Name:      EntityPegasus.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.entity.mount;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.src.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityPegasus extends EntityHorse {
    public float sinage;
	
	public EntityPegasus(World world) {
		super(world);
		moveSpeed = 0.5F;
		sinage = rand.nextFloat();
	}
	
	public EntityPegasus(World world, double x, double y, double z, int i) {
		super(world, x, y, z, i);
		moveSpeed = 0.5F;
		sinage = rand.nextFloat();
	}
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(40.0D);
    }

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if (!onGround) {
			sinage += 0.75F;
		} else {
			sinage += 0.15F;
		}
		
		if (sinage > 3.141593F * 2F) {
			sinage -= (3.141593F * 2F);
		}
	}
	
	@Override
	protected void fall(float f) {
    }
	
	@Override
	public void updateEntityActionState() {
		super.updateEntityActionState();
		
		boolean plunge = false;
		if (!onGround && riddenByEntity != null && riddenByEntity instanceof EntityClayMan) {
			EntityClayMan ec = (EntityClayMan)riddenByEntity;
			if (!ec.isDead) {
				if (ec.getFollowEntity() != null) {
					double a = ec.posX - ec.getFollowEntity().posX;
					double b = ec.posZ - ec.getFollowEntity().posZ;
					double d = Math.sqrt((a * a) + (b * b));
					
					if (d <= 1.75D && ec.boundingBox.minY > (ec.getFollowEntity().posY - 1.0D)) {
						plunge = true;
					}
				} if (ec.getEntityToAttack() != null) {
					double a = ec.posX - ec.getEntityToAttack().posX;
					double b = ec.posZ - ec.getEntityToAttack().posZ;
					double d = Math.sqrt((a * a) + (b * b));
					
					if (d <= 1.75D && ec.boundingBox.minY > (ec.getEntityToAttack().posY - 1.0D)) {
						plunge = true;
					}
				}
			}
		}
	
		if (riddenByEntity != null) {
			riddenByEntity.fallDistance = 0.0F;
			if (moveForward != 0.0F && !plunge) {
				if (onGround) {
					isJumping = true;
				} else {
					int j = MathHelper.floor_double(posX);
					int i1 = MathHelper.floor_double(boundingBox.minY);
					int k1 = MathHelper.floor_double(boundingBox.minY - 0.5D);
					int l1 = MathHelper.floor_double(posZ);
					if ((worldObj.getBlockId(j, i1 - 1, l1) != 0 || worldObj.getBlockId(j, k1 - 1, l1) != 0) && worldObj.getBlockId(j, i1 + 2, l1) == 0 && worldObj.getBlockId(j, i1 + 1, l1) == 0) {
						motionY = 0.2D;
					}
				}
			}
		}
		
		if (!onGround && motionY < -0.1D) {
			motionY = -0.1D;
		}
    }
	
	@Override
	protected void dropFewItems(boolean flag, int i) {
		Item item1 = CSMModRegistry.pegasusDoll;
		dropItem(item1.itemID, 1, this.getType());
	}
	
	@Override
	public boolean isOnLadder() {
		return isCollidedHorizontally && !onGround;
	}
}