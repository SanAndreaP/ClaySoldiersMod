package de.sanandrew.mods.claysoldiers.util.upgrades;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import net.minecraft.entity.EntityLivingBase;

import java.util.Iterator;

/**
 * @author SanAndreas
 * @version 1.0
 */
public interface ISoldierUpgrade
{
    /**
     * Determines if the soldier should be targeted, regardless if it isn't originally (or due to other upgrades) supposed to be one.
     * Note that it does NOT allow targeting of other soldiers if they are marked as dead!
     * @param clayMan the soldier firing the method
     * @param upgInst the instance of this upgrade (like an ItemStack for an Item)
     * @param target the soldier which is targeted
     * @return true, if the soldier should be targeted
     */
    public boolean allowSoldierTarget(EntityClayMan clayMan, SoldierUpgradeInst upgInst, EntityClayMan target);

    /**
     * Triggered when a soldier attempts to attack the target.
     * @param clayMan the soldier firing the method
     * @param upgInst the instance of this upgrade (like an ItemStack for an Item)
     * @param target the entity which is targeted
     * @param damage the initial damage the soldier would've dealt
     * @return the new damage value. Note that with multiple upgrades modifying the damage value, the highest damage from those upgrades will be applied!
     */
    public float onEntityAttack(EntityClayMan clayMan, SoldierUpgradeInst upgInst, EntityLivingBase target, float damage);

    /**
     * Called when a soldier holding the upgrade updates.
     * @param clayMan the soldier firing the method
     * @param upgInst the instance of this upgrade (like an ItemStack for an Item)
     * @return true, if the upgrade should be removed from the soldiers upgrade list
     */
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgInst);

    /**
     * Checks if the given upgrade is compatible with this upgrade.
     * @param upgrade the upgrade to be checked
     * @return true, if the upgrade can co-exist with this upgrade
     */
    public boolean isCompatibleWith(ISoldierUpgrade upgrade);
}
