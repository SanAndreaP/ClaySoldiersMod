/*******************************************************************************************************************
 * Name:      RenderBunny.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.client.render;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityBunny;
import sanandreasp.mods.ClaySoldiersMod.registry.Textures;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderBunny extends RenderBiped implements Textures {

    public RenderBunny(ModelBiped model, float f) {
        super(model, f);
    }
		
	@Override
	public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1) {
		f1 *= 2F;
		super.doRenderLiving(entityliving, d, d1, d2, f, f1);
    } 

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        int type = (entity instanceof EntityBunny) ? ((EntityBunny)entity).getType() : 0;
        return BUNNY[type];
    }
}