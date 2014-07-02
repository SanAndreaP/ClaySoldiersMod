/*******************************************************************************************************************
 * Name:      RenderPegasus.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.client.render;
import sanandreasp.mods.ClaySoldiersMod.client.model.ModelPegasus;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityPegasus;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.src.*;

public class RenderPegasus extends RenderHorse {

    public RenderPegasus(ModelBiped model, float f) {
        super(model, f);
		mv1 = (ModelPegasus)model;
    }
	
	@Override
	protected void preRenderCallback(EntityLivingBase entityliving, float f) {
		super.preRenderCallback(entityliving, f);
		EntityPegasus v1 = (EntityPegasus)entityliving;
		mv1.sinage = v1.sinage;
		mv1.gonRound = v1.onGround;
    }
	
	public ModelPegasus mv1;
}