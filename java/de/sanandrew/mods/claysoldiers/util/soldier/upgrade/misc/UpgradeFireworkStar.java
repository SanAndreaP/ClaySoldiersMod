/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.IExplosiveUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

public class UpgradeFireworkStar
        extends AUpgradeMisc
        implements IExplosiveUpgrade
{
    @Override
    public void onClientUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        if( clayMan.getHealth() <= 0.0F ) {
            clayMan.worldObj.makeFireworks(clayMan.posX, clayMan.posY + clayMan.getEyeHeight(), clayMan.posZ, 0.0F, 0.0F, 0.0F, upgradeInst.getNbtTag());
        }
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);

        if( stack.hasTagCompound() && stack.getTagCompound().hasKey("Explosion") ) {
            NBTTagList explosionList = new NBTTagList();
            explosionList.appendTag(stack.getTagCompound().getCompoundTag("Explosion"));
            upgradeInst.getNbtTag().setTag("Explosions", explosionList);
        }
    }

    @Override
    public boolean shouldNbtSyncToClient(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return true;
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade) {
        return !clayMan.hasUpgrade(IExplosiveUpgrade.class);
    }
}
