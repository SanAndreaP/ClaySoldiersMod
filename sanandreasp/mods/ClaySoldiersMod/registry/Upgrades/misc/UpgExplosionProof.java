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
import net.minecraft.util.DamageSource;
import sanandreasp.mods.ClaySoldiersMod.client.render.UpgradeRenderHelper;
import sanandreasp.mods.ClaySoldiersMod.packet.PacketSendParticle;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.leftHand.UpgShearBladeL;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.rightHand.UpgShearBladeR;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class UpgExplosionProof extends MiscUpgrade
{
    
    @Override
    public void initUpgrade(IUpgradeEntity entity, NBTTagCompound nbt)
    {
        
    }
    
    @Override
    public ItemStack getItemStack(IUpgradeEntity entity)
    {
        return new ItemStack(Item.silk);
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
        if (source.isExplosion())
        {
            entity.breakUpgrade(thisUpgID);
            return 0F;
        }
        return initAmount;
    }
    
}
