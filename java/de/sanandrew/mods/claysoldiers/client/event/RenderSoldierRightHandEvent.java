package de.sanandrew.mods.claysoldiers.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.ModItems;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class RenderSoldierRightHandEvent
{
    private final ItemStack upgStick_ = new ItemStack(Items.stick);
    private final ItemStack upgBlazeRod_ = new ItemStack(Items.blaze_rod);
    private final ItemStack upgWoodButton_ = new ItemStack(Blocks.planks);
    private final ItemStack upgStoneButton_ = new ItemStack(Blocks.stone);
    private final ItemStack upgShearBlade_ = new ItemStack(ModItems.shearBlade);

    @SubscribeEvent
    public void onSoldierRender(SoldierRenderEvent event) {
        if( event.stage == SoldierRenderEvent.RenderStage.EQUIPPED ) {
            if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_STICK)) ) {
                this.renderRightHandItem(event.clayMan, event.clayManRender, this.upgStick_);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_BLAZEROD)) ) {
                this.renderRightHandItem(event.clayMan, event.clayManRender, this.upgBlazeRod_);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_SHEARRIGHT)) ) {
                this.renderRightHandItem(event.clayMan, event.clayManRender, this.upgShearBlade_);
            }

            if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_WOODBUTTON)) ) {
                this.renderKnuckle(event.clayMan, event.clayManRender, this.upgWoodButton_);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.getUpgrade(SoldierUpgrades.UPG_STONEBUTTON)) ) {
                this.renderKnuckle(event.clayMan, event.clayManRender, this.upgStoneButton_);
            }
        }
    }

    private void renderKnuckle(EntityClayMan clayMan, RenderClayMan renderer, ItemStack stack) {
        GL11.glPushMatrix();
        renderer.modelBipedMain.bipedRightArm.postRender(0.0625F);
        GL11.glTranslatef(-0.05F, 0.55F, 0.0F);
        GL11.glScalef(0.3F, 0.3F, 0.3F);
        renderer.getItemRenderer().renderItem(clayMan, stack, 0);
        GL11.glPopMatrix();
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
