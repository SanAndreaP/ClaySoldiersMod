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
import de.sanandrew.core.manpack.util.client.helpers.ItemRenderHelper;
import de.sanandrew.mods.claysoldiers.client.event.SoldierRenderEvent.EnumRenderStage;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.client.util.Textures;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class SoldierLeftHandRenderHandler
{
    private final ItemStack p_itemShearBlade = new ItemStack(RegistryItems.shearBlade);
    private final ItemStack p_blockGravel = new ItemStack(Blocks.gravel);
    private final ItemStack p_blockSnow = new ItemStack(Blocks.snow);
    private final ItemStack p_blockObsidian = new ItemStack(Blocks.obsidian); //TODO: substitude until proper texture arrives
    private final ItemStack p_blockEmerald = new ItemStack(Blocks.emerald_block);

    @SubscribeEvent
    public void onSoldierRender(SoldierRenderEvent event) {
        if( event.stage == EnumRenderStage.EQUIPPED ) {
            if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_SHEARLEFT) ) {
                renderLeftHandItem(event.clayMan, event.clayManRender, this.p_itemShearBlade);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_GRAVEL) ) {
                renderThrowableBlock(event.clayMan, event.clayManRender, this.p_blockGravel);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_SNOW) ) {
                renderThrowableBlock(event.clayMan, event.clayManRender, this.p_blockSnow);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_FIRECHARGE) ) {
                renderThrowableBlock(event.clayMan, event.clayManRender, this.p_blockObsidian);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_EMERALD) ) {
                renderThrowableBlock(event.clayMan, event.clayManRender, this.p_blockEmerald);
            } else if( event.clayMan.hasUpgrade(SoldierUpgrades.UPG_BOWL) ) {
                renderShield(event.clayMan, event.clayManRender);
            }
        }
    }

    private static void renderLeftHandItem(EntityClayMan clayMan, RenderClayMan renderer, ItemStack stack) {
        GL11.glPushMatrix();
        renderer.modelBipedMain.bipedLeftArm.postRender(0.0625F);
        GL11.glTranslatef(-0.1F, 0.6F, 0.0F);

        float itemScale = 0.6F;
        GL11.glScalef(itemScale, itemScale, itemScale);
        GL11.glRotatef(140.0F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);

        renderer.getItemRenderer().renderItem(clayMan, stack, 0);
        GL11.glPopMatrix();
    }

    private static void renderThrowableBlock(EntityClayMan clayMan, RenderClayMan renderer, ItemStack stack) {
        GL11.glPushMatrix();
        renderer.modelBipedMain.bipedLeftArm.postRender(0.0625F);
        GL11.glTranslatef(0.05F, 0.55F, 0.0F);
        GL11.glScalef(0.3F, 0.3F, 0.3F);
        renderer.getItemRenderer().renderItem(clayMan, stack, 0);
        GL11.glPopMatrix();
    }

    private static void renderShield(EntityClayMan clayMan, RenderClayMan renderer) {
        IIcon icon = clayMan.hasUpgrade(SoldierUpgrades.UPG_IRON_BLOCK) ? Textures.s_shieldStudIcon : Textures.s_shieldIcon;

        GL11.glPushMatrix();
        renderer.modelBipedMain.bipedLeftArm.postRender(0.0625F);
        GL11.glTranslatef(-0.4F, 0.15F, -0.2F);
        GL11.glScalef(0.75F, 0.75F, 0.75F);
        ItemRenderHelper.renderIconIn3D(icon, false, false, 0xFFFFFF);
        GL11.glPopMatrix();
    }
}
