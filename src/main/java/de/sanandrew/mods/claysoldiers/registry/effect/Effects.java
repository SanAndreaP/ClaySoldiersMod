/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.effect;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.effect.IEffectRegistry;

import java.util.UUID;

public final class Effects
{
    public static final UUID TIME_BOMB = UUID.fromString("710095EE-7E5F-4A6C-92E6-5214660810FD");
    public static final UUID BLINDING_REDSTONE = UUID.fromString("826F5747-C73A-4D68-AB77-67DD9144D4F1");
    public static final UUID STICKING_SLIMEBALL = UUID.fromString("BB1DE376-BBF8-47C0-9004-D3062AC8233B");
    public static final UUID MOVE_BACK = UUID.fromString("8CFB21EE-028C-4C40-B45D-115BE61FEC80");

    public static void initialize(IEffectRegistry registry) {
        registry.registerEffect(TIME_BOMB, EffectTimeBomb.INSTANCE);
        registry.registerEffect(BLINDING_REDSTONE, EffectRedstone.INSTANCE);
        registry.registerEffect(STICKING_SLIMEBALL, EffectSlimeball.INSTANCE);
        registry.registerEffect(MOVE_BACK, EffectBackWalk.INSTANCE);
    }
}
