/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.client;

import de.sanandrew.mods.claysoldiers.api.entity.soldier.upgrade.IHandedUpgradeable;

public class DummyHander
        implements IHandedUpgradeable
{
    public static final DummyHander MAIN = new DummyHander(true);
    public static final DummyHander OFF = new DummyHander(false);

    private final boolean mainHand;

    private DummyHander(boolean mainHand) {
        this.mainHand = mainHand;
    }

    @Override
    public boolean hasMainHandUpgrade() {
        return this.mainHand;
    }

    @Override
    public boolean hasOffHandUpgrade() {
        return !this.mainHand;
    }
}
