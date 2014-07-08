package de.sanandrew.mods.claysoldiers.client.event;

import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.event.SoldierEvent;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class SoldierRenderEvent
    extends SoldierEvent
{
    public final RenderClayMan clayManRender;
    public final double x;
    public final double y;
    public final double z;
    public final float yaw;
    public final float partTicks;
    public final RenderStage stage;

    public SoldierRenderEvent(EntityClayMan clayMan, RenderStage stage, RenderClayMan clayManRender, double x, double y, double z, float yaw, float partTicks) {
        super(clayMan);
        this.clayManRender = clayManRender;
        this.stage = stage;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.partTicks = partTicks;
    }

    public static class RenderModelEvent extends SoldierRenderEvent {
        public final float limbSwing;
        public final float limbSwingAmount;
        public final float rotFloat;
        public final float pitch;

        public RenderModelEvent(EntityClayMan clayMan, RenderClayMan clayManRender, float limbSwing, float limbSwingAmount, float rotFloat, float rotYaw, float rotPitch, float partTicks) {
            super(clayMan, RenderStage.MODEL, clayManRender, clayMan.posX, clayMan.posY, clayMan.posZ, clayMan.rotationYaw, partTicks);
            this.limbSwing = limbSwing;
            this.limbSwingAmount = limbSwingAmount;
            this.rotFloat = rotFloat;
            this.pitch = rotPitch;
        }
    }

    public static class RenderLivingEvent extends SoldierRenderEvent {
        public RenderLivingEvent(EntityClayMan clayMan, RenderClayMan clayManRender, double x, double y, double z) {
            super(clayMan, RenderStage.LIVING, clayManRender, x, y, z, 0.0F, 0.0F);
        }
    }

    /**
     * <p>An Enum for the different render stages the
     * {@link #onRender(de.sanandrew.mods.claysoldiers.util.upgrades.ISoldierUpgrade.RenderStage, de.sanandrew.mods.claysoldiers.entity.EntityClayMan, de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan, double, double, double, float, float) onRender}
     * method can be called in.</p>
     * <code>PRE</code> - Stage before the rendering happens.<br>
     * <code>POST</code> - Stage after the rendering happened.<br>
     * <code>EQUIPPED</code> - Stage during rendering of the equipped items.
     */
    public static enum RenderStage { PRE, POST, EQUIPPED, MODEL, LIVING }
}
