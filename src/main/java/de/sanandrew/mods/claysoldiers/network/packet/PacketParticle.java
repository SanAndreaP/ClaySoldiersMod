/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network.packet;

import de.sanandrew.mods.sanlib.lib.network.AbstractMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class PacketParticle
        extends AbstractMessage<PacketParticle>
{
    @Override
    public void handleClientMessage(PacketParticle packetParticle, EntityPlayer entityPlayer) {

    }

    @Override
    public void handleServerMessage(PacketParticle packetParticle, EntityPlayer entityPlayer) {

    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }
}
