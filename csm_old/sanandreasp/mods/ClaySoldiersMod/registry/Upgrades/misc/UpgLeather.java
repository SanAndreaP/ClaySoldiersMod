package sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class UpgLeather extends MiscUpgrade {

    @Override
    public void initUpgrade(IUpgradeEntity entity, NBTTagCompound nbt) {
        nbt.setByte("points", (byte) 20);
    }
    
    @Override
    public float onHit(IUpgradeEntity entity, DamageSource source, float initAmount) {
        int thisUpgID = CSMModRegistry.clayUpgRegistry.getIDByUpgrade(this);
        NBTTagCompound nbt = entity.getUpgradeNBT(thisUpgID);
        byte pts = (byte)(nbt.getByte("points") - 1);
        nbt.setByte("points", pts);
        if( pts <= 0 ) entity.breakUpgrade(thisUpgID);
        return initAmount / 2F;
    }
    
    @Override
    public ItemStack getItemStack(IUpgradeEntity attacker) {
        return new ItemStack(Item.leather);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getHeldItem(IUpgradeEntity attacker) {
        return null;
    }

}
