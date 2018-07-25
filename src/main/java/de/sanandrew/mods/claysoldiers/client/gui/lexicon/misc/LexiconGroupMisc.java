/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.misc;

import de.sanandrew.mods.claysoldiers.util.Resources;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconGroup;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconInst;
import de.sanandrew.mods.sanlib.api.client.lexicon.LexiconGroup;

public class LexiconGroupMisc
        extends LexiconGroup
{
    public static final String GRP_NAME = "misc";

    protected LexiconGroupMisc() {
        super(GRP_NAME, Resources.GUI_GROUPICON_MISC.resource);
    }

    public static void register(ILexiconInst registry) {
        ILexiconGroup grp = new LexiconGroupMisc();
        registry.registerGroup(grp);

        grp.addEntry(new LexiconEntryDisruptor());
        grp.addEntry(new LexiconEntryDispenser());
        grp.addEntry(new LexiconEntryChestAccess());
    }
}
