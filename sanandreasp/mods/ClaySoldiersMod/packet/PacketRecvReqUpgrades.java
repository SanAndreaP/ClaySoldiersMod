package sanandreasp.mods.ClaySoldiersMod.packet;

import java.io.DataInputStream;
import java.io.IOException;

import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PacketRecvReqUpgrades extends PacketBase
{
	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) throws IOException {
		Entity e = player.worldObj.getEntityByID(iStream.readInt());
		if( e != null && e instanceof IUpgradeEntity ) {
			PacketSendSldUpgrades.sendUpgrades((IUpgradeEntity) e);
		}
	}
}
