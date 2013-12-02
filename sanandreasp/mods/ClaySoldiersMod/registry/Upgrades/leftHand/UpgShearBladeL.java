package sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.leftHand;

import java.util.Random;

import sanandreasp.mods.ClaySoldiersMod.packet.PacketSendParticle;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc.UpgEggScent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class UpgShearBladeL extends LeftHandUpgrade
{

    @Override
    public void initUpgrade(IUpgradeEntity entity, NBTTagCompound nbt) {
        nbt.setByte("points", (byte) 25);
    }
    
	@Override
	public float onAttack(IUpgradeEntity attacker, EntityLivingBase target, float initAmount) {
		int thisUpgID = CSMModRegistry.clayUpgRegistry.getIDByUpgrade(this);
		NBTTagCompound nbt = attacker.getUpgradeNBT(thisUpgID);
		byte pts = (byte)(nbt.getByte("points") - 1);
		nbt.setByte("points", pts);
		if( pts <= 0 ) attacker.breakUpgrade(thisUpgID);
		return initAmount + 1.0F + attacker.getEntity().getRNG().nextFloat();
	}

	@Override
	public ItemStack getItemStack(IUpgradeEntity attacker) {
		return new ItemStack(CSMModRegistry.shearBlade);
	}

	@Override
	public ItemStack getHeldItem(IUpgradeEntity attacker) {
		return this.getItemStack(attacker);
	}
}
