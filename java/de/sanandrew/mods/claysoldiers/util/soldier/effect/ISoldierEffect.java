package de.sanandrew.mods.claysoldiers.util.soldier.effect;

import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.AttackState;
import net.minecraft.util.DamageSource;

/**
 * @author SanAndreas
 * @version 1.0
 */
public interface ISoldierEffect
{
    /**
     * Fired when an instance for this effect is created. Use this to initiate all needed values in the NBT provided by the instance
     * @param clayMan the soldier calling the method
     * @param effectInst the instance of this effect (like an ItemStack for an Item)
     */
    public void onConstruct(EntityClayMan clayMan, SoldierEffectInst effectInst);

    /**
     * Determines if the calling soldier should target the other soldier.
     * Note that it does NOT allow targeting of other soldiers if they are marked as dead!
     * @param clayMan the soldier calling the method
     * @param effectInst the instance of this effect (like an ItemStack for an Item)
     * @param target the soldier which is targeted by the calling soldier
     * @return An AttackStage Enum value. See
     *         {@link de.sanandrew.mods.claysoldiers.util.soldier.AttackState AttackStage}
     *         for more information.
     */
    public AttackState onTargeting(EntityClayMan clayMan, SoldierEffectInst effectInst, EntityClayMan target);

    /**
     * Determines if the calling soldier should be targeted by the targeting soldier.
     * @param clayMan the soldier calling the method
     * @param effectInst the instance of this effect (like an ItemStack for an Item)
     * @param attacker the solder targeting the calling soldier
     * @return An AttackStage Enum value. See
     *         {@link de.sanandrew.mods.claysoldiers.util.soldier.AttackState AttackStage}
     *         for more information.
     */
    public AttackState onBeingTargeted(EntityClayMan clayMan, SoldierEffectInst effectInst, EntityClayMan attacker);

    /**
     * Triggered when a soldier attempts to attack the target.
     * @param clayMan the soldier calling the method
     * @param effectInst the instance of this effect (like an ItemStack for an Item)
     * @param target the soldier which is targeted
     * @param damage the initial damage the soldier would've dealt
     * @return the new damage value. Note that with multiple upgrade modifying the damage value, the highest damage from those upgrade will be applied!
     */
    public float onSoldierAttack(EntityClayMan clayMan, SoldierEffectInst effectInst, EntityClayMan target, float damage);

    /**
     * Triggered when a soldier successfully damaged the target.
     * @param clayMan the soldier calling the method
     * @param effectInst the instance of this effect (like an ItemStack for an Item)
     * @param target the soldier which is targeted
     */
    public void onSoldierDamage(EntityClayMan clayMan, SoldierEffectInst effectInst, EntityClayMan target);

    public void onSoldierDeath(EntityClayMan clayMan, SoldierEffectInst effectInst, DamageSource source);

    public Pair<Float, Boolean> onSoldierHurt(EntityClayMan clayMan, SoldierEffectInst effectInst, DamageSource source, float damage);

    /**
     * Called when a soldier holding the upgrade updates.
     * @param clayMan the soldier calling the method
     * @param effectInst the instance of this effect (like an ItemStack for an Item)
     * @return true, if the upgrade should be removed from the soldiers upgrade list
     */
    public boolean onUpdate(EntityClayMan clayMan, SoldierEffectInst effectInst);

    public void onClientUpdate(EntityClayMan clayMan);
}
