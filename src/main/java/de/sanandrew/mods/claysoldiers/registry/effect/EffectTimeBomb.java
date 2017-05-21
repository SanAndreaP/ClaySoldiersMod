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

public class EffectTimeBomb
        implements ISoldierEffect
{
    @Override
    public void onExpired(ISoldier<?> soldier, ISoldierEffectInst effectInst) {
        if( !soldier.getEntity().world.isRemote ) {
            soldier.getEntity().world.createExplosion(null, soldier.getEntity().posX, soldier.getEntity().posY, soldier.getEntity().posZ, 1.5F, false);
        }
    }

    @Override
    public boolean syncData() {
        return true;
    }
}
