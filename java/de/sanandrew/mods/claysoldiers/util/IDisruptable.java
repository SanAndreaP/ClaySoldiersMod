package de.sanandrew.mods.claysoldiers.util;

import net.minecraft.util.DamageSource;

/**
 * @author SanAndreas
 * @version 1.0
 */
public interface IDisruptable
{
    public static DamageSource disruptDamage = new DamageSource(CSM_Main.MOD_ID + ":disrupt").setDamageBypassesArmor().setMagicDamage();

    public void disrupt();
}
