package sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc;

import java.util.Random;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import sanandreasp.mods.ClaySoldiersMod.client.render.UpgradeRenderHelper;
import sanandreasp.mods.ClaySoldiersMod.packet.PacketSendParticle;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.leftHand.UpgShearBladeL;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.rightHand.UpgShearBladeR;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class UpgGlowstone extends MiscUpgrade {
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onPreRender(IUpgradeEntity entity, RenderManager manager, float partTicks, ModelBase model) {
        UpgradeRenderHelper.onGlowPreRender(this, manager, entity, partTicks, model);
    }
    
    @Override
    public void initUpgrade(IUpgradeEntity entity, NBTTagCompound nbt) {
        
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onPostRender(IUpgradeEntity entity, RenderManager manager, float partTicks, ModelBase model) {
        UpgradeRenderHelper.onGlowPostRender(this, manager, entity, partTicks, model);
    }
    
    @Override
    public ItemStack getItemStack(IUpgradeEntity entity) {
        return new ItemStack(Item.glowstone);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getHeldItem(IUpgradeEntity entity) {
        return null;
    }

}
