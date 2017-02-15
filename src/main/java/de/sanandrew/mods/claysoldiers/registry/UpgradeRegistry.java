/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.IUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.IUpgradeRegistry;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class UpgradeRegistry
        implements IUpgradeRegistry
{
    public static final UpgradeRegistry INSTANCE = new UpgradeRegistry();

    private final Map<UUID, IUpgrade> uuidUpgradeMap;
    private final Map<HashItemStack, IUpgrade> stackUpgradeMap;

    private UpgradeRegistry() {
        this.uuidUpgradeMap = new HashMap<>();
        this.stackUpgradeMap = new HashMap<>();
    }

    @Override
    public boolean registerUpgrade(UUID id, IUpgrade upgradeInst) {
        if( id == null || upgradeInst == null ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Upgrade ID and instance cannot be null nor empty for ID %s!", id));
            return false;

        }

        ItemStack upgItem = upgradeInst.getItem();
        if( !ItemStackUtils.isValid(upgItem) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Upgrade item is invalid for ID %s!", id));
            return false;
        }

        if( this.uuidUpgradeMap.containsKey(id) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Duplicate Upgrade ID %s!", id));
            return false;
        }

        HashItemStack hStack = new HashItemStack(upgItem);
        if( this.stackUpgradeMap.containsKey(hStack) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Duplicate Upgrade Item %s for ID %s!", upgItem, id));
            return false;
        }

        this.uuidUpgradeMap.put(id, upgradeInst);
        this.stackUpgradeMap.put(hStack, upgradeInst);

        return true;
    }

    @Nullable
    @Override
    public IUpgrade getUpgrade(UUID id) {
        return this.uuidUpgradeMap.get(id);
    }

    @Nullable
    @Override
    public IUpgrade getUpgrade(ItemStack stack) {
        return this.stackUpgradeMap.get(new HashItemStack(stack));
    }

    @Override
    public ArrayList<IUpgrade> getUpgrades() {
        return null;
    }

    private static class HashItemStack
    {
        private final ItemStack heldStack;

        public HashItemStack(ItemStack stack) {
            this.heldStack = stack;
        }

        @Override
        public int hashCode() {
            return this.hashCode(this.heldStack);
        }

        public int hashCode(ItemStack stack) {
            return 911 * stack.getItem().hashCode() ^ 401 * stack.getItemDamage() ^ 521 * (MiscUtils.defIfNull(stack.getTagCompound(), 1).hashCode());
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof ItemStack ? this.hashCode() == this.hashCode((ItemStack) obj) : obj instanceof HashItemStack ? this.hashCode() == obj.hashCode() : this == obj;
        }
    }
}
