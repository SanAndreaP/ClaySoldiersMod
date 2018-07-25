/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier;

import de.sanandrew.mods.claysoldiers.client.gui.lexicon.crafting.LexiconRenderBrickDoll;
import de.sanandrew.mods.claysoldiers.util.Resources;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconGroup;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconInst;
import de.sanandrew.mods.sanlib.api.client.lexicon.LexiconGroup;

public class LexiconGroupSoldiers
        extends LexiconGroup
{
    public static final String GRP_NAME = "soldiers";

    protected LexiconGroupSoldiers() {
        super(GRP_NAME, Resources.GUI_GROUPICON_SOLDIERS.resource);
    }

    public static void register(ILexiconInst registry) {
        registry.registerPageRender(new LexiconRenderBrickDoll());

        ILexiconGroup grp = new LexiconGroupSoldiers();
        registry.registerGroup(grp);

        grp.addEntry(new LexiconEntryBaseSoldier());
        grp.addEntry(new LexiconEntryDyeSoldier());
        grp.addEntry(new LexiconEntryGlassSoldier());
        grp.addEntry(new LexiconEntryMiscSoldier());
        grp.addEntry(new LexiconEntryWashSoldier());
        grp.addEntry(new LexiconEntryBrickDoll());
    }
}
