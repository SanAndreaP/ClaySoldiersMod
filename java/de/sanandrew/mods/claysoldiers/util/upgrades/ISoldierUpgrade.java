package de.sanandrew.mods.claysoldiers.util.upgrades;

import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * @author SanAndreas
 * @version 1.0
 */
public interface ISoldierUpgrade
{
    /**
     * Fired when an instance for this upgrade is created. Use this to initiate all needed values in the NBT provided by the instance
     * @param clayMan the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     */
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst);

    /**
     * Determines if the calling soldier should target the other soldier.
     * Note that it does NOT allow targeting of other soldiers if they are marked as dead!
     * @param clayMan the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @param target the soldier which is targeted by the calling soldier
     * @return <code>ALLOW, forces the calling soldier to take the other soldier as target<br>
     *         <code>DENY, forces the calling soldier to ignore the other soldier<br>
     *         <code>SKIP, if the calling soldier should skip this upgrade for checking
     */
    public AttackState onTargeting(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target);

    /**
     * Determines if the calling soldier should be targeted by the targeting soldier.
     * @param clayMan the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @param attacker the solder targeting the calling soldier
     * @return An AttackStage Enum value. See
     *         {@link de.sanandrew.mods.claysoldiers.util.upgrades.ISoldierUpgrade.AttackState AttackStage}
     *         for more information.
     */
    public AttackState onBeingTargeted(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan attacker);

    /**
     * Triggered when a soldier attempts to attack the target.
     * @param clayMan the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @param target the soldier which is targeted
     * @param damage the initial damage the soldier would've dealt
     * @return the new damage value. Note that with multiple upgrades modifying the damage value, the highest damage from those upgrades will be applied!
     */
    public float onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, float damage);

    /**
     * Triggered when a soldier successfully damaged the target.
     * @param clayMan the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @param target the soldier which is targeted
     */
    public void onSoldierDamage(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target);

    public Pair<Float, Boolean> onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, float damage);

    /**
     * Called when a soldier holding the upgrade updates.
     * @param clayMan the soldier calling the method
     * @param upgradeInst the instance of this upgrade (like an ItemStack for an Item)
     * @return true, if the upgrade should be removed from the soldiers upgrade list
     */
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst);

    /**
     * Checks if the given upgrade is compatible with this upgrade.
     * @param clayMan the soldier calling the method
     * @param stack the ItemStack which should be picked up
     * @param upgrade the upgrade to be checked
     * @return true, if the upgrade can co-exist with this upgrade
     */
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ISoldierUpgrade upgrade);

    /**
     * Called when a soldier picks the item up
     * @param clayMan the soldier calling the method
     * @param stack the ItemStack which is picked up
     */
    public void onPickup(EntityClayMan clayMan, ItemStack stack);

//    /**
//     * Called when a soldier is rendered.
//     * @param stage The stage this method is called. See
//     *              {@link de.sanandrew.mods.claysoldiers.util.upgrades.ISoldierUpgrade.RenderStage RenderStage}
//     *              for more information.
//     * @param clayMan The soldier calling the method.
//     * @param clayManRender The renderer of the calling soldier.
//     * @param x X-Position of the calling soldier.
//     * @param y Y-Position of the calling soldier.
//     * @param z Z-Position of the calling soldier.
//     * @param yaw The yaw of the calling soldier.
//     * @param partTicks The partial ticks of the soldier renderer.
//     */
//    @SideOnly(Side.CLIENT)
//    public void onRender(RenderStage stage, EntityClayMan clayMan, RenderClayMan clayManRender, double x, double y, double z, float yaw, float partTicks);
//
//    /**
//     * <p>An Enum for the different render stages the
//     * {@link #onRender(de.sanandrew.mods.claysoldiers.util.upgrades.ISoldierUpgrade.RenderStage, de.sanandrew.mods.claysoldiers.entity.EntityClayMan, de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan, double, double, double, float, float) onRender}
//     * method can be called in.</p>
//     * <code>PRE</code> - Stage before the rendering happens.<br>
//     * <code>POST</code> - Stage after the rendering happened.<br>
//     * <code>EQUIPPED</code> - Stage during rendering of the equipped items.
//     */
//    public static enum RenderStage { PRE, POST, EQUIPPED }

    /**
     * <p>An Enum for determining the attack behavior of the soldier.</p>
     * <code>ALLOW</code> - forces the targeting soldier to take the calling soldier as target<br>
     * <code>DENY</code> - forces the targeting soldier to ignore the calling soldier<br>
     * <code>SKIP</code> - if the called soldier should skip this upgrade for checking
     */
    public static enum AttackState { ALLOW, DENY, SKIP }
}
