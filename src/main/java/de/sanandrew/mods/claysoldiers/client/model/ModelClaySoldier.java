/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.model;

import de.sanandrew.mods.claysoldiers.api.client.event.ClayModelRotationEvent;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;

public class ModelClaySoldier
        extends ModelBiped
{
    public ModelClaySoldier() {
        this(0.0F);
    }

    public ModelClaySoldier(float scale) {
        super(scale, 0.0F, 64, 64);
        this.bipedHeadwear.isHidden = true;
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);

        ClaySoldiersMod.EVENT_BUS.post(new ClayModelRotationEvent(this, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn));
    }
}
