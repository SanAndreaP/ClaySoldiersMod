/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.mount;

import de.sanandrew.mods.claysoldiers.registry.mount.EnumClayHorseType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityPegasus
        extends EntityClayHorse
{
    public float wingSwing = 0.0F;
    public float prevWingSwing = 0.0F;

    public EntityPegasus(World world) {
        super(world);
    }

    public EntityPegasus(World world, EnumClayHorseType type, ItemStack doll) {
        super(world, type, doll);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        this.fallDistance = 0.0F;

        this.jumpMovementFactor = this.getAIMoveSpeed() * 0.216F;

        if( this.world.isRemote ) {
            this.calcWingSwing();
        }

        if( !this.onGround && this.motionY < 0.0D ) {
            this.motionY *= 0.6D;
        }

        if( this.rand.nextInt(100) >= 5 && this.ticksExisted % 10 == 0 && this.isBeingRidden() && (this.moveForward > 0.001D || this.moveForward < -0.001D) ) {
            if( !this.world.isAirBlock(new BlockPos(this.posX, this.posY - 1, this.posZ)) ) {
                this.motionY = 0.3D;
            }
        }
    }

    private void calcWingSwing() {
        this.prevWingSwing = this.wingSwing;

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
