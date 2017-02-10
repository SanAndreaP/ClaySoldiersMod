/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.mount;

import de.sanandrew.core.manpack.util.annotation.UsedByReflection;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.mount.EnumHorseType;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityPegasusMount
        extends EntityHorseMount
{
    public float wingSwing = 0.0F;
    public float wingSwingStep = 0.0F;
    public float prevWingSwing = 0.0F;

    @UsedByReflection
    public EntityPegasusMount(World world) {
        super(world);
        this.wingSwing = this.rand.nextFloat() * (float) Math.PI;
    }

    public EntityPegasusMount(World world, EnumHorseType horseType) {
        super(world, horseType);
        this.wingSwing = this.rand.nextFloat() * (float) Math.PI;
    }

    @Override
    public void onUpdate() {
        if( this.worldObj.isRemote ) {
            this.calcWingSwing();
        }

        this.jumpMovementFactor = this.getAIMoveSpeed() * (0.16277136F / (0.91F * 0.91F * 0.91F));

        this.fallDistance = 0.0F;

        if( this.motionY < -0.1D ) {
            this.motionY = -0.1D;
        }

        if( this.riddenByEntity instanceof EntityClayMan && this.moveForward != 0.0F ) {
            EntityClayMan rider = (EntityClayMan) this.riddenByEntity;
            double dist = Double.MAX_VALUE;

            if( rider.getEntityToAttack() != null ) {
                dist = this.getDistanceSqToEntity(rider.getEntityToAttack());
            } else if( rider.getTargetFollowing() != null ) {
                dist = this.getDistanceSqToEntity(rider.getTargetFollowing());
            }

            if( dist > 2.25D ) {
                if( this.onGround ) {
                    this.motionY = 0.4D;
                    this.isAirBorne = true;
                } else {
                    int[] blockPos = new int[] { (int) posX, (int) posY - 1, (int) posZ };
                    Block blockBelow = this.worldObj.getBlock(blockPos[0], blockPos[1], blockPos[2]);
                    if( blockBelow != null && !blockBelow.isAir(this.worldObj, blockPos[0], blockPos[1], blockPos[2]) ) {
                        AxisAlignedBB aabb = blockBelow.getCollisionBoundingBoxFromPool(this.worldObj, blockPos[0], blockPos[1], blockPos[2]);
                        if( aabb == null || aabb.maxY > (this.posY - 1.0D) ) {
                            this.motionY = 0.2D;
                            this.isAirBorne = true;
                        }
                    }
                }
            }
        }

        super.onUpdate();
    }

    @Override
    protected void fall(float fallHeight) {
    }

    private void calcWingSwing() {
        this.prevWingSwing = this.wingSwing;
        this.wingSwingStep = this.wingSwing;

        if( this.onGround ) {
            this.wingSwing += Math.PI / 16.0F;
        } else {
            this.wingSwing += Math.PI / 4.0F;
        }

        if( this.wingSwing > Math.PI * 2.0F ) {
            this.wingSwing = 0;
            this.prevWingSwing = 0;
        }
    }
}
