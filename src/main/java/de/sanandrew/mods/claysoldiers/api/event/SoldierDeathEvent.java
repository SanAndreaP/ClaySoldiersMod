/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.event;

import de.sanandrew.mods.claysoldiers.api.soldier.ISoldier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingEvent;

public class SoldierDeathEvent
        extends LivingEvent
{
    public final ISoldier<?> soldier;
    public final DamageSource dmgSource;
    public final NonNullList<ItemStack> drops;

    public SoldierDeathEvent(ISoldier<?> entity, DamageSource source, NonNullList<ItemStack> drops) {
        super(entity.getEntity());

        this.soldier = entity;
        this.dmgSource = source;
        this.drops = drops;
    }
}
