/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.SoldierEffects;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.IExplosiveUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

import java.util.ArrayList;

public class UpgradeMagmacream
        extends AUpgradeMisc
        implements IExplosiveUpgrade
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        upgradeInst.getNbtTag().setShort(NBT_USES, (short) 1);
    }

    @Override
    public void onSoldierDeath(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source) {
        if( source instanceof EntityDamageSource && !source.isProjectile() && !source.isExplosion() && source.getEntity() instanceof EntityClayMan ) {
            EntityClayMan target = (EntityClayMan) source.getEntity();
            target.playSound("game.tnt.primed", 1.0F, 1.0F);
            target.addEffect(SoldierEffects.getEffect(SoldierEffects.EFF_MAGMABOMB));
            target.attackEntityFrom(DamageSource.magic, 0.0F);
        }
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade) {
        return !clayMan.hasUpgrade(IExplosiveUpgrade.class);
    }

    @Override
    public void onItemDrop(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ArrayList<ItemStack> droppedItems) {
        // TODO: drop it when unused or?
        if( upgradeInst.getNbtTag().getShort(NBT_USES) == 1 ) {
            droppedItems.add(upgradeInst.getStoredItem());
        }
    }
}
