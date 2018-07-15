/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.info;

import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGroup;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconRegistry;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.LexiconGroup;
import de.sanandrew.mods.claysoldiers.util.Resources;

public final class LexiconGroupInfo
        extends LexiconGroup
{
    public static final String GRP_NAME = "info";

    protected LexiconGroupInfo() {
        super(GRP_NAME, Resources.GUI_GROUPICON_INFO.resource);
    }

    public static void register(ILexiconRegistry registry) {
        registry.registerPageRender(new LexiconRenderInfo());

        ILexiconGroup grp = new LexiconGroupInfo();
        registry.registerGroup(grp);

        grp.addEntry(new LexiconEntryInfo());
    }
}
