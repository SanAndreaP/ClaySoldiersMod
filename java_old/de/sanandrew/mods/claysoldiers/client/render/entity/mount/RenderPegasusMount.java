/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.render.entity.mount;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.client.model.mount.ModelPegasusMount;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityPegasusMount;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderPegasusMount
        extends RenderLiving
{
    public RenderPegasusMount() {
        super(new ModelPegasusMount(), 0.2F);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase livingBase, float partTicks) {
        GL11.glTranslatef(0.0F, 0.36F, 0.0F);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return ((EntityPegasusMount) entity).getHorseTexture();
    }
}
