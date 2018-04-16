/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.gui.lexicon.upgrades;

import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconEntry;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconGroup;
import de.sanandrew.mods.claysoldiers.api.client.lexicon.ILexiconRegistry;
import de.sanandrew.mods.claysoldiers.api.soldier.IHandedUpgradeable;
import de.sanandrew.mods.claysoldiers.api.soldier.upgrade.EnumUpgradeType;
import de.sanandrew.mods.claysoldiers.client.gui.lexicon.LexiconGroup;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeRegistry;
import de.sanandrew.mods.claysoldiers.util.Resources;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Comparator;

public class LexiconGroupUpgrades
        extends LexiconGroup
{
    public static final String GRP_NAME = "upgrades";

    public LexiconGroupUpgrades() {
        super(GRP_NAME, Resources.GUI_GROUPICON_UPGRADES.resource);
    }

    @Override
    public void sortEntries() {
        this.entries.sort(UpgradeSorter.INSTANCE);
    }

    public static void register(ILexiconRegistry registry) {
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

                EnumUpgradeType t1m = l1.upgrade.getType(Hander.MAIN);
                EnumUpgradeType t1o = l1.upgrade.getType(Hander.OFF);
                EnumUpgradeType t2m = l2.upgrade.getType(Hander.MAIN);
                EnumUpgradeType t2o = l2.upgrade.getType(Hander.OFF);

                if( t1m == t2m || t1o == t2o ) {
                    return l1.getEntryName().compareTo(l2.getEntryName());
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
    }

    public static class Hander
            implements IHandedUpgradeable
    {
        public static final Hander MAIN = new Hander(true);
        public static final Hander OFF = new Hander(false);

        private final boolean mainHand;

        private Hander(boolean mainHand) {
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
}
