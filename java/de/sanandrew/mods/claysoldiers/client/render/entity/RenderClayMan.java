package de.sanandrew.mods.claysoldiers.client.render.entity;

import de.sanandrew.mods.claysoldiers.client.util.Textures;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class RenderClayMan extends RenderBiped
{
    public RenderClayMan() {
        super(new ModelBiped(0.0F, 0.0F, 64, 64), 0.1F);
    }

//    @Override
//    protected void renderModel(EntityLivingBase par1EntityLivingBase, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
//        GL11.glPushMatrix();
////        GL11.glScalef(0.2F, 0.2F, 0.2F);
////        GL11.glTranslatef(0.0F, 5.96F, 0.0F);
//        super.renderModel(par1EntityLivingBase, limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks);
//        GL11.glPopMatrix();
//    }


    @Override
    protected void renderLivingAt(EntityLivingBase entityLivingBase, double x, double y, double z) {
        super.renderLivingAt(entityLivingBase, x, y, z);
        GL11.glScalef(0.2F, 0.2F, 0.2F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving entityLiving) {
        return this.getEntityTexture((EntityClayMan) entityLiving);
    }

    private ResourceLocation getEntityTexture(EntityClayMan clayMan) {
        return clayMan.getTexture();
    }
}
