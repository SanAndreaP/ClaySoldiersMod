package de.sanandrew.mods.claysoldiers.util.upgrades.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgradeInst;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeGunpowder
    extends AUpgradeMisc
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        upgradeInst.getNbtTag().setByte("fromBlock", (byte) 0);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, ItemStack stack) {
        if( stack.getItem() == Items.gunpowder ) {
            stack.stackSize--;
            clayMan.playSound("random.pop", 1.0F, 1.0F);
        } else if( stack.getItem() == Item.getItemFromBlock(Blocks.tnt) ) {
            clayMan.getUpgradeData(this).getNbtTag().setByte("fromBlock", (byte) 1);
            clayMan.playSound("dig.glass", 1.0F, 1.0F);
        }
    }

    @Override
    public void onSoldierDeath(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source) {
        if( !(source.getEntity() instanceof EntityPlayer) ) {
            clayMan.worldObj.createExplosion(clayMan, clayMan.posX, clayMan.posY, clayMan.posZ, 0.5F, false);
        }
    }
}
