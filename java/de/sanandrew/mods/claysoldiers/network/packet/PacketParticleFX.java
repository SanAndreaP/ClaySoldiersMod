package de.sanandrew.mods.claysoldiers.network.packet;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Quartet;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.network.IPacket;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.item.Item;
import net.minecraft.network.INetHandler;

import java.io.IOException;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class PacketParticleFX
    implements IPacket
{
    public static final byte FX_BREAK = 0;
    public static final byte FX_CRIT = 1;

    @Override
    public void process(ByteBufInputStream stream, INetHandler handler) throws IOException {
        Minecraft mc = Minecraft.getMinecraft();
        switch( stream.readByte() ) {
            case FX_BREAK: {
                Quartet<Double, Double, Double, String> data = Quartet.with(stream.readDouble(), stream.readDouble() + 0.5D, stream.readDouble(), stream.readUTF());
                Item item = (Item) Item.itemRegistry.getObject(data.getValue3());
                for (int i = 0; i < 5; i++) {
                    EntityBreakingFX fx = new EntityBreakingFX(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), item);
                    mc.effectRenderer.addEffect(fx);
                }
                break;
            }
            case FX_CRIT: {
                Triplet<Double, Double, Double> data = Triplet.with(stream.readDouble(), stream.readDouble() + 0.1D, stream.readDouble());
                for (int i = 0; i < 10; i++) {
                    double motX = SAPUtils.RANDOM.nextDouble() - 0.5D;
                    double motY = SAPUtils.RANDOM.nextDouble() * 0.5D;
                    double motZ = SAPUtils.RANDOM.nextDouble() - 0.5D;
                    EntityCritFX fx = new EntityCritFX(mc.theWorld, data.getValue0(), data.getValue1(), data.getValue2(), motX, motY, motZ);
                    mc.effectRenderer.addEffect(fx);
                }
                break;
            }
        }
    }

    @Override
    public void writeData(ByteBufOutputStream stream, Tuple dataTuple) throws IOException {
        byte particleId = (byte) dataTuple.getValue(0);
        stream.writeByte(particleId);
        switch( particleId ) {
            case FX_BREAK:
                stream.writeDouble((double) dataTuple.getValue(1));
                stream.writeDouble((double) dataTuple.getValue(2));
                stream.writeDouble((double) dataTuple.getValue(3));
                stream.writeUTF((String) dataTuple.getValue(4));
                break;
            case FX_CRIT:
                stream.writeDouble((double) dataTuple.getValue(1));
                stream.writeDouble((double) dataTuple.getValue(2));
                stream.writeDouble((double) dataTuple.getValue(3));
                break;
        }
    }
}
