/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.lefthand;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.core.manpack.util.client.ItemRenderHelper;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.projectile.EntityFirechargeChunk;
import de.sanandrew.mods.claysoldiers.entity.projectile.ISoldierProjectile;
import de.sanandrew.mods.claysoldiers.tileentity.TileEntityClayNexus;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.IThrowableUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.mutable.MutableFloat;

public class UpgradeFirecharge
    extends AUpgradeLeftHanded
    implements IThrowableUpgrade
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        upgradeInst.getNbtTag().setShort("uses", (short) 15);
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return upgradeInst.getNbtTag().getShort("uses") == 0;
    }

    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgInst, ItemStack stack) {
        this.consumeItem(stack, upgInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }

    @Override
    public void getAiMoveSpeed(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, MutableFloat speed) {
        Entity target = clayMan.getEntityToAttack();
        if( target instanceof EntityLivingBase && !target.isDead && clayMan.canEntityBeSeen(target) && ((EntityLivingBase) target).getHealth() > 0 ) {
            float multiplier = Math.min(1.0F, Math.max(-1.0F, (float) clayMan.getDistanceSqToEntity(target) - 16.0F));
            speed.setValue(speed.getValue() * multiplier);
        }
    }

    @Override
    public void getAttackRange(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, Entity target, MutableFloat attackRange) {
        attackRange.setValue(-1.0F);
        if( target instanceof EntityLivingBase && !target.isDead && clayMan.canEntityBeSeen(target) && ((EntityLivingBase) target).getHealth() > 0 ) {
            clayMan.throwSomethingAtEnemy(((EntityLivingBase) target), EntityFirechargeChunk.class, true);
            clayMan.attackTime = 30;
            upgradeInst.getNbtTag().setShort("uses", (short) (upgradeInst.getNbtTag().getShort("uses") - 1));
        }
    }

    @Override
    public Class<? extends ISoldierProjectile<? extends EntityThrowable>> getThrowableClass() {
        return EntityFirechargeChunk.class;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderNexusThrowable(TileEntityClayNexus nexus, float partTicks) {
        ItemRenderHelper.renderIconIn3D(Blocks.lava.getIcon(0, 0), true, false, 0xFFFFFF);
    }
}
