/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgFunctions;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgradeInst;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.UpgradeFunctions;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.Upgrades;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import de.sanandrew.mods.sanlib.lib.util.UuidUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.mutable.MutableFloat;

import javax.annotation.Nonnull;
import java.util.UUID;

@UpgradeFunctions({EnumUpgFunctions.ON_OTHR_DESTROYED, EnumUpgFunctions.ON_DAMAGED})
public class UpgradeWool
        implements ISoldierUpgrade
{
    private static final ItemStack[] UPG_ITEMS = { new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE) };

    @Override
    @Nonnull
    public EnumUpgradeType getType(ISoldier<?> checker) {
        return EnumUpgradeType.ENHANCEMENT;
    }

    @Override
    @Nonnull
    public ItemStack[] getStacks() {
        return UPG_ITEMS;
    }

    @Override
    public boolean isApplicable(ISoldier<?> soldier, ItemStack stack) {
        return soldier.hasUpgrade(Upgrades.MC_LEATHER, EnumUpgradeType.MISC) || soldier.hasUpgrade(Upgrades.MC_RABBITHIDE, EnumUpgradeType.MISC);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean syncData() {
        return true;
    }

    @Override
    public boolean syncNbtData() {
        return true;
    }

    @Override
    public void writeSyncData(ByteBuf buf, NBTTagCompound nbt) {
        buf.writeInt(nbt.getInteger("color"));
    }

    @Override
    public void readSyncData(ByteBuf buf, NBTTagCompound nbt) {
        nbt.setInteger("color", buf.readInt());
    }

    @Override
    public void onAdded(ISoldier<?> soldier, ItemStack stack, ISoldierUpgradeInst upgradeInst) {
        if( !soldier.getEntity().world.isRemote ) {
            soldier.getEntity().playSound(SoundEvents.BLOCK_CLOTH_BREAK, 0.2F, ((MiscUtils.RNG.randomFloat() - MiscUtils.RNG.randomFloat()) * 0.7F + 1.0F) * 2.0F);
            upgradeInst.getNbtData().setInteger("color", stack.getItemDamage());
        }
    }

    @Override
    public void onDamaged(ISoldier<?> soldier, ISoldierUpgradeInst upgradeInst, Entity attacker, DamageSource dmgSource, MutableFloat damage) {
        damage.subtract(1.0F);
    }

    @Override
    public void onUpgradeDestroyed(ISoldier soldier, ISoldierUpgradeInst upgradeInst, ISoldierUpgradeInst destroyedUpgInst) {
        UUID dUpgId = UpgradeRegistry.INSTANCE.getId(destroyedUpgInst.getUpgrade());
        if( !soldier.getEntity().world.isRemote && (UuidUtils.areUuidsEqual(dUpgId, Upgrades.MC_LEATHER) || UuidUtils.areUuidsEqual(dUpgId, Upgrades.MC_RABBITHIDE)) ) {
            soldier.destroyUpgrade(this, this.getType(soldier), true);
        }
    }
}
