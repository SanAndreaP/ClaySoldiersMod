/*******************************************************************************************************************
* Name: BehaviorUpgrade.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.behavior;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeItem;

public abstract class BehaviorUpgrade implements IUpgradeItem {

	@Override
	public final int getType() {
		return 4;
	}

	@Override
	public boolean isCompatibleWith(IUpgradeItem upgrade) {
		return !(upgrade instanceof BehaviorUpgrade);
	}
    
    @Override
    public Entity onTargeting(IUpgradeEntity attacker, Entity target) {
        return target;
    }
    
    @Override
    public float onHit(IUpgradeEntity attacker, DamageSource source, float initAmount) {
        return initAmount;
    }
    
    @Override
    public float onAttack(IUpgradeEntity attacker, EntityLivingBase target, float initAmount) {
        return initAmount;
    }

}
