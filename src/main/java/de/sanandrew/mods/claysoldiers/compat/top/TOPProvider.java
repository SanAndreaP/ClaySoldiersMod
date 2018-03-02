/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.compat.top;

import mcjty.theoneprobe.api.ITheOneProbe;

import java.util.function.Function;

public class TOPProvider
        implements Function<ITheOneProbe, Void>
{
    @Override
    public Void apply(ITheOneProbe probe) {
        probe.registerEntityDisplayOverride(new TOPEntityDisplayOverride());

        return null;
    }
}
