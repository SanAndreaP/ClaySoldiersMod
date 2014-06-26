package sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc;

import java.util.Random;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.rightHand.UpgStick;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class UpgFlint extends ItemUpgrade
{
    
    @Override
    public void initUpgrade(IUpgradeEntity entity, NBTTagCompound nbt)
    {
    }
    
    @Override
    public ItemStack getItemStack(IUpgradeEntity attacker)
    {
        return new ItemStack(Item.flint);
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
                .getIDByUpgradeClass(UpgStick.class)))
        {
            cs.playSound("step.wood", 0.2F, ((cs.getRNG().nextFloat() - cs
                    .getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            cs.getUpgradeNBT(
                    CSMModRegistry.clayUpgRegistry
                            .getIDByUpgradeClass(UpgStick.class)).setBoolean(
                    "sharp", true);
        }
        else
        {
            System.out.println(cs.hasUpgrade(CSMModRegistry.clayUpgRegistry
                    .getIDByUpgradeClass(UpgFlint.class)));
            cs.deleteUpgrade(CSMModRegistry.clayUpgRegistry
                    .getIDByUpgradeClass(UpgFlint.class));
        }
    }
    
    @Override
    public void onDrop(IUpgradeEntity entity, Random rnd)
    {
    }
    
}
