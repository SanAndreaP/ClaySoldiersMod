/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.lefthand;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.ParticlePacketSender;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import org.apache.commons.lang3.mutable.MutableFloat;

import java.util.ArrayList;

public class UpgradeNetherQuartz
        extends AUpgradeLeftHanded
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        upgradeInst.getNbtTag().setShort(NBT_USES, (short) 4);
        upgradeInst.getNbtTag().setByte("hitCounter", (byte) 0);
    }

    @Override
    public boolean onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, MutableFloat damage) {
        byte hits = (byte) (upgradeInst.getNbtTag().getByte("hitCounter") + 1);

        if( hits >= 10 ) {
            hits = 0;
            for( EntityClayMan rick : clayMan.getSoldiersInRange() ) {
                if( !rick.getClayTeam().equals(clayMan.getClayTeam()) && rick.getDistanceSqToEntity(clayMan) <= 4.0D ) {
                    double dx = rick.posX - clayMan.posX;
                    double dz = rick.posZ - clayMan.posZ;
                    double vecLength = Math.sqrt(dx * dx + dz * dz);

                    rick.motionX = dx / vecLength;
                    rick.motionY = 0.3D;
                    rick.motionZ = dz / vecLength;
                }
            }

            ParticlePacketSender.sendShockwaveFx(clayMan.posX, clayMan.posY, clayMan.posZ, clayMan.yOffset, clayMan.dimension);

            upgradeInst.getNbtTag().setShort(NBT_USES, (short) (upgradeInst.getNbtTag().getShort(NBT_USES) - 1));
        }

        upgradeInst.getNbtTag().setByte("hitCounter", hits);

        return false;
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return upgradeInst.getNbtTag().getShort(NBT_USES) == 0;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }

    @Override
    public void onItemDrop(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ArrayList<ItemStack> droppedItems) {
        // TODO: drop it when unused or?
        if( upgradeInst.getNbtTag().getShort(NBT_USES) == 4 ) {
            droppedItems.add(upgradeInst.getStoredItem());
        }
    }
}
