/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.upgrade;

import de.sanandrew.mods.claysoldiers.api.client.DummyHander;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.claysoldiers.util.Lang;
import de.sanandrew.mods.claysoldiers.util.Resources;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconGroup;
import de.sanandrew.mods.sanlib.api.client.lexicon.ILexiconInst;
import de.sanandrew.mods.sanlib.api.client.lexicon.LexiconGroup;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Comparator;

public class LexiconGroupUpgrades
        extends LexiconGroup
{
    public static final String GRP_NAME = "upgrades";

    protected LexiconGroupUpgrades() {
        super(GRP_NAME, Resources.GUI_GROUPICON_UPGRADES.resource);
    }

    @Override
    public void sortEntries() {
        this.entries.sort(UpgradeSorter.INSTANCE);
    }

    public static void register(ILexiconInst registry) {
        registry.registerPageRender(new LexiconRenderUpgradeType());

        ILexiconGroup grp = new LexiconGroupUpgrades();
        registry.registerGroup(grp);

        grp.addEntry(new LexiconEntryUpgradeType(EnumUpgradeType.MAIN_HAND, new ItemStack(Items.ARROW)));
        grp.addEntry(new LexiconEntryUpgradeType(EnumUpgradeType.OFF_HAND, new ItemStack(ItemRegistry.SOLDIER_SHIELD)));
        grp.addEntry(new LexiconEntryUpgradeType(EnumUpgradeType.CORE, new ItemStack(Items.IRON_INGOT)));
        grp.addEntry(new LexiconEntryUpgradeType(EnumUpgradeType.BEHAVIOR, new ItemStack(Items.WHEAT)));
        grp.addEntry(new LexiconEntryUpgradeType(EnumUpgradeType.MISC, new ItemStack(Items.EGG)));
        grp.addEntry(new LexiconEntryUpgradeType(EnumUpgradeType.ENHANCEMENT, new ItemStack(Items.FLINT)));

        UpgradeRegistry.INSTANCE.getUpgrades().forEach(upg -> grp.addEntry(new LexiconEntryUpgrade(upg)));
    }

    private static final class UpgradeSorter
            implements Comparator<ILexiconEntry>
    {
        public static final UpgradeSorter INSTANCE = new UpgradeSorter();

        private UpgradeSorter() { }

        @Override
        public int compare(ILexiconEntry o1, ILexiconEntry o2) {
            if( o1 instanceof LexiconEntryUpgrade && o2 instanceof LexiconEntryUpgrade ) {
                LexiconEntryUpgrade l1 = (LexiconEntryUpgrade) o1;
                LexiconEntryUpgrade l2 = (LexiconEntryUpgrade) o2;

                EnumUpgradeType t1m = l1.upgrade.getType(DummyHander.MAIN);
                EnumUpgradeType t1o = l1.upgrade.getType(DummyHander.OFF);
                EnumUpgradeType t2m = l2.upgrade.getType(DummyHander.MAIN);
                EnumUpgradeType t2o = l2.upgrade.getType(DummyHander.OFF);

                if( t1m == t2m || t1o == t2o ) {
                    return getEntryName(l1).compareTo(getEntryName(l2));
                } else {
                    int cmp = Integer.compare(t1m.ordinal(), t2m.ordinal());
                    return cmp == 0 ? Integer.compare(t1o.ordinal(), t2o.ordinal()) : cmp;
                }
            } else if( o1 instanceof LexiconEntryUpgradeType && o2 instanceof LexiconEntryUpgradeType ) {
                LexiconEntryUpgradeType l1 = (LexiconEntryUpgradeType) o1;
                LexiconEntryUpgradeType l2 = (LexiconEntryUpgradeType) o2;

                return Integer.compare(l1.type.ordinal(), l2.type.ordinal());
            } else {
                return o1 instanceof LexiconEntryUpgradeType ? -1 : o2 instanceof LexiconEntryUpgradeType ? 1 : 0;
            }
        }

        private static String getEntryName(ILexiconEntry entry) {
            return Lang.translate(Lang.LEXICON_ENTRY_NAME.get(entry.getGroupId(), entry.getId()));
        }
    }
}
