/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.soldier;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public interface IUpgradeRegistry
{
    boolean registerUpgrade(UUID id, IUpgrade upgradeInst);

    @Nullable
    IUpgrade getUpgrade(UUID id);

    @Nullable
    IUpgrade getUpgrade(ItemStack stack);

    ArrayList<IUpgrade> getUpgrades();
}
