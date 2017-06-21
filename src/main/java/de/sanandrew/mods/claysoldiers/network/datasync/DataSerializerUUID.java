/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network.datasync;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.io.IOException;
import java.util.UUID;

public class DataSerializerUUID
        implements DataSerializer<UUID>
{
    public static final DataSerializerUUID INSTANCE = new DataSerializerUUID();

    @Override
    public void write(PacketBuffer buf, UUID value) {
        buf.writeLong(value.getMostSignificantBits());
        buf.writeLong(value.getLeastSignificantBits());
    }

    @Override
    public UUID read(PacketBuffer buf) throws IOException {
        return new UUID(buf.readLong(), buf.readLong());
    }

    @Override
    public DataParameter<UUID> createKey(int id) {
        return new DataParameter<>(id, this);
    }

    public static void initialize() {
        DataSerializers.registerSerializer(INSTANCE);
    }
}
