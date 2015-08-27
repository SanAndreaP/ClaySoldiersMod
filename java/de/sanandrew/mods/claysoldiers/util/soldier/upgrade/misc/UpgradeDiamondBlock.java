/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants.NBT;

public class UpgradeDiamondBlock
        extends AUpgradeMisc
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        IAttributeInstance attrib = clayMan.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        attrib.setBaseValue(attrib.getBaseValue() * 80.0D);
        clayMan.heal(clayMan.getMaxHealth());

        ASoldierUpgrade[] upgrades = clayMan.getAvailableUpgrades();
        for( ASoldierUpgrade upgrade : upgrades ) {
            NBTTagCompound upgNbt = clayMan.getUpgrade(upgrade).getNbtTag();
            if( upgNbt.hasKey(NBT_USES, NBT.TAG_COMPOUND) ) {
                upgNbt.setShort(NBT_USES, (short) (upgNbt.getShort(NBT_USES) * 5));
            }
        }

        clayMan.addUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_SUGAR));
    }

    @Override
    public void onUpgradeAdded(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, SoldierUpgradeInst appliedUpgradeInst) {
        NBTTagCompound upgNbt = appliedUpgradeInst.getNbtTag();
        if( upgNbt.hasKey(NBT_USES, NBT.TAG_COMPOUND) ) {
            upgNbt.setShort(NBT_USES, (short) (upgNbt.getShort(NBT_USES) * 2));
        }
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade) {
        return !clayMan.hasUpgrade(SoldierUpgrades.UPG_DIAMOND_ITEM);
    }
}
