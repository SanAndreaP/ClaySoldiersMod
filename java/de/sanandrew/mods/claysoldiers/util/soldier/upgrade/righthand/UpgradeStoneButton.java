package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.righthand;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeStoneButton
    extends AUpgradeRightHanded
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        NBTTagCompound nbt = upgradeInst.getNbtTag();
    }

    @Override
    public float onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, float damage) {
        return damage + 2.0F + clayMan.getRNG().nextFloat();
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        if( !clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_WOODBUTTON)) && !clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_STICK)) && !clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_BLAZEROD)) ) {
            return true;
        }
        return false;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, ItemStack stack) {
        stack.stackSize--;
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
