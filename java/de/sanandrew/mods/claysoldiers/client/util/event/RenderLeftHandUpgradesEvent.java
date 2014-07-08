package de.sanandrew.mods.claysoldiers.client.util.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.claysoldiers.client.event.SoldierRenderEvent;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.ModItems;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgrades;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class RenderLeftHandUpgradesEvent
{
    private final ItemStack itemShearBlade_ = new ItemStack(ModItems.shearBlade);

    @SubscribeEvent
    public void onSoldierRender(SoldierRenderEvent event) {
        if( event.stage == SoldierRenderEvent.RenderStage.EQUIPPED ) {
            if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgradeFromName(SoldierUpgrades.UPG_SHEARLEFT)) ) {
                renderLeftHandItem(event.clayMan, event.clayManRender, this.itemShearBlade_);
            }
        }
    }

    private void renderLeftHandItem(EntityClayMan clayMan, RenderClayMan renderer, ItemStack stack) {
        GL11.glPushMatrix();
        renderer.modelBipedMain.bipedLeftArm.postRender(0.0625F);
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
