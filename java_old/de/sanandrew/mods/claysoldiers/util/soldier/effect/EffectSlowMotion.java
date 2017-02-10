/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.effect;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.ParticlePacketSender;
import org.apache.commons.lang3.mutable.MutableFloat;

public class EffectSlowMotion
        extends ASoldierEffect
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierEffectInst effectInst) {
        effectInst.getNbtTag().setShort("ticksRemaining", (short) 60);
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierEffectInst effectInst) {
        if( clayMan.ticksExisted % 10 == 0 ) {
            ParticlePacketSender.sendSpellFx(clayMan.posX, clayMan.posY, clayMan.posZ, clayMan.dimension, 1.0D, 1.0D, 1.0D);
        }

        short remaining = (short) (effectInst.getNbtTag().getShort("ticksRemaining") - 1);
        effectInst.getNbtTag().setShort("ticksRemaining", remaining);
        return remaining == 0;
    }

    @Override
    public void getAiMoveSpeed(EntityClayMan clayMan, SoldierEffectInst effectInst, MutableFloat speed) {
        speed.setValue(speed.getValue() / 2.0F);
    }
}
