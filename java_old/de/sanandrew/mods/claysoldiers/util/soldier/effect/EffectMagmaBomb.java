/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.effect;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.ParticlePacketSender;
import de.sanandrew.mods.claysoldiers.util.soldier.EnumMethodState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;

public class EffectMagmaBomb
        extends ASoldierEffect
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierEffectInst effectInst) {
        effectInst.getNbtTag().setByte("ticksRemain", (byte) 40);
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierEffectInst effectInst) {
        byte ticksRemain = effectInst.getNbtTag().getByte("ticksRemain");
        effectInst.getNbtTag().setByte("ticksRemain", --ticksRemain);

        if( ticksRemain == 0 ) {
            Explosion explosion = clayMan.worldObj.createExplosion(clayMan, clayMan.posX, clayMan.posY, clayMan.posZ, 1.0F, false);
            clayMan.attackEntityFrom(DamageSource.setExplosionSource(explosion), 10000.0F);
        }

        return false;
    }

    @Override
    public EnumMethodState onTargeting(EntityClayMan clayMan, SoldierEffectInst effectInst, EntityClayMan target) {
        return EnumMethodState.DENY;
    }

    @Override
    public void onClientUpdate(EntityClayMan clayMan, SoldierEffectInst effectInst) {
        ParticlePacketSender.sendMagmafuseFx(clayMan.posX, clayMan.posY + 0.2D, clayMan.posZ, clayMan.dimension);
    }

    @Override
    public void onSoldierDeath(EntityClayMan clayMan, SoldierEffectInst effectInst, DamageSource source) {
        if( !(source.getEntity() instanceof EntityPlayer) ) {
            clayMan.worldObj.createExplosion(clayMan, clayMan.posX, clayMan.posY, clayMan.posZ, 1.0F, false);
        }
    }
}
