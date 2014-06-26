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
import net.minecraft.util.DamageSource;
import sanandreasp.mods.ClaySoldiersMod.client.render.UpgradeRenderHelper;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.packet.PacketSendParticle;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.leftHand.UpgShearBladeL;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.rightHand.UpgShearBladeR;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class UpgDBlock extends MiscUpgrade
{
    
    @Override
    public void initUpgrade(IUpgradeEntity entity, NBTTagCompound nbt)
    {
        EntityClayMan cs = (EntityClayMan) entity;
        cs.isSuper = true;
        cs.moveSpeed *= 3F;
        cs.setHealth(cs.getHealth() * 80);
    }
    
    @Override
    public ItemStack getItemStack(IUpgradeEntity entity)
    {
        return new ItemStack(Block.blockDiamond);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getHeldItem(IUpgradeEntity entity)
    {
        return null;
    }
    
    @Override
    public float onHit(IUpgradeEntity entity, DamageSource source,
            float initAmount)
    {
        int thisUpgID = CSMModRegistry.clayUpgRegistry.getIDByUpgrade(this);
        NBTTagCompound nbt = entity.getUpgradeNBT(thisUpgID);
        // byte pts = (byte)(nbt.getByte("points") - 1);
        // nbt.setByte("points", pts);
        // if( pts <= 0 ) entity.breakUpgrade(thisUpgID);
        return initAmount / 8F;
    }
    
    public void onPickup(IUpgradeEntity entity, EntityItem item,
            NBTTagCompound nbt)
    {
        this.initUpgrade(entity, nbt);
        EntityClayMan cs = (EntityClayMan) entity.getEntity();
        if (cs.hasUpgrade(CSMModRegistry.clayUpgRegistry
                .getIDByUpgradeClass(UpgSuper.class)))
        {
            cs.playSound("random.levelup", 0.2F, ((cs.getRNG().nextFloat() - cs
                    .getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            cs.deleteUpgrade(CSMModRegistry.clayUpgRegistry
                    .getIDByUpgradeClass(UpgDBlock.class));
        }
    }
    
}
