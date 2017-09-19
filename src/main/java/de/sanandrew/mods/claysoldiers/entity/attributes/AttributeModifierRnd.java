/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.entity.attributes;

import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class AttributeModifierRnd
        extends AttributeModifier
{
    private final double rngMultiplier;

    public AttributeModifierRnd(UUID idIn, String nameIn, double amountIn, double rngMultiplier, int operationIn) {
        super(idIn, nameIn, amountIn, operationIn);

        this.rngMultiplier = rngMultiplier;
    }

    @Override
    public double getAmount() {
        return super.getAmount() + MiscUtils.RNG.randomFloat() * this.rngMultiplier;
    }
}
