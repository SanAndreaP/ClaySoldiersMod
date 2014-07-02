package sanandreasp.mods.ClaySoldiersMod.registry.Upgrades;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;

public interface IUpgradeEntity {
	public NBTTagCompound getUpgradeNBT(int id);
	public void addUpgrade(int id);
	public EntityLivingBase getEntity();
	public void breakUpgrade(int id);
    public void deleteUpgrade(int id);
	public void clearUpgrades();
	public List<Integer>getUpgrades();
    public boolean hasUpgrade(int id);
}
