package de.sanandrew.mods.claysoldiers.api;

import de.sanandrew.mods.claysoldiers.api.soldier.effect.ISoldierEffect;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("UnusedReturnValue")
public interface IEffectRegistry
{
    boolean registerEffect(UUID id, ISoldierEffect effect);

    @Nullable
    ISoldierEffect getEffect(UUID id);

    @Nullable
    UUID getId(ISoldierEffect effect);

    List<ISoldierEffect> getEffects();
}
