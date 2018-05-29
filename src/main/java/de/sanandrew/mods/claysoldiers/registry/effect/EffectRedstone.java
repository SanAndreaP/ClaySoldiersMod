/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.effect;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.effect.ISoldierEffect;
import de.sanandrew.mods.claysoldiers.api.soldier.effect.ISoldierEffectInst;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.EnumParticleTypes;

public class EffectRedstone
        implements ISoldierEffect
{
    public static final EffectRedstone INSTANCE = new EffectRedstone();

    private EffectRedstone() { }

    @Override
    public void onTick(ISoldier<?> soldier, ISoldierEffectInst effectInst) {
        EntityCreature soldierL = soldier.getEntity();
        if( soldierL.world.isRemote && soldierL.getRNG().nextInt(10) == 0 ) {
            soldierL.world.spawnParticle(EnumParticleTypes.REDSTONE, soldierL.posX, soldierL.posY + soldierL.getEyeHeight(), soldierL.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean syncData() {
        return true;
    }
}
