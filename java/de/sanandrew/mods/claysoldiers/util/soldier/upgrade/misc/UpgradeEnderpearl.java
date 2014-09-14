/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.MethodState;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

public class UpgradeEnderpearl
        extends AUpgradeMisc
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        IAttributeInstance attrib = clayMan.getEntityAttribute(SharedMonsterAttributes.maxHealth);
        attrib.setBaseValue(attrib.getBaseValue() + 5.0D);
        clayMan.heal(clayMan.getMaxHealth());
        upgradeInst.getNbtTag().setShort("ticksActive", (short) 0);
    }

    @Override
    public MethodState onTargeting(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {
        if( clayMan.getClayTeam().equals(target.getClayTeam()) && !target.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_ENDERPEARL)) ) {
            return MethodState.ALLOW;
        }

        return MethodState.SKIP;
    }

    @Override
    public MethodState onBeingTargeted(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan attacker) {
        return attacker.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_ENDERPEARL)) ? MethodState.SKIP : MethodState.ALLOW;
    }

    @Override
    public void onSoldierAttack(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target, MutableFloat damage) {
        if( target.getEntityToAttack() == null ) {
            target.setTarget(clayMan);
        }

        if( damage.getValue() >= target.getHealth() ) {
            target.addUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_ENDERPEARL));
            clayMan.heal(clayMan.getMaxHealth());
            damage.setValue(0.0F);
        }

        upgradeInst.getNbtTag().setShort("ticksActive", (short) 0);
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        short ticksAlive = upgradeInst.getNbtTag().getShort("ticksActive");
        upgradeInst.getNbtTag().setShort("ticksActive", ++ticksAlive);

        if( !(clayMan.getEntityToAttack() instanceof EntityClayMan
                && ((EntityClayMan) clayMan.getEntityToAttack()).getClayTeam().equals(clayMan.getClayTeam())) ) {
            EntityPlayer closestPlayer = clayMan.worldObj.getClosestPlayer(clayMan.posX, clayMan.posY, clayMan.posZ, clayMan.getLookRangeRad());
            if( !(clayMan.getEntityToAttack() instanceof EntityPlayer) && closestPlayer != null && !closestPlayer.isDead
                    && !(closestPlayer.isEntityInvulnerable() || closestPlayer.capabilities.isCreativeMode) ) {
                clayMan.setTarget(closestPlayer);
            }
        }

        if( ticksAlive == 12000 ) {
            clayMan.attackEntityFrom(DamageSource.magic, 10000.0F);
        }

        return false;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
