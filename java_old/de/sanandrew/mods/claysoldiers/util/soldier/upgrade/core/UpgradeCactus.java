/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.core;

import cpw.mods.fml.common.FMLLog;
import de.sanandrew.core.manpack.util.SAPReflectionHelper;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Field;

public class UpgradeCactus
        extends AUpgradeCore
{
    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        Field cmFireField = SAPReflectionHelper.getCachedField(Entity.class, "field_70151_c", "fire");

        try {
            int fire = cmFireField.getInt(clayMan);
            if( fire > 0 && clayMan.getRNG().nextInt(10) == 0 ) {
                cmFireField.setInt(clayMan, clayMan.getRNG().nextInt(4) != 0 ? 0 : fire / 2);
            }
        } catch( IllegalAccessException e ) {
            FMLLog.log(ClaySoldiersMod.MOD_LOG, Level.WARN, "Couldn't access fire field for cactus upgrade!");
        }

        return false;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
