/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.soldier.layer;

import de.sanandrew.mods.claysoldiers.api.client.soldier.ISoldierRender;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.claysoldiers.util.Resources;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityCreature;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerGunpowder
        implements LayerRenderer<EntityCreature>
{
    private final ModelBiped modelGunpwd;
    private final ISoldierRender<?, ?> renderer;

    public LayerGunpowder(ISoldierRender<?, ?> renderer) {
        this.renderer = renderer;
        this.modelGunpwd = renderer.getNewSoldierModel(0.001F);
    }

    @Override
    public void doRenderLayer(EntityCreature creature, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if( !(creature instanceof ISoldier) ) { return; }
        ISoldier soldier = (ISoldier) creature;

        if( soldier.hasUpgrade(Upgrades.MC_GUNPOWDER, EnumUpgradeType.MISC) ) {
            this.renderer.bindSoldierTexture(Resources.ENTITY_WEARABLE_GUNPOWDER.resource);

            this.modelGunpwd.setModelAttributes(this.renderer.getSoldierModel());
            this.modelGunpwd.render(creature, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
