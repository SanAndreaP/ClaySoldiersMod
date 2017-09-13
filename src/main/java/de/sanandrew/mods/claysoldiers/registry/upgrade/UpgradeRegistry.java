/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade;

import com.google.common.collect.ImmutableList;
import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.IUpgradeRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.core.UpgradeBrick;
import de.sanandrew.mods.claysoldiers.registry.upgrade.core.UpgradeIronIngot;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeFlint;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeIronBlock;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeArrow;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeBlazeRod;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeBone;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeBowl;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeQuartz;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeSpeckledMelon;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeShearBlade;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeStick;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeThrowable;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeEgg;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeFeather;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeFood;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGlowstone;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGoggles;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeLeather;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeMagmaCream;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeRabbitHide;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeSugar;
import de.sanandrew.mods.claysoldiers.util.HashItemStack;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.item.ItemStack;
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
    private final Map<HashItemStack, ISoldierUpgrade> stackToUpgradeMap;

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

        HashItemStack hStacks[] = Arrays.stream(upgItems).map(HashItemStack::new).toArray(HashItemStack[]::new);
        for( HashItemStack existingItm : this.stackToUpgradeMap.keySet() ) {
            if( Arrays.stream(hStacks).anyMatch(existingItm::equals) ) {
                CsmConstants.LOG.log(Level.WARN, String.format("Duplicate Upgrade Item %s for ID %s!", existingItm.getStack(), id));
                return false;
            }
        }

        this.idToUpgradeMap.put(id, upgrade);
        this.upgradeToIdMap.put(upgrade, id);
        Arrays.stream(hStacks).forEach(stk -> stackToUpgradeMap.put(stk, upgrade));
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
        return MiscUtils.defIfNull(this.stackToUpgradeMap.get(new HashItemStack(stack)), this.stackToUpgradeMap.get(new HashItemStack(stack, true)));
    }

    @Override
    public List<ISoldierUpgrade> getUpgrades() {
        return ImmutableList.copyOf(this.upgrades);
    }

}
