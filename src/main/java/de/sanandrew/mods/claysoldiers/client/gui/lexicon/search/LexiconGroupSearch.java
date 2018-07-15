/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.search;

import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGroup;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconRegistry;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.LexiconGroup;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.misc.LexiconEntryChestAccess;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.misc.LexiconEntryDispenser;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.misc.LexiconEntryDisruptor;
import de.sanandrew.mods.claysoldiers.util.Resources;

public final class LexiconGroupSearch
        extends LexiconGroup
{
    public static final String GRP_NAME = "search";

    protected LexiconGroupSearch() {
        super(GRP_NAME, Resources.GUI_GROUPICON_SEARCH.resource);
    }

    public static void register(ILexiconRegistry registry) {
        registry.registerPageRender(new LexiconRenderSearch());

        ILexiconGroup grp = new LexiconGroupSearch();
        registry.registerGroup(grp);

        grp.addEntry(new LexiconEntrySearch());
    }
}
