/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.effect;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;

public class EffectThunder
        extends ASoldierEffect
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierEffectInst effectInst) {
        effectInst.getNbtTag().setShort("ticksRemaining", (short) 20);
        effectInst.getNbtTag().setLong("randomLightning", SAPUtils.RNG.nextLong());
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierEffectInst effectInst) {
        short remaining = (short) (effectInst.getNbtTag().getShort("ticksRemaining") - 1);
        effectInst.getNbtTag().setShort("ticksRemaining", remaining);
        return remaining == 0;
    }

    @Override
    public void onClientUpdate(EntityClayMan clayMan, SoldierEffectInst effectInst) {
        short remaining = (short) (effectInst.getNbtTag().getShort("ticksRemaining") - 1);
        effectInst.getNbtTag().setShort("ticksRemaining", remaining);
    }

    @Override
    public boolean shouldNbtSyncToClient(EntityClayMan clayMan, SoldierEffectInst effectInst) {
        if( effectInst.getNbtTag().getBoolean("alreadySent") ) {
            return false;
        } else {
            effectInst.getNbtTag().setBoolean("alreadySent", true);
            return true;
        }
    }
}
