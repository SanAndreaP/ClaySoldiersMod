/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade;

public enum EnumUpgFunctions
{
    ON_PICKUP,
    ON_TICK,
    ON_OTHR_DESTROYED,
    ON_ATTACK,
    ON_DAMAGED,
    ON_DEATH,
    ON_ATTACK_SUCCESS,
    ON_DAMAGED_SUCCESS,
    ON_UPGRADE_ADDED,
    ON_SET_FIRE;

    public static final EnumUpgFunctions[] VALUES = values();
}
