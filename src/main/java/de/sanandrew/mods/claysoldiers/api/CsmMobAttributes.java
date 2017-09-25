/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;

public final class CsmMobAttributes
{
    public static final IAttribute KB_RESISTANCE = (new RangedAttribute(null, CsmConstants.ID + ".knockbackResistance", 0.0D, 0.0D, 1.0D)).setDescription("Knockback Resistance");
}
