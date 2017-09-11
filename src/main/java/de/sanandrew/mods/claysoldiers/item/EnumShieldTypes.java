/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.item;

public enum EnumShieldTypes
{
    SHIELD_NRM(0, "shield_nrm"),
    SHIELD_STUD(1, "shield_stud"),
    SHIELD_EEGG(2, "starfruit"),
    SHIELD_EEGG_STUD(3, "starfruit_stud");

    public final int damageVal;
    public final String modelName;

    public static final EnumShieldTypes[] VALUES = values();

    EnumShieldTypes(int dmg, String name) {
        this.damageVal = dmg;
        this.modelName = name;
    }
}
