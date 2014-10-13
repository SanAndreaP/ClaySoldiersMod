/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.render.entity;

import de.sanandrew.mods.claysoldiers.client.event.SoldierRenderEvent;
import de.sanandrew.mods.claysoldiers.client.model.ModelClayMan;
import de.sanandrew.mods.claysoldiers.client.util.ClientProxy;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderClayMan
        extends RenderBiped
{
    public RenderClayMan() {
        super(new ModelClayMan(), 0.1F);
    }

    @Override
    public void bindTexture(ResourceLocation resource) {
        super.bindTexture(resource);
    }

    @Override
    public void doRender(EntityLiving entityLiving, double x, double y, double z, float yaw, float partTicks) {
        if( ClientProxy.clayCamEntity == entityLiving && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 ) {
            return;
        }
        this.doRenderClayMan((EntityClayMan) entityLiving, x, y, z, yaw, partTicks);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLiving entityLiving) {
        return this.getEntityTexture((EntityClayMan) entityLiving);
    }

    @Override
    protected void renderEquippedItems(EntityLivingBase entityLivingBase, float partTicks) {
        super.renderEquippedItems(entityLivingBase, partTicks);
        ClaySoldiersMod.EVENT_BUS.post(new SoldierRenderEvent(((EntityClayMan) entityLivingBase), SoldierRenderEvent.RenderStage.EQUIPPED, this, 0.0D, 0.0D, 0.0D, 0.0F,
                                                       partTicks)
        );
    }

    @Override
    protected void renderModel(EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch,
                               float partTicks) {
        GL11.glPushMatrix();
        super.renderModel(entityLivingBase, limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks);
        ClaySoldiersMod.EVENT_BUS.post(new SoldierRenderEvent.RenderModelEvent((EntityClayMan) entityLivingBase, this, limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch,
                                                                        partTicks)
        );
        GL11.glPopMatrix();
    }

    @Override
    protected void renderLivingAt(EntityLivingBase entityLivingBase, double x, double y, double z) {
        super.renderLivingAt(entityLivingBase, x, y, z);
        GL11.glScalef(0.2F, 0.2F, 0.2F);
        ClaySoldiersMod.EVENT_BUS.post(new SoldierRenderEvent.RenderLivingEvent((EntityClayMan) entityLivingBase, this, x, y, z));
    }

    public ItemRenderer getItemRenderer() {
        return this.renderManager.itemRenderer;
    }

    private void doRenderClayMan(EntityClayMan clayMan, double x, double y, double z, float yaw, float partTicks) {
        GL11.glPushMatrix();
        ClaySoldiersMod.EVENT_BUS.post(new SoldierRenderEvent(clayMan, SoldierRenderEvent.RenderStage.PRE, this, x, y, z, yaw, partTicks));
        super.doRender(clayMan, x, y, z, yaw, partTicks);
        ClaySoldiersMod.EVENT_BUS.post(new SoldierRenderEvent(clayMan, SoldierRenderEvent.RenderStage.POST, this, x, y, z, yaw, partTicks));
        GL11.glPopMatrix();
    }

    private ResourceLocation getEntityTexture(EntityClayMan clayMan) {
        return clayMan.getTexture();
    }
}
