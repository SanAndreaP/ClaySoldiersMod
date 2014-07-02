package sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc;

import java.util.Random;

import net.minecraft.block.Block;
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
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeItem;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.leftHand.UpgShearBladeL;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.rightHand.UpgShearBladeR;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class UpgGlowstoneInf extends MiscUpgrade {
    
    @Override
    public void onPickup(IUpgradeEntity entity, EntityItem item, NBTTagCompound nbt) {
        entity.getEntity().playSound(
                "random.glass",
                0.6F,
                ((entity.getEntity().getRNG().nextFloat() - entity.getEntity().getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F
        );
        
        entity.addUpgrade(CSMModRegistry.clayUpgRegistry.getIDByUpgradeClass(UpgGlowstone.class));
    }
    
    @Override
    public void initUpgrade(IUpgradeEntity entity, NBTTagCompound nbt) {
        
    }
    
    @Override
    public boolean isCompatibleWith(IUpgradeItem upgrade) {
        return super.isCompatibleWith(upgrade) && upgrade.getClass() != UpgGlowstone.class;
    }
    
    @Override
    public ItemStack getItemStack(IUpgradeEntity entity) {
        return new ItemStack(Block.glowStone);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getHeldItem(IUpgradeEntity entity) {
        return null;
    }

}
