/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network;

import de.sanandrew.mods.claysoldiers.network.packet.PacketParticle;
import de.sanandrew.mods.claysoldiers.network.packet.PacketSyncUpgrades;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.sanlib.lib.network.AbstractMessage;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public final class PacketManager
{
    public static void initialize() {
        registerMessage(ClaySoldiersMod.network, PacketParticle.class, 0, Side.CLIENT);
        registerMessage(ClaySoldiersMod.network, PacketSyncUpgrades.class, 1, Side.CLIENT);
    }

    public static void sendToAllAround(IMessage message, int dim, double x, double y, double z, double range) {
        ClaySoldiersMod.network.sendToAllAround(message, new NetworkRegistry.TargetPoint(dim, x, y, z, range));
    }

    public static void sendToAllAround(IMessage message, int dim, BlockPos pos, double range) {
        sendToAllAround(message, dim, pos.getX(), pos.getY(), pos.getZ(), range);
    }

    public static void sendToAll(IMessage message) {
        ClaySoldiersMod.network.sendToAll(message);
    }

    public static void sendToServer(IMessage message) {
        ClaySoldiersMod.network.sendToServer(message);
    }

    public static void sendToPlayer(IMessage message, EntityPlayerMP player) {
        ClaySoldiersMod.network.sendTo(message, player);
    }

    public static <T extends AbstractMessage<T> & IMessageHandler<T, IMessage>> void registerMessage (SimpleNetworkWrapper network, Class<T> clazz, int id, Side side) {
        network.registerMessage(clazz, clazz, id, side);
    }
}
