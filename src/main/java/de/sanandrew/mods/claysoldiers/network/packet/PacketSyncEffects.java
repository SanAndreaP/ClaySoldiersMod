/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network.packet;

import de.sanandrew.mods.claysoldiers.api.soldier.effect.ISoldierEffect;
import de.sanandrew.mods.claysoldiers.api.soldier.effect.ISoldierEffectInst;
import de.sanandrew.mods.claysoldiers.entity.soldier.EntityClaySoldier;
import de.sanandrew.mods.claysoldiers.registry.effect.EffectRegistry;
import de.sanandrew.mods.sanlib.lib.network.AbstractMessage;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import de.sanandrew.mods.sanlib.lib.util.UuidUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PacketSyncEffects
        extends AbstractMessage<PacketSyncEffects>
{
    private int soldierId;
    private ISoldierEffect[] effects;
    private Integer[] durations;
    private boolean add;
    private Map<ISoldierEffect, NBTTagCompound> effectNBT = new HashMap<>();

    public PacketSyncEffects() { }

    public PacketSyncEffects(EntityClaySoldier soldier, boolean add, ISoldierEffectInst... effects) {
        this.add = add;
        this.effects = Arrays.stream(effects).map(ISoldierEffectInst::getEffect).toArray(ISoldierEffect[]::new);
        this.durations = Arrays.stream(effects).map(ISoldierEffectInst::getDurationLeft).toArray(Integer[]::new);

        this.soldierId = soldier.getEntityId();
        if( this.add ) {
            Arrays.stream(this.effects).forEach(entry -> {
                if( entry.syncNbtData() ) {
                    this.effectNBT.put(entry, soldier.getEffectInstance(entry).getNbtData());
                }
            });
        }
    }

    @Override
    public void handleClientMessage(PacketSyncEffects pkt, EntityPlayer player) {
        Entity entity = player.world.getEntityByID(pkt.soldierId);
        if( entity instanceof EntityClaySoldier ) {
            pkt.applyEffects((EntityClaySoldier) entity);
        }
    }

    public void applyEffects(EntityClaySoldier soldier) {
        for( int i = 0, max = this.effects.length; i < max; i++ ) {
            ISoldierEffect eff = this.effects[i];
            int duration = this.durations[i];
            if( eff != null && this.add ) {
                ISoldierEffectInst inst = soldier.addEffect(eff, duration);
                if( this.effectNBT.containsKey(eff) ) {
                    inst.setNbtData(this.effectNBT.get(eff));
                }
            } else {
                soldier.expireEffect(eff);
            }
        }
    }

    @Override
    public void handleServerMessage(PacketSyncEffects pkt, EntityPlayer player) { }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.add = buf.readBoolean();
        this.soldierId = buf.readInt();
        this.effects = new ISoldierEffect[buf.readInt()];
        this.durations = new Integer[this.effects.length];

        for( int i = 0, max = this.effects.length; i < max; i++ ) {
            String idStr = ByteBufUtils.readUTF8String(buf);
            if( UuidUtils.isStringUuid(idStr) ) {
                this.effects[i] = EffectRegistry.INSTANCE.getEffect(UUID.fromString(idStr));
                this.durations[i] = buf.readInt();

                if( this.add && this.effects[i].syncNbtData() ) {
                    NBTTagCompound newNbt = new NBTTagCompound();
                    this.effects[i].readSyncData(buf, newNbt);
                    this.effectNBT.put(this.effects[i], newNbt);
                }
            }
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.add);
        buf.writeInt(this.soldierId);
        buf.writeInt(this.effects.length);

        for( int i = 0, max = this.effects.length; i < max; i++ ) {
            ISoldierEffect eff = this.effects[i];
            int duration = this.durations[i];
            UUID id = EffectRegistry.INSTANCE.getId(eff);

            ByteBufUtils.writeUTF8String(buf, MiscUtils.defIfNull(id, UuidUtils.EMPTY_UUID).toString());
            buf.writeInt(duration);

            if( this.add && this.effectNBT.containsKey(eff) ) {
                eff.writeSyncData(buf, this.effectNBT.get(eff));
            }
        }
    }
}
