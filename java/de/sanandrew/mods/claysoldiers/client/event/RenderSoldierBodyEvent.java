package de.sanandrew.mods.claysoldiers.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.claysoldiers.client.event.SoldierRenderEvent.RenderStage;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class RenderSoldierBodyEvent
{
    @SubscribeEvent
    public void onSoldierRender(SoldierRenderEvent event) {
        if( event.stage == RenderStage.PRE || event.stage == RenderStage.POST ) {
            if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_EGG)) ) {
                renderStealthEffect(event.clayMan, event.clayManRender, event.stage);
            }
            if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_GLOWSTONE)) ) {
                renderGlowstoneEffect(event.clayMan, event.clayManRender, event.stage);
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
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        } else {
            GL11.glDisable(GL11.GL_BLEND);
        }
    }

    private void renderGlowstoneEffect(EntityClayMan clayMan, RenderClayMan clayManRender, RenderStage stage) {
        if( stage == RenderStage.PRE ) {
            int c0 = 0xF0;
            int j = c0 % 65536;
            int k = c0 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
