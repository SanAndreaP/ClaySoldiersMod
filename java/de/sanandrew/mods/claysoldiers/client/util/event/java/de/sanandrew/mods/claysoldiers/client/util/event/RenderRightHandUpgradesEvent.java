package de.sanandrew.mods.claysoldiers.client.util.event.java.de.sanandrew.mods.claysoldiers.client.util.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.claysoldiers.client.event.SoldierRenderEvent;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class RenderRightHandUpgradesEvent
{
    private final ItemStack itemStick_ = new ItemStack(Items.stick);
    private final ItemStack itemBlazeRod_ = new ItemStack(Items.blaze_rod);

    @SubscribeEvent
    public void onSoldierRender(SoldierRenderEvent event) {
        if( event.stage == SoldierRenderEvent.RenderStage.EQUIPPED ) {
            if (event.clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_STICK))) {
                renderRightHandItem(event.clayMan, event.clayManRender, itemStick_);
            } else if (event.clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_BLAZEROD))) {
                renderRightHandItem(event.clayMan, event.clayManRender, itemBlazeRod_);
            }
        }
    }

    private void renderRightHandItem(EntityClayMan clayMan, RenderClayMan renderer, ItemStack stack) {
        GL11.glPushMatrix();
        renderer.modelBipedMain.bipedRightArm.postRender(0.0625F);
        GL11.glTranslatef(-0.1F, 0.6F, 0F);

        float itemScale = 0.6F;
        GL11.glScalef(itemScale, itemScale, itemScale);
        GL11.glRotatef(140F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);

        renderer.getItemRenderer().renderItem(clayMan, stack, 0);
        GL11.glPopMatrix();
    }
}
