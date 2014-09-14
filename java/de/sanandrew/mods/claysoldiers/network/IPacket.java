/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network;

import de.sanandrew.core.manpack.util.javatuples.Tuple;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.network.INetHandler;

import java.io.IOException;

public interface IPacket
{
    /**
     * This is called when the packet is being received. Use this to read data from the packet.
     *
     * @param stream  The stream to read data from.
     * @param rawData The raw data buffer. ONLY refer to this if you're using it directly (for example through the {@link cpw.mods.fml.common.network.ByteBufUtils})!
     * @param handler An instance of the INetHandler interface.
     * @throws IOException
     */
    public void process(ByteBufInputStream stream, ByteBuf rawData, INetHandler handler) throws IOException;

    /**
     * This is called when the packet is getting prepared for being send. Use this to write data to the packet.
     *
     * @param stream    The stream to write data to.
     * @param dataTuple A tuple containing the data provided by the packet sending call.
     * @throws IOException
     */
    public void writeData(ByteBufOutputStream stream, Tuple dataTuple) throws IOException;
}
