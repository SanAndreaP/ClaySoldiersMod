/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.mount;

import de.sanandrew.mods.claysoldiers.util.Resources;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconGroup;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconInst;
import de.sanandrew.mods.sanlib.api.client.lexicon.LexiconGroup;

public class LexiconGroupMounts
        extends LexiconGroup
{
    public static final String GRP_NAME = "mounts";

    protected LexiconGroupMounts() {
        super(GRP_NAME, Resources.GUI_GROUPICON_MOUNTS.resource);
    }

    public static void register(ILexiconInst registry) {
        ILexiconGroup grp = new LexiconGroupMounts();
        registry.registerGroup(grp);

        grp.addEntry(new LexiconEntryMounts());
        grp.addEntry(new LexiconEntryHorse());
        grp.addEntry(new LexiconEntryPegasus());
        grp.addEntry(new LexiconEntryTurtle());
        grp.addEntry(new LexiconEntryBunny());
        grp.addEntry(new LexiconEntryGecko());
    }
}
