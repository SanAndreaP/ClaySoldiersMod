/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.soldier;

import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.client.model.ModelGoggleBand;
import de.sanandrew.mods.claysoldiers.client.renderer.RenderClaySoldier;
import de.sanandrew.mods.claysoldiers.entity.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.claysoldiers.util.Resources;
import de.sanandrew.mods.sanlib.lib.client.ModelJsonLoader;
import de.sanandrew.mods.sanlib.lib.client.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.EnumMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class LayerGoggles
        implements LayerRenderer<EntityClaySoldier>
{
    private final ModelGoggleBand model;
    private final RenderClaySoldier renderer;

    public LayerGoggles(RenderClaySoldier renderer) {
        this.renderer = renderer;
        this.model = new ModelGoggleBand();
    }

    private static final ItemStack GLASS_NORMAL = new ItemStack(Blocks.GLASS, 1);
    private static final Map<EnumDyeColor, ItemStack> GLASS_STAINED = new EnumMap<>(EnumDyeColor.class);

    @Override
    public void doRenderLayer(EntityClaySoldier soldier, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ISoldierUpgradeInst inst = soldier.getUpgradeInstance(UpgradeRegistry.MC_GLASS, EnumUpgradeType.MISC);
        if( inst != null ) {
            GlStateManager.pushMatrix();
            this.renderer.modelBipedMain.bipedHead.postRender(scale);

            if( this.model.texture != null ) {
                this.renderer.bindTexture(this.model.texture);
                this.model.render(scale);
            }

            ItemStack renderedStack = GLASS_NORMAL;
            if( inst.getNbtData().hasKey("color") ) {
                EnumDyeColor clr = EnumDyeColor.byMetadata(inst.getNbtData().getByte("color"));
                renderedStack = GLASS_STAINED.computeIfAbsent(clr, newClr -> new ItemStack(Blocks.STAINED_GLASS, 1, newClr.getMetadata()));
            }
            RenderUtils.renderStackInWorld(renderedStack, 0.15D, -0.3D, -0.1825D, 0.0F, 0.0F, 0.0F, 0.375F);
            RenderUtils.renderStackInWorld(renderedStack, -0.15D, -0.3D, -0.1825D, 0.0F, 0.0F, 0.0F, 0.375F);

            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
