/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.effect;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.effect.ISoldierEffect;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.effect.ISoldierEffectInst;

public class EffectSlimeball
        implements ISoldierEffect
{
    public static final EffectSlimeball INSTANCE = new EffectSlimeball();

    private EffectSlimeball() { }

    @Override
    public void onAdded(ISoldier<?> soldier, ISoldierEffectInst effectInst) {
        soldier.setMovable(false);
    }

    @Override
    public void onExpired(ISoldier<?> soldier, ISoldierEffectInst effectInst) {
        soldier.setMovable(true);
    }

    @Override
    public boolean syncData() {
        return true;
    }
}
