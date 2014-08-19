/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.ParticlePacketSender;
import de.sanandrew.mods.claysoldiers.util.IDisruptable;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

public class UpgradeBrownMushroom
    extends AUpgradeMisc
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        upgradeInst.getNbtTag().setShort(NBT_USES, (short) 2);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        if( clayMan.getHealth() < clayMan.getMaxHealth() * 0.25F && !upgradeInst.getNbtTag().getBoolean("disrupted") ) {
            upgradeInst.getNbtTag().setShort(NBT_USES, (short)(upgradeInst.getNbtTag().getShort(NBT_USES) - 1));
            clayMan.heal(10.0F);

            ParticlePacketSender.sendDiggingFx(clayMan.posX, clayMan.posY, clayMan.posZ, clayMan.dimension, Blocks.red_mushroom);
            clayMan.playSound("random.eat", 1.0F, 0.9F + SAPUtils.RNG.nextFloat() * 0.2F);
        }

        return upgradeInst.getNbtTag().getShort(NBT_USES) == 0;
    }

    @Override
    public boolean onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, MutableFloat damage) {
        if( source == IDisruptable.disruptDamage ) {
            upgradeInst.getNbtTag().setBoolean("disrupted", true);
        }

        return true;
    }
}
