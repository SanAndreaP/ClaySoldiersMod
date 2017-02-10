/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc.enchantment;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc.AUpgradeMisc;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.Constants.NBT;
import org.apache.commons.lang3.mutable.MutableFloat;

public class UpgradeGoldIngot
        extends AUpgradeMisc
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        ASoldierUpgrade[] upgrades = clayMan.getAvailableUpgrades();
        for( ASoldierUpgrade upgrade : upgrades ) {
            NBTTagCompound upgNbt = clayMan.getUpgrade(upgrade).getNbtTag();
            if( upgNbt.hasKey(NBT_USES, NBT.TAG_SHORT) ) {
                upgNbt.setShort(NBT_USES, (short) (upgNbt.getShort(NBT_USES) * 2));
            }
        }
    }

    @Override
    public void onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, MutableFloat damage) {
        damage.add(SAPUtils.RNG.nextFloat() + 1.0F);
    }

    @Override
    public boolean onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, MutableFloat damage) {
        damage.setValue(Math.max(0.25F, damage.getValue() - 1.0F));
        return false;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }

    @Override
    public void onUpgradeAdded(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, SoldierUpgradeInst appliedUpgradeInst) {
        NBTTagCompound upgNbt = appliedUpgradeInst.getNbtTag();
        if( upgNbt.hasKey(NBT_USES, NBT.TAG_SHORT) ) {
            upgNbt.setShort(NBT_USES, (short) (upgNbt.getShort(NBT_USES) * 2));
        }
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade) {
        return clayMan.hasUpgrade(SoldierUpgrades.UPG_GOLD_NUGGET);
    }
}
