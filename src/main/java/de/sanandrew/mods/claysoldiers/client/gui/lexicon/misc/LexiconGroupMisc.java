/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.misc;

import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGroup;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconRegistry;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.LexiconGroup;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.mount.LexiconEntryBunny;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.mount.LexiconEntryGecko;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.mount.LexiconEntryHorse;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.mount.LexiconEntryMounts;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.mount.LexiconEntryPegasus;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.mount.LexiconEntryTurtle;
import de.sanandrew.mods.claysoldiers.util.Resources;

public class LexiconGroupMisc
        extends LexiconGroup
{
    public static final String GRP_NAME = "misc";

    protected LexiconGroupMisc() {
        super(GRP_NAME, Resources.GUI_GROUPICON_MISC.resource);
    }

    public static void register(ILexiconRegistry registry) {
        ILexiconGroup grp = new LexiconGroupMisc();
        registry.registerGroup(grp);

        grp.addEntry(new LexiconEntryDisruptor());
        grp.addEntry(new LexiconEntryDispenser());
    }
}
