package de.sanandrew.mods.claysoldiers.util.soldier.effect;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import org.apache.commons.lang3.mutable.MutableFloat;

/**
 * @author SanAndreas
 * @version 1.0
 */
public abstract class ASoldierEffect
{
    /**
     * Fired when an instance for this effect is created. Use this to initiate all needed values in the NBT provided by the instance
     * @param clayMan the soldier calling the method
     * @param effectInst the instance of this effect (like an ItemStack for an Item)
     */
    public void onConstruct(EntityClayMan clayMan, SoldierEffectInst effectInst) { }

    /**
     * Called when a soldier holding the upgrade updates.
     * @param clayMan the soldier calling the method
     * @param effectInst the instance of this effect (like an ItemStack for an Item)
     * @return true, if the upgrade should be removed from the soldiers upgrade list
     */
    public boolean onUpdate(EntityClayMan clayMan, SoldierEffectInst effectInst) { return false; }

    public void getAiMoveSpeed(EntityClayMan clayMan, SoldierEffectInst effectInst, MutableFloat speed) { }

    public void onClientUpdate(EntityClayMan clayMan, SoldierEffectInst effectInst) { }

    public boolean sendNbtToClient(EntityClayMan clayMan, SoldierEffectInst effectInst) { return false; }
}
