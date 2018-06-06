/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.mount;

import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGroup;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconRegistry;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.LexiconGroup;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.crafting.LexiconRenderBrickDoll;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.crafting.LexiconRenderCraftingGrid;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier.LexiconEntryBaseSoldier;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier.LexiconEntryBrickDoll;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier.LexiconEntryDyeSoldier;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier.LexiconEntryGlassSoldier;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier.LexiconEntryMiscSoldier;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.soldier.LexiconEntryWashSoldier;
import de.sanandrew.mods.claysoldiers.util.Resources;

public class LexiconGroupMounts
        extends LexiconGroup
{
    public static final String GRP_NAME = "mounts";

    protected LexiconGroupMounts() {
        super(GRP_NAME, Resources.GUI_GROUPICON_MOUNTS.resource);
    }

    public static void register(ILexiconRegistry registry) {
        ILexiconGroup grp = new LexiconGroupMounts();
        registry.registerGroup(grp);

        grp.addEntry(new LexiconEntryMounts());
        grp.addEntry(new LexiconEntryHorse());
        grp.addEntry(new LexiconEntryPegasus());
        grp.addEntry(new LexiconEntryTurtle());
        grp.addEntry(new LexiconEntryBunny());
    }
}
