/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UpgradeGlowstone
        extends AUpgradeMisc
{
    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        if( stack.getItem() == Items.glowstone_dust ) {
            this.consumeItem(stack, upgradeInst);
            clayMan.playSound("random.pop", 1.0F, 1.0F);
        } else if( stack.getItem() == Item.getItemFromBlock(Blocks.glowstone) ) {
            clayMan.playSound("dig.glass", 1.0F, 1.0F);
        }
    }
}
