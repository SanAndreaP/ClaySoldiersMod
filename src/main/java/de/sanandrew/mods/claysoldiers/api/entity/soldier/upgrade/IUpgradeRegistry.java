/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.ISoldierUpgrade;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public interface IUpgradeRegistry
{
    boolean registerUpgrade(UUID id, ISoldierUpgrade upgrade);

    @Nullable
    ISoldierUpgrade getUpgrade(UUID id);

    @Nullable
    ISoldierUpgrade getUpgrade(ItemStack stack);

    @Nullable
    UUID getId(ISoldierUpgrade upgrade);

    List<ISoldierUpgrade> getUpgrades();
}
