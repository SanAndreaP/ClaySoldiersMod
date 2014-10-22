/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.client.event.SoldierRenderEvent.EnumRenderStage;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class SoldierRightHandRenderHandler
{
    private final ItemStack p_upgStick = new ItemStack(Items.stick);
    private final ItemStack p_upgStickArrow = new ItemStack(Items.arrow);
    private final ItemStack p_upgBlazeRod = new ItemStack(Items.blaze_rod);
    private final ItemStack p_upgWoodButton = new ItemStack(Blocks.planks);
    private final ItemStack p_upgStoneButton = new ItemStack(Blocks.stone);
    private final ItemStack p_upgShearBlade = new ItemStack(RegistryItems.shearBlade);
    private final ItemStack p_upgGoldMelon = new ItemStack(Items.speckled_melon);
    private final ItemStack p_upgBone = new ItemStack(Items.bone);

    @SubscribeEvent
    public void onSoldierRender(SoldierRenderEvent event) {
        if( event.stage == EnumRenderStage.EQUIPPED ) {
            if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_STICK) ) {
                if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_FLINT) ) {
                    renderRightHandItem(event.clayMan, event.clayManRender, this.p_upgStickArrow);
                } else {
                    renderRightHandItem(event.clayMan, event.clayManRender, this.p_upgStick);
                }
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_BLAZEROD) ) {
                renderRightHandItem(event.clayMan, event.clayManRender, this.p_upgBlazeRod);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_SHEARRIGHT) ) {
                renderRightHandItem(event.clayMan, event.clayManRender, this.p_upgShearBlade);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_GOLDMELON) ) {
                renderRightHandItem(event.clayMan, event.clayManRender, this.p_upgGoldMelon);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_BONE) ) {
                renderRightHandItem(event.clayMan, event.clayManRender, this.p_upgBone);
            }

            if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_WOODBUTTON) ) {
                renderKnuckle(event.clayMan, event.clayManRender, this.p_upgWoodButton);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_STONEBUTTON) ) {
                renderKnuckle(event.clayMan, event.clayManRender, this.p_upgStoneButton);
            }
        }
    }

    private static void renderRightHandItem(EntityClayMan clayMan, RenderClayMan renderer, ItemStack stack) {
        GL11.glPushMatrix();
        renderer.modelBipedMain.bipedRightArm.postRender(0.0625F);
        GL11.glTranslatef(-0.1F, 0.6F, 0.0F);

        float itemScale = 0.6F;
        GL11.glScalef(itemScale, itemScale, itemScale);
        GL11.glRotatef(140.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);

        renderer.getItemRenderer().renderItem(clayMan, stack, 0);
        GL11.glPopMatrix();
    }

    private static void renderKnuckle(EntityClayMan clayMan, RenderClayMan renderer, ItemStack stack) {
        GL11.glPushMatrix();
        renderer.modelBipedMain.bipedRightArm.postRender(0.0625F);
        GL11.glTranslatef(-0.05F, 0.55F, 0.0F);
        GL11.glScalef(0.3F, 0.3F, 0.3F);
        renderer.getItemRenderer().renderItem(clayMan, stack, 0);
        GL11.glPopMatrix();
    }
}
