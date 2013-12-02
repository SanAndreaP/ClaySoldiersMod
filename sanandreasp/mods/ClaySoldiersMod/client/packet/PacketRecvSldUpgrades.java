package sanandreasp.mods.ClaySoldiersMod.client.packet;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import sanandreasp.mods.ClaySoldiersMod.packet.PacketBase;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;

public class PacketRecvSldUpgrades extends PacketBase
{
	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) throws IOException {
		Entity e = player.worldObj.getEntityByID(iStream.readInt());
		if( e != null && e instanceof IUpgradeEntity ) {
			IUpgradeEntity upgMan = (IUpgradeEntity)e;
			upgMan.clearUpgrades();
			while( true ) {
				try {
					upgMan.addUpgrade(iStream.readInt());
				} catch(IOException exception) {
					break;
				}
			}
		}
	}
}
