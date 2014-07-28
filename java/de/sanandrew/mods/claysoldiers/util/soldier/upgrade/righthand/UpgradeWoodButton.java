package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.righthand;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableFloat;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeWoodButton
    extends AUpgradeRightHanded //TODO: this is a MISC upgrade, not a RIGHTHAND upgrade! Please change!
{
    @Override
    public void onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, MutableFloat damage) {
        damage.add(1.0F + clayMan.getRNG().nextFloat());
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        //TODO: If soldier has no stone button and no blaze rod and no stick, then destroy upgrade. What???
        if( !clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_STONEBUTTON))
                && !clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_STICK))
                && !clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_BLAZEROD)) ) {
            return true;
        }
        return false;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgInst, ItemStack stack) {
        this.consumeItem(stack, upgInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
