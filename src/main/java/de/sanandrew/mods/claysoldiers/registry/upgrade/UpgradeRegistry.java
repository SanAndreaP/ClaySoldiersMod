/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade;

import com.google.common.collect.ImmutableList;
import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgFunctions;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.IUpgradeRegistry;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.UpgradeFunctions;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class UpgradeRegistry
        implements IUpgradeRegistry
{
    public static final UpgradeRegistry INSTANCE = new UpgradeRegistry();

    private final List<ISoldierUpgrade> upgrades;
    private final Map<UUID, ISoldierUpgrade> idToUpgradeMap;
    private final Map<ISoldierUpgrade, UUID> upgradeToIdMap;
    private final Map<ItemStack, ISoldierUpgrade> stackToUpgradeMap;

    private UpgradeRegistry() {
        this.idToUpgradeMap = new HashMap<>();
        this.upgradeToIdMap = new HashMap<>();
        this.stackToUpgradeMap = new HashMap<>();
        this.upgrades = new ArrayList<>();
    }

    @Override
    public boolean registerUpgrade(UUID id, ISoldierUpgrade upgrade) {
        if( id == null || upgrade == null ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Upgrade ID and instance cannot be null nor empty for ID %s!", id));
            return false;
        }

        ItemStack[] upgItems = upgrade.getStacks();
        if( upgItems.length < 1 || Arrays.stream(upgItems).anyMatch(itm -> !ItemStackUtils.isValid(itm)) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Upgrade items are invalid for ID %s!", id));
            return false;
        }

        if( this.idToUpgradeMap.containsKey(id) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Duplicate Upgrade ID %s!", id));
            return false;
        }

        if( this.upgradeToIdMap.containsKey(upgrade) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Duplicate Upgrade instances for %s!", id));
            return false;
        }

        for( ItemStack existingItm : this.stackToUpgradeMap.keySet() ) {
            if( Arrays.stream(upgItems).anyMatch(itm -> ItemStackUtils.areEqualNbtFit(itm, existingItm, false, existingItm.getItemDamage() != OreDictionary.WILDCARD_VALUE)) ) {
                CsmConstants.LOG.log(Level.WARN, String.format("Duplicate Upgrade Item %s for ID %s!", existingItm, id));
                return false;
            }
        }

        this.idToUpgradeMap.put(id, upgrade);
        this.upgradeToIdMap.put(upgrade, id);
        Arrays.stream(upgItems).forEach(stk -> stackToUpgradeMap.put(stk.copy(), upgrade));
        this.upgrades.add(upgrade);

        return true;
    }

    @Nullable
    @Override
    public ISoldierUpgrade getUpgrade(UUID id) {
        return this.idToUpgradeMap.get(id);
    }

    @Nullable
    @Override
    public UUID getId(ISoldierUpgrade upgrade) {
        return this.upgradeToIdMap.get(upgrade);
    }

    @Nullable
    @Override
    public ISoldierUpgrade getUpgrade(ItemStack stack) {
        if( !ItemStackUtils.isValid(stack) ) {
            return null;
        }

        return this.stackToUpgradeMap.entrySet().stream()
                                     .filter(entry -> ItemStackUtils.areEqualNbtFit(stack, entry.getKey(), false, entry.getKey().getItemDamage() != OreDictionary.WILDCARD_VALUE))
                                     .map(Map.Entry::getValue).findFirst().orElse(null);
    }

    @Override
    public List<ISoldierUpgrade> getUpgrades() {
        return ImmutableList.copyOf(this.upgrades);
    }

    private static final EnumUpgFunctions[] EMPTY_FUNCS = {};
    public static EnumUpgFunctions[] getFuncCalls(ISoldierUpgrade upgrade) {
        UpgradeFunctions a = upgrade.getClass().getAnnotation(UpgradeFunctions.class);
        if( a != null ) {
            return a.value();
        }

        return EMPTY_FUNCS;
    }
}
