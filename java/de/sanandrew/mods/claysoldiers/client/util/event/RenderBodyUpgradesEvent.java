package de.sanandrew.mods.claysoldiers.client.util.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.claysoldiers.client.event.SoldierRenderEvent;
import de.sanandrew.mods.claysoldiers.client.event.SoldierRenderEvent.RenderStage;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgrades;
import org.lwjgl.opengl.GL11;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class RenderBodyUpgradesEvent
{
    @SubscribeEvent
    public void onSoldierRender(SoldierRenderEvent event) {
        if( event.stage == RenderStage.PRE || event.stage == RenderStage.POST ) {
            if (event.clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_EGG))) {
                renderStealthEffect(event.clayMan, event.clayManRender, event.stage);
            }
        }
    }

    @SubscribeEvent
    public void onSoldierLivingRender(SoldierRenderEvent.RenderLivingEvent event) {
        if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_IRONINGOT)) ) {
            GL11.glScalef(1.19F, 1.19F, 1.19F);
        }
    }

    private void renderStealthEffect(EntityClayMan clayMan, RenderClayMan clayManRender, RenderStage stage) {
        if( stage == RenderStage.PRE ) {
            GL11.glEnable(GL11.GL_BLEND);
            float transparency = 0.5F;
            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
            GL11.glColor4f(0.5F, 0.5F, 0.5F, transparency);
        } else {
            GL11.glDisable(GL11.GL_BLEND);
        }
    }
}
