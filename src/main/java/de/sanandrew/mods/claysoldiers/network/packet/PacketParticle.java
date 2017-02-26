/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network.packet;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.EnumParticle;
import de.sanandrew.mods.sanlib.lib.Tuple;
import de.sanandrew.mods.sanlib.lib.network.AbstractMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.Arrays;

public class PacketParticle
        extends AbstractMessage<PacketParticle>
{
    private EnumParticle particle;
    private Tuple data;

    public PacketParticle() {}

    public PacketParticle(EnumParticle particle, Tuple data) {
        this.particle = particle;
        this.data = data;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleClientMessage(PacketParticle pkt, EntityPlayer player) {
        int dim = pkt.data.getValue(0);
        double x = pkt.data.getValue(1);
        double y = pkt.data.getValue(2);
        double z = pkt.data.getValue(3);
        Object[] additData = pkt.data.toArray();
        if( additData.length > 4 ) {
            additData = Arrays.copyOfRange(additData, 4, additData.length);
        } else {
            additData = null;
        }

        ClaySoldiersMod.proxy.spawnParticle(pkt.particle, dim, x, y, z, additData);
    }

    @Override
    public void handleServerMessage(PacketParticle pkt, EntityPlayer player) { }

    @Override
    public void fromBytes(ByteBuf buf) {
        try( ByteBufInputStream bis = new ByteBufInputStream(buf) ) {
            this.particle = EnumParticle.VALUES[bis.readShort()];
        } catch( IOException | ArrayIndexOutOfBoundsException ex ) {
            CsmConstants.LOG.log(Level.ERROR, "Cannot read particle packet!", ex);
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        try( ByteBufOutputStream bos = new ByteBufOutputStream(buf) ) {
            bos.writeShort(this.particle.ordinal());
            this.data.writeToStream(bos);
        } catch( IOException ex ) {
            CsmConstants.LOG.log(Level.ERROR, "Cannot write particle packet!", ex);
        }
    }
}
