/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer;

import de.sanandrew.mods.claysoldiers.client.model.ModelClaySoldier;
import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderClaySoldier
        extends RenderBiped<EntityClaySoldier>
{
    public RenderClaySoldier(RenderManager manager) {
        super(manager, new ModelClaySoldier(), 0.1F);
    }

    @Override
    protected void renderLivingAt(EntityClaySoldier entityLivingBaseIn, double x, double y, double z) {
        super.renderLivingAt(entityLivingBaseIn, x, y, z);
        GlStateManager.scale(0.2F, 0.2F, 0.2F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityClaySoldier entity) {
        int textureType = entity.getTextureType();
        int textureId = entity.getTextureId();
        if( textureType == 0x02 ) {
            return entity.getSoldierTeam().getUniqueTexture(textureId);
        } else if( textureType == 0x01 ) {
            return entity.getSoldierTeam().getRareTexture(textureId);
        }
        return entity.getSoldierTeam().getNormalTexture(textureId);
    }

    //    @Override
//    public void doRender(EntityLiving entityLiving, double x, double y, double z, float yaw, float partTicks) {
//        this.doRenderClayMan((EntityClayMan) entityLiving, x, y, z, yaw, partTicks);
//    }
//
//    @Override
//    protected ResourceLocation getEntityTexture(EntityLiving entityLiving) {
//        return ((EntityClayMan) entityLiving).getTexture();
//    }
//
//    @Override
//    protected void renderEquippedItems(EntityLivingBase entityLivingBase, float partTicks) {
//        super.renderEquippedItems(entityLivingBase, partTicks);
//        ClaySoldiersMod.EVENT_BUS.post(new SoldierRenderEvent(((EntityClayMan) entityLivingBase), EnumRenderStage.EQUIPPED, this, 0.0D, 0.0D, 0.0D, 0.0F, partTicks));
//    }
//
//    @Override
//    protected void renderModel(EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch,
//                               float partTicks) {
//        GL11.glPushMatrix();
//        super.renderModel(entityLivingBase, limbSwing, limbSwingAmount, rotFloat, rotYaw, rotPitch, partTicks);
//        ClaySoldiersMod.EVENT_BUS.post(new SoldierRenderEvent.RenderModelEvent((EntityClayMan) entityLivingBase, this, limbSwing, limbSwingAmount, rotFloat, rotYaw,
//                                                                               rotPitch, partTicks
//                                       )
//        );
//        GL11.glPopMatrix();
//    }
//
//    @Override
//    protected void renderLivingAt(EntityLivingBase entityLivingBase, double x, double y, double z) {
//        super.renderLivingAt(entityLivingBase, x, y, z);
//        GL11.glScalef(0.2F, 0.2F, 0.2F);
//        ClaySoldiersMod.EVENT_BUS.post(new SoldierRenderEvent.RenderLivingEvent((EntityClayMan) entityLivingBase, this, x, y, z));
//    }
//
//    public ItemRenderer getItemRenderer() {
//        return this.renderManager.itemRenderer;
//    }
//
//    private void doRenderClayMan(EntityClayMan clayMan, double x, double y, double z, float yaw, float partTicks) {
//        GL11.glPushMatrix();
//        ClaySoldiersMod.EVENT_BUS.post(new SoldierRenderEvent(clayMan, EnumRenderStage.PRE, this, x, y, z, yaw, partTicks));
//        super.doRender(clayMan, x, y, z, yaw, partTicks);
//        ClaySoldiersMod.EVENT_BUS.post(new SoldierRenderEvent(clayMan, EnumRenderStage.POST, this, x, y, z, yaw, partTicks));
//        GL11.glPopMatrix();
//    }
}
