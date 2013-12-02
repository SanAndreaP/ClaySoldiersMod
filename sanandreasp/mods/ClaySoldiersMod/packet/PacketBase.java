package sanandreasp.mods.ClaySoldiersMod.packet;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;

public abstract class PacketBase {
	
	public Random rand = new Random();

    public abstract void handle(DataInputStream iStream, EntityPlayer player) throws IOException;

}
