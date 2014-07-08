package de.sanandrew.mods.claysoldiers.util.upgrades.misc;

import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.upgrades.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgrades;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeWool
    extends AUpgradeMisc
{
    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        if( !clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_LEATHER)) ) {
            return true;
        }
        return false;
    }

    @Override
    public Pair<Float, Boolean> onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, float damage) {
        return Pair.with(damage >= 1.0F ? damage - 1.0F : 0.0F, true);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, ItemStack stack) {
        clayMan.setMiscColor(15 - stack.getItemDamage());
        clayMan.playSound("dig.cloth", 1.0F, 1.0F);
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ISoldierUpgrade upgrade) {
        return clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_LEATHER));
    }
}
