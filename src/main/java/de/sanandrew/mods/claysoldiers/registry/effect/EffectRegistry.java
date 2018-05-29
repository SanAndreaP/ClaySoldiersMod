/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.effect;

import com.google.common.collect.ImmutableList;
import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.IEffectRegistry;
import de.sanandrew.mods.claysoldiers.api.soldier.effect.ISoldierEffect;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class EffectRegistry
        implements IEffectRegistry
{
    public static final EffectRegistry INSTANCE = new EffectRegistry();
    private final List<ISoldierEffect> effects;
    private final Map<UUID, ISoldierEffect> idToEffectMap;
    private final Map<ISoldierEffect, UUID> effectToIdMap;

    private EffectRegistry() {
        this.effects = new ArrayList<>();
        this.idToEffectMap = new HashMap<>();
        this.effectToIdMap = new HashMap<>();
    }

    @Override
    public boolean registerEffect(UUID id, ISoldierEffect effect) {
        if( id == null || effect == null ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Effect ID and instance cannot be null nor empty for ID %s!", id));
            return false;

        }

        if( this.idToEffectMap.containsKey(id) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Duplicate Effect ID %s!", id));
            return false;
        }

        if( this.effectToIdMap.containsKey(effect) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Duplicate Effect instances for %s!", id));
            return false;
        }

        this.idToEffectMap.put(id, effect);
        this.effectToIdMap.put(effect, id);
        this.effects.add(effect);

        return true;
    }

    @Nullable
    @Override
    public ISoldierEffect getEffect(UUID id) {
        return this.idToEffectMap.get(id);
    }

    @Nullable
    @Override
    public UUID getId(ISoldierEffect effect) {
        return this.effectToIdMap.get(effect);
    }

    @Override
    public List<ISoldierEffect> getEffects() {
        return ImmutableList.copyOf(this.effects);
    }

}
