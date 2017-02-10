/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc.enchantment;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityFirechargeChunk;
import de.sanandrew.mods.claysoldiers.entity.projectile.ISoldierProjectile;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc.AUpgradeMisc;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import org.apache.commons.lang3.mutable.MutableFloat;

public class UpgradeCoal
        extends AUpgradeMisc
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        if( clayMan.hasUpgrade(SoldierUpgrades.UPG_BLAZE_POWDER) ) {
            SoldierUpgradeInst bpUpgInst = clayMan.getUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_BLAZE_POWDER));
            bpUpgInst.getNbtTag().setShort(NBT_USES, (short) (bpUpgInst.getNbtTag().getShort(NBT_USES) + 1));
        }
    }

    @Override
    public void onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, MutableFloat damage) {
        if( clayMan.hasUpgrade(SoldierUpgrades.UPG_BLAZEROD) ) {
            target.setFire(6);
        }
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return !clayMan.hasUpgrade(SoldierUpgrades.UPG_BLAZEROD);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.fizz", 1.0F, 1.0F);
    }

    @Override
    public void onProjectileHit(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, MovingObjectPosition target,
                                ISoldierProjectile<? extends EntityThrowable> projectile) {
        if( target.entityHit != null && projectile instanceof EntityFirechargeChunk ) {
            target.entityHit.setFire(6);
        }
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade) {
        return clayMan.hasUpgrade(SoldierUpgrades.UPG_BLAZEROD) || clayMan.hasUpgrade(SoldierUpgrades.UPG_BLAZE_POWDER)
               || clayMan.hasUpgrade(SoldierUpgrades.UPG_FIRECHARGE);
    }
}
