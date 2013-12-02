package sanandreasp.mods.ClaySoldiersMod.client.packet;

import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import sanandreasp.mods.ClaySoldiersMod.client.particle.ParticleHelper;
import sanandreasp.mods.ClaySoldiersMod.packet.PacketBase;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;

public class PacketRecvParticle extends PacketBase
{
	@Override
	public void handle(DataInputStream iStream, EntityPlayer player) throws IOException {
	    short partID = iStream.readShort();
	    switch( partID ) {
            case 0:
                ParticleHelper.onSpawnSldCritical(iStream.readFloat(), iStream.readFloat(), iStream.readFloat());
                break;
    
            default:
                break;
        }
	}
}
