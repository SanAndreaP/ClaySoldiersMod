package de.sanandrew.mods.claysoldiers.util;

import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.network.ServerPacketHandler;

/**
 * @author SanAndreasP
 * @version 1.0
 */
public class CommonProxy
{
    public void modInit() {
        CSM_Main.channel.register(new ServerPacketHandler());
    }

    public void spawnParticles(byte particleId, Tuple data) { }
}
