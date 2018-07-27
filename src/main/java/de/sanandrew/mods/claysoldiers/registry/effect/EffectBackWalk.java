/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.effect;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.attribute.AttributeHelper;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.effect.ISoldierEffect;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.effect.ISoldierEffectInst;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class EffectBackWalk
        implements ISoldierEffect
{
    public static final EffectBackWalk INSTANCE = new EffectBackWalk();

    private EffectBackWalk() { }

    @Override
    public void onAdded(ISoldier<?> soldier, ISoldierEffectInst effectInst) {
        AttributeHelper.tryApplyMoveDirectionModifier(soldier.getEntity(), MOVE_BACK);
    }

    @Override
    public void onExpired(ISoldier<?> soldier, ISoldierEffectInst effectInst) {
        AttributeHelper.tryRemoveMoveDirectionModifier(soldier.getEntity(), MOVE_BACK);
    }

    @Override
    public boolean syncData() {
        return true;
    }

    private static final AttributeModifier MOVE_BACK = new AttributeModifier(UUID.fromString("351A7B3C-B4B9-4B89-91B8-F42621E918A8"), CsmConstants.ID + ".movement.backwards", -2.0D, 2);
}
