/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util;

import cpw.mods.fml.common.network.IGuiHandler;
import de.sanandrew.core.manpack.util.javatuples.Tuple;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.ServerPacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class CommonProxy
    implements IGuiHandler
{
    public void modInit() {
        ClaySoldiersMod.channel.register(new ServerPacketHandler());
    }

    public void spawnParticles(byte particleId, Tuple particleData) {
    }

    public void applySoldierRenderFlags(int entityId, long upgFlags1, long upgFlags2, long effFlags1, long effFlags2) {
    }

    public void applyEffectNbt(int entityId, byte effectRenderId, NBTTagCompound nbt) {
    }

    public void applyUpgradeNbt(int entityId, byte upgradeRenderId, NBTTagCompound nbt) {
    }

    public void switchClayCam(boolean enable, EntityClayMan clayMan) {
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
}
