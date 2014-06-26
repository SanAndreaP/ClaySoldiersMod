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

public class UpgWool extends ItemUpgrade
{
    
    @Override
    public void initUpgrade(IUpgradeEntity entity, NBTTagCompound nbt)
    {
    }
    
    @Override
    public float onHit(IUpgradeEntity entity, DamageSource source,
            float initAmount)
    {
        int thisUpgID = CSMModRegistry.clayUpgRegistry.getIDByUpgrade(this);
        NBTTagCompound nbt = entity.getUpgradeNBT(thisUpgID);
        return initAmount / 4F;
    }
    
    @Override
    public ItemStack getItemStack(IUpgradeEntity attacker)
    {
        return new ItemStack(Block.cloth);
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
        if (cs.hasUpgrade(CSMModRegistry.clayUpgRegistry
                .getIDByUpgradeClass(UpgLeather.class)))
        {
            cs.playSound("step.cloth", 0.2F, ((cs.getRNG().nextFloat() - cs
                    .getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            this.initUpgrade(entity, nbt);
        }
        else
        {
            System.out.println(cs.hasUpgrade(CSMModRegistry.clayUpgRegistry
                    .getIDByUpgradeClass(UpgWool.class)));
            cs.deleteUpgrade(CSMModRegistry.clayUpgRegistry
                    .getIDByUpgradeClass(UpgWool.class));
        }
    }
    
    @Override
    public void onDrop(IUpgradeEntity entity, Random rnd)
    {
    }
    
}
