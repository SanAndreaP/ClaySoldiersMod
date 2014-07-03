package de.sanandrew.mods.claysoldiers.util.upgrades;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import net.minecraft.entity.EntityLivingBase;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class TestUpgrade
    implements ISoldierUpgrade
{
    @Override
    public boolean allowSoldierTarget(EntityClayMan clayMan, SoldierUpgradeInst upgInst, EntityClayMan target) {
        return false;
    }

    @Override
    public float onEntityAttack(EntityClayMan clayMan, SoldierUpgradeInst upgInst, EntityLivingBase target, float damage) {
        return 10F;
    }

    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgInst) {
        return false;
    }

    @Override
    public boolean isCompatibleWith(ISoldierUpgrade upgrade) {
        return false;
    }
}
