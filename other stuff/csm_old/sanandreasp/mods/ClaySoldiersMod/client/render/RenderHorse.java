/*******************************************************************************************************************
 * Name:      RenderHorseMount.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.client.render;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityHorse;
import net.minecraft.src.*;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityTurtle;
import sanandreasp.mods.ClaySoldiersMod.registry.Textures;

public class RenderHorse extends RenderBiped implements Textures {

    public RenderHorse(ModelBiped model, float f) {
        super(model, f);
    }

	@Override
	protected void preRenderCallback(EntityLivingBase entityliving, float f) {
		GL11.glScalef(0.7F, 0.7F, 0.7F);
    }

	@Override
	public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
		f1 *= 2F;
		super.doRenderLiving(entityliving, d, d1, d2, f, f1);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        if( entity instanceof EntityHorse ) {
            EntityHorse horse = (EntityHorse)entity;
            if( horse.isNightmare() )
                return HORSE_NIGHTMARE[horse.getAltTex()];

            return HORSE[horse.getType()][horse.getAltTex()];
        }
        return HORSE[0][0];
    }
}
