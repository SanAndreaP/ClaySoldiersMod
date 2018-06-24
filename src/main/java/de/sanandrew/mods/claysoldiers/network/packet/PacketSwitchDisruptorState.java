/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.network.packet;

import de.sanandrew.mods.claysoldiers.api.IDisruptable;
import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.sanlib.lib.network.AbstractMessage;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PacketSwitchDisruptorState
        extends AbstractMessage<PacketSwitchDisruptorState>
{
    private boolean forward;

    public PacketSwitchDisruptorState() { }

    public PacketSwitchDisruptorState(boolean forward) {
        this.forward = forward;
    }

    @Override
    public void handleClientMessage(PacketSwitchDisruptorState packet, EntityPlayer player) { }

    @Override
    public void handleServerMessage(PacketSwitchDisruptorState packet, EntityPlayer player) {
        ItemStack stack = player.inventory.getCurrentItem();
        if( ItemStackUtils.isItem(stack, ItemRegistry.DISRUPTOR) ) {
            int stateId = ItemDisruptor.getState(stack).ordinal();
            int maxStateId = IDisruptable.DisruptState.VALUES.length - 1;
            if( packet.forward && stateId == maxStateId ) {
                stateId = 0;
            } else if( !packet.forward && stateId == 0 ) {
                stateId = maxStateId;
            } else {
                stateId = stateId + (packet.forward ? 1 : -1);
            }
            ItemDisruptor.setState(stack, IDisruptable.DisruptState.VALUES[stateId]);
            player.inventoryContainer.detectAndSendChanges();
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.forward = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.forward);
    }
}
