package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeGlowstone
    extends AUpgradeMisc
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        upgradeInst.getNbtTag().setByte("fromBlock", (byte) 0);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, ItemStack stack) {
        if( stack.getItem() == Items.glowstone_dust ) {
            stack.stackSize--;
            clayMan.playSound("random.pop", 1.0F, 1.0F);
        } else if( stack.getItem() == Item.getItemFromBlock(Blocks.glowstone) ) {
            clayMan.getUpgradeData(this).getNbtTag().setByte("fromBlock", (byte) 1);
            clayMan.playSound("dig.glass", 1.0F, 1.0F);
        }
    }
}
