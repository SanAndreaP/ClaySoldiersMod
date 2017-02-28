/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.soldier;

import net.minecraft.entity.EntityCreature;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public interface ISoldier<T extends EntityCreature & ISoldier<T>>
{
    boolean canMove();

    void setMovable(boolean move);

    void setBreathableUnderwater(boolean breathable);

    Team getSoldierTeam();

    T getEntity();

    int getTextureType();

    int getTextureId();

    void setNormalTextureId(byte id);

    void setRareTextureId(byte id);

    void setUniqueTextureId(byte id);

    void destroyUpgrade(ISoldierUpgrade upgrade);

    ISoldierUpgradeInst addUpgrade(ISoldierUpgrade upgrade, ItemStack stack);

    boolean hasUpgrade(ItemStack stack);

    boolean hasUpgrade(UUID id);

    boolean hasUpgrade(ISoldierUpgrade upgrade);
}
