/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.event;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.client.model.ModelClayMan;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.event.SoldierEvent;
import net.minecraft.client.model.ModelBiped;

@SideOnly(Side.CLIENT)
public class SoldierRenderEvent
        extends SoldierEvent
{
    public final RenderClayMan clayManRender;
    public final double renderX;
    public final double renderY;
    public final double renderZ;
    public final float renderYaw;
    public final float partTicks;
    public final EnumRenderStage stage;

    public SoldierRenderEvent(EntityClayMan clayMan, EnumRenderStage stage, RenderClayMan clayManRender, double x, double y, double z, float yaw, float partTicks) {
        super(clayMan);
        this.clayManRender = clayManRender;
        this.stage = stage;
        this.renderX = x;
        this.renderY = y;
        this.renderZ = z;
        this.renderYaw = yaw;
        this.partTicks = partTicks;
    }

    /**
     * <p>An Enum for the different render stages this event currently is in.</p>
     * <code>PRE</code> - Stage before the rendering happens.<br>
     * <code>POST</code> - Stage after the rendering happened.<br>
     * <code>EQUIPPED</code> - Stage during rendering of the equipped items.
     */
    @SideOnly(Side.CLIENT)
    public static enum EnumRenderStage
    {
        PRE, POST, EQUIPPED, MODEL, MODEL_ROTATIONS, LIVING
    }

    @SideOnly(Side.CLIENT)
    public static class RenderModelEvent
            extends SoldierRenderEvent
    {
        public final float limbSwing;
        public final float limbSwingAmount;
        public final float rotFloat;
        public final float pitch;

        public RenderModelEvent(EntityClayMan clayMan, RenderClayMan clayManRender, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw,
                                float rotPitch, float partTicks) {
            super(clayMan, EnumRenderStage.MODEL, clayManRender, clayMan.posX, clayMan.posY, clayMan.posZ, rotYaw, partTicks);
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.rotFloat = rotFloat;
            this.pitch = rotPitch;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class SetRotationAnglesEvent
            extends SoldierRenderEvent
    {
        public final ModelBiped model;
        public final float limbSwing;
        public final float limbSwingAmount;
        public final float rotFloat;
        public final float pitch;

        public SetRotationAnglesEvent(EntityClayMan clayMan, ModelClayMan clayManModel, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw,
                                      float rotPitch, float partTicks) {
            super(clayMan, EnumRenderStage.MODEL_ROTATIONS, null, clayMan.posX, clayMan.posY, clayMan.posZ, rotYaw, partTicks);
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.rotFloat = rotFloat;
            this.pitch = rotPitch;
            this.model = clayManModel;
        }
    }

    @SideOnly(Side.CLIENT)
    public static class RenderLivingEvent
            extends SoldierRenderEvent
    {
        public RenderLivingEvent(EntityClayMan clayMan, RenderClayMan clayManRender, double x, double y, double z) {
            super(clayMan, EnumRenderStage.LIVING, clayManRender, x, y, z, 0.0F, 0.0F);
        }
    }
}
