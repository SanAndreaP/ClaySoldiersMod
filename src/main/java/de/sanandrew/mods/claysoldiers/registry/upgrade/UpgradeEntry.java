/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade;

import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;

import java.util.Objects;

public class UpgradeEntry
{
    public final ISoldierUpgrade upgrade;
    public final EnumUpgradeType type;

    public UpgradeEntry(ISoldierUpgrade upgrade, EnumUpgradeType type) {
        this.upgrade = upgrade;
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof UpgradeEntry ) {
            UpgradeEntry othr = (UpgradeEntry) obj;
            return Objects.equals(this.upgrade, othr.upgrade) && this.type == othr.type;
        }

        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.upgrade, this.type);
    }
}
