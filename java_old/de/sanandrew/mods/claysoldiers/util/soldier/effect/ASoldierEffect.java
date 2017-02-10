/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.effect;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.EnumMethodState;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

public abstract class ASoldierEffect
{
    /**
     * Fired when an instance for this effect is created. Use this to initiate all needed values in the NBT provided by the instance
     *
     * @param clayMan    the soldier calling the method
     * @param effectInst the instance of this effect (like an ItemStack for an Item)
     */
    public void onConstruct(EntityClayMan clayMan, SoldierEffectInst effectInst) {
    }

    /**
     * Called when a soldier holding the upgrade updates.
     *
     * @param clayMan    the soldier calling the method
     * @param effectInst the instance of this effect (like an ItemStack for an Item)
     * @return true, if the upgrade should be removed from the soldiers upgrade list
     */
    @SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
    public boolean onUpdate(EntityClayMan clayMan, SoldierEffectInst effectInst) {
        return false;
    }

    public EnumMethodState onTargeting(EntityClayMan clayMan, SoldierEffectInst effectInst, EntityClayMan target) {
        return EnumMethodState.SKIP;
    }

    public void getAiMoveSpeed(EntityClayMan clayMan, SoldierEffectInst effectInst, MutableFloat speed) {
    }

    public void onClientUpdate(EntityClayMan clayMan, SoldierEffectInst effectInst) {
    }

    public boolean shouldNbtSyncToClient(EntityClayMan clayMan, SoldierEffectInst effectInst) {
        return false;
    }

    public void onSoldierDeath(EntityClayMan clayMan, SoldierEffectInst effectInst, DamageSource source) {
    }

    public boolean isCompatibleWith(EntityClayMan clayMan, SoldierEffectInst effectInst, SoldierEffectInst checkEffect) {
        return true;
    }
}
