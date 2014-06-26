package sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.leftHand.UpgShield;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.rightHand.UpgStick;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class UpgIronShield extends ItemUpgrade
{
    
    @Override
    public void initUpgrade(IUpgradeEntity entity, NBTTagCompound nbt)
    {
    }
    
    @Override
    public ItemStack getItemStack(IUpgradeEntity attacker)
    {
        return new ItemStack(Block.blockIron);
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
        EntityClayMan cs = (EntityClayMan) entity;
        if (cs.hasUpgrade(CSMModRegistry.clayUpgRegistry
                .getIDByUpgradeClass(UpgShield.class)))
        {
            cs.playSound("step.metal", 0.2F, ((cs.getRNG().nextFloat() - cs
                    .getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
            // System.out.println("UPGRADAAAD");
            cs.studded = 1;
            cs.getUpgradeNBT(
                    CSMModRegistry.clayUpgRegistry
                            .getIDByUpgradeClass(UpgShield.class)).setBoolean(
                    "upgraded", true);
        }
        else
        {
            cs.studded = 0;
            System.out.println(cs.hasUpgrade(CSMModRegistry.clayUpgRegistry
                    .getIDByUpgradeClass(UpgIronShield.class)));
            cs.deleteUpgrade(CSMModRegistry.clayUpgRegistry
                    .getIDByUpgradeClass(UpgIronShield.class));
        }
    }
    
}
