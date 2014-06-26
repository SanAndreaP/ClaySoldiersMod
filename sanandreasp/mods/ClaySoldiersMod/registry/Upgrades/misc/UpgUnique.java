package sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class UpgUnique extends ItemUpgrade
{
    
    @Override
    public void initUpgrade(IUpgradeEntity entity, NBTTagCompound nbt)
    {
        
    }
    
    @Override
    public ItemStack getItemStack(IUpgradeEntity attacker)
    {
        return new ItemStack(Block.melon);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getHeldItem(IUpgradeEntity attacker)
    {
        return null;
    }
    
    public void onPickup(IUpgradeEntity entity, EntityItem item,
            NBTTagCompound nbt)
    {
        EntityClayMan cs = (EntityClayMan) entity.getEntity();
        // if(cs.hasUpgrade(CSMModRegistry.clayUpgRegistry.getIDByUpgradeClass(UpgLeather.class)))
        {
            cs.playSound("random.hurt", 0.2F, ((cs.getRNG().nextFloat() - cs
                    .getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            this.initUpgrade(entity, nbt);
            cs.setUniqTexture(0);
        }
        // else
        // {
        // System.out.println(cs.hasUpgrade(CSMModRegistry.clayUpgRegistry.getIDByUpgradeClass(UpgWool.class)));
        // cs.deleteUpgrade(CSMModRegistry.clayUpgRegistry.getIDByUpgradeClass(UpgWool.class));
        // }
    }
    
    @Override
    public void onDrop(IUpgradeEntity entity, Random rnd)
    {
    }
    
}
