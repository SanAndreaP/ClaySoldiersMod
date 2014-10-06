/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import net.minecraft.util.DamageSource;

public interface IDisruptable
{
    public static DamageSource disruptDamage = new DamageSource(ClaySoldiersMod.MOD_ID + ":disrupt").setDamageBypassesArmor().setMagicDamage();

    public void disrupt();
}
