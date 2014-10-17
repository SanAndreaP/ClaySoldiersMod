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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableDouble;

public class UpgradeHelperGlass
        extends AUpgradeMisc
{
    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        ASoldierUpgrade mainUpgrade = SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_GLASS);
        if( clayMan.hasUpgrade(mainUpgrade) ) {
            SoldierUpgradeInst upgInst = clayMan.getUpgrade(mainUpgrade);
            return upgInst.getNbtTag().getShort("leftColor") == upgInst.getNbtTag().getShort("rightColor");
        } else {
            return true;
        }
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        ASoldierUpgrade mainUpgrade = SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_GLASS);
        SoldierUpgradeInst inst = clayMan.hasUpgrade(mainUpgrade) ? clayMan.getUpgrade(mainUpgrade) : clayMan.addUpgrade(mainUpgrade);

        if( inst.getNbtTag().hasKey("leftColor") && inst.getNbtTag().hasKey("rightColor") ) {
            short dmg = (stack.getItem() == Item.getItemFromBlock(Blocks.stained_glass_pane) || stack.getItem() == Item.getItemFromBlock(Blocks.stained_glass))
                        ? (short) stack.getItemDamage()
                        : -1;
            inst.getNbtTag().setShort("rightColor", dmg);
            clayMan.playSound("dig.glass", 1.0F, 1.0F);
        } else {
            if( stack.getItem() == Items.glass_bottle || stack.getItem() == Item.getItemFromBlock(Blocks.glass_pane) ) {
                this.consumeItem(stack, inst);
                clayMan.playSound("random.pop", 1.0F, 1.0F);
                inst.getNbtTag().setShort("leftColor", (short) -1);
                inst.getNbtTag().setShort("rightColor", (short) -1);
            } else if( stack.getItem() == Item.getItemFromBlock(Blocks.stained_glass_pane) ) {
                this.consumeItem(stack, inst);
                clayMan.playSound("random.pop", 1.0F, 1.0F);
                inst.getNbtTag().setShort("leftColor", (short) stack.getItemDamage());
                inst.getNbtTag().setShort("rightColor", (short) stack.getItemDamage());
            } else if( stack.getItem() == Item.getItemFromBlock(Blocks.glass) ) {
                clayMan.playSound("dig.glass", 1.0F, 1.0F);
                inst.getNbtTag().setShort("leftColor", (short) -1);
                inst.getNbtTag().setShort("rightColor", (short) -1);
            } else if( stack.getItem() == Item.getItemFromBlock(Blocks.stained_glass) ) {
                clayMan.playSound("dig.glass", 1.0F, 1.0F);
                inst.getNbtTag().setShort("leftColor", (short) stack.getItemDamage());
                inst.getNbtTag().setShort("rightColor", (short) stack.getItemDamage());
            }
        }
    }

    @Override
    public void getLookRange(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, MutableDouble radius) {
        radius.add(radius);
    }

    @Override
    public boolean shouldNbtSyncToClient(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return true;
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade) {
        ASoldierUpgrade mainUpgrade = SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_GLASS);
        if( clayMan.hasUpgrade(mainUpgrade) ) {
            SoldierUpgradeInst upgInst = clayMan.getUpgrade(mainUpgrade);
            short rColor = upgInst.getNbtTag().getShort("rightColor");
            short dmg = (stack.getItem() == Item.getItemFromBlock(Blocks.stained_glass_pane) || stack.getItem() == Item.getItemFromBlock(Blocks.stained_glass))
                        ? (short) stack.getItemDamage()
                        : -1;
            return upgInst.getNbtTag().getShort("leftColor") == rColor && dmg != rColor;
        } else {
            return true;
        }
    }
}
