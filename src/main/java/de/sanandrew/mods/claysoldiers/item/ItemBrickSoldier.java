/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.item;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.util.CsmCreativeTabs;
import net.minecraft.item.Item;

public class ItemBrickSoldier
        extends Item
{
    public ItemBrickSoldier() {
        super();
        this.setCreativeTab(CsmCreativeTabs.MISC);
        this.setUnlocalizedName(CsmConstants.ID + ":doll_brick_soldier");
        this.setMaxDamage(0);
        this.maxStackSize = 16;
        this.setRegistryName(CsmConstants.ID, "doll_brick_soldier");
    }
}
