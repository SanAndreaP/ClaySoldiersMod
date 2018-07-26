/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.renderer.soldier.layer;

import com.mojang.authlib.GameProfile;
import de.sanandrew.mods.claysoldiers.api.client.soldier.ISoldierRender;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.EntityCreature;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@SideOnly(Side.CLIENT)
public class LayerSkull
        implements LayerRenderer<EntityCreature>
{
    public final ISoldierRender<?, ?> renderer;

    public LayerSkull(ISoldierRender<?, ?> renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(EntityCreature creature, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if( !(creature instanceof ISoldier) ) {
            return;
        }

        ISoldier soldier = (ISoldier) creature;
        if( !soldier.hasUpgrade(Upgrades.MC_SKULL, EnumUpgradeType.MISC) ) {
            return;
        }

        ItemStack skullStack = new ItemStack(soldier.getUpgradeInstance(Upgrades.MC_SKULL, EnumUpgradeType.MISC).getNbtData().getCompoundTag("SkullItem"));
        GameProfile profile = null;

        GlStateManager.pushMatrix();

        this.renderer.getSoldierModel().bipedHead.postRender(0.0625F);

        float scaling = 1.1875F;
        GlStateManager.scale(scaling, -scaling, -scaling);

        if( skullStack.hasTagCompound() ) {
            NBTTagCompound nbt = skullStack.getTagCompound();

            if( Objects.requireNonNull(nbt).hasKey("SkullOwner", 10) ) {
                profile = NBTUtil.readGameProfileFromNBT(nbt.getCompoundTag("SkullOwner"));
            } else if( nbt.hasKey("SkullOwner", 8) ) {
                String s = nbt.getString("SkullOwner");

                if( !StringUtils.isBlank(s) ) {
                    profile = TileEntitySkull.updateGameprofile(new GameProfile(null, s));
                    nbt.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), profile));
                }
            }
        }

        TileEntitySkullRenderer.instance.renderSkull(-0.5F, 0.0F, -0.5F, EnumFacing.UP, 180.0F, skullStack.getMetadata(), profile, -1, limbSwing);
        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
