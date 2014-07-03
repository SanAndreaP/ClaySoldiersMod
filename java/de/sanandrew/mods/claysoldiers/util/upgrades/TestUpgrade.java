package de.sanandrew.mods.claysoldiers.util.upgrades;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class TestUpgrade
    implements ISoldierUpgrade
{
    @Override
    public void onConstruct(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        NBTTagCompound nbt = upgradeInst.getNbtTag();
        nbt.setInteger("uses", 5);
    }

    @Override
    public boolean allowSoldierTarget(EntityClayMan clayMan, SoldierUpgradeInst upgInst, EntityClayMan target) {
        return false;
    }

    @Override
    public float onEntityAttack(EntityClayMan clayMan, SoldierUpgradeInst upgInst, EntityLivingBase target, float damage) {
        upgInst.getNbtTag().setInteger("uses", upgInst.getNbtTag().getInteger("uses")-1);
        return 10F;
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgInst) {
        if( upgInst.getNbtTag().getInteger("uses") <= 0 ) {
            clayMan.playSound("random.break", 1.0F, 1.0F);
            return true;
        }
        return false;
    }

    @Override
    public boolean isCompatibleWith(ISoldierUpgrade upgrade) {
        return false;
    }

    @Override
    public void onPickup(EntityClayMan clayMan, ItemStack stack) {
        stack.stackSize--;
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}
