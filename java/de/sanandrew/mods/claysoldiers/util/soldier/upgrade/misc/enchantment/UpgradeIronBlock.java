/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc.enchantment;

import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.ASoldierEffect;
import de.sanandrew.mods.claysoldiers.util.soldier.effect.SoldierEffects;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc.AUpgradeMisc;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc.UpgradeBlazePowder;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

public class UpgradeIronBlock
        extends AUpgradeMisc
{
    public ASoldierEffect[] blockableEffects = new ASoldierEffect[] {
            SoldierEffects.getEffect(SoldierEffects.EFF_SLOWMOTION),
            SoldierEffects.getEffect(SoldierEffects.EFF_SLIMEFEET),
            SoldierEffects.getEffect(SoldierEffects.EFF_MAGMABOMB),
            SoldierEffects.getEffect(SoldierEffects.EFF_SLOWMOTION)
    };

    @Override
    public boolean onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, MutableFloat damage) {
        damage.setValue(Math.max(0.0F, damage.getValue() - 1.0F));
        if( SAPUtils.RNG.nextBoolean() ) {
            if( source == UpgradeBlazePowder.BLAZEPOWDER_DAMAGE_SRC ) {
                return true;
            }

            if( clayMan.isPotionActive(Potion.poison) ) {
                clayMan.removePotionEffect(Potion.poison.getId());
            }

            for( ASoldierEffect effect : this.blockableEffects ) {
                if( clayMan.hasEffect(effect) ) {
                    clayMan.removeEffect(effect);
                }
            }
        }

        return false;
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return !clayMan.hasUpgrade(SoldierUpgrades.UPG_BOWL);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }

    @Override
    public boolean canBePickedUp(EntityClayMan clayMan, ItemStack stack, ASoldierUpgrade upgrade) {
        return clayMan.hasUpgrade(SoldierUpgrades.UPG_BOWL);
    }
}
