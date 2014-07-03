package de.sanandrew.mods.claysoldiers.util.upgrades;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.FMLLog;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.apache.logging.log4j.Level;

import java.util.Map;

/**
 * @author SanAndreas
 * @version 1.0
 */
public final class SoldierUpgrades
{
    private static final Map<String, ISoldierUpgrade> NAME_TO_UPGRADE_MAP_ = Maps.newHashMap();
    private static final Map<ISoldierUpgrade, String> UPGRADE_TO_NAME_MAP_ = Maps.newHashMap();
    private static final Map<Pair<Item, Integer>, ISoldierUpgrade> ITEM_TO_UPGRADE_MAP_ = Maps.newHashMap();
    private static final Map<ISoldierUpgrade, Byte> UPGRADE_TO_RENDER_ID_MAP_ = Maps.newHashMap();
    private static final Map<Byte, ISoldierUpgrade> RENDER_ID_TO_UPGRADE_MAP_ = Maps.newHashMap();

    public static void registerUpgrade(String upgradeName, ItemStack upgradeItem, ISoldierUpgrade upgradeInst) {
        registerUpgrade(upgradeName, upgradeItem, upgradeInst, -1);
    }

    public static void registerUpgrade(String upgradeName, ItemStack upgradeItem, ISoldierUpgrade upgradeInst, int cltRenderId) {
        NAME_TO_UPGRADE_MAP_.put(upgradeName, upgradeInst);
        UPGRADE_TO_NAME_MAP_.put(upgradeInst, upgradeName);
        ITEM_TO_UPGRADE_MAP_.put(Pair.with(upgradeItem.getItem(), upgradeItem.getItemDamage()), upgradeInst);

        if( cltRenderId >= 0 ) {
            if( cltRenderId > 127 ) {
                FMLLog.log(CSM_Main.MOD_LOG, Level.WARN, "The Upgrade \"%s\" cannot be bound to the render ID! The render ID is greater than 127!", upgradeName);
            } else if( RENDER_ID_TO_UPGRADE_MAP_.containsKey((byte) cltRenderId) ) {
                FMLLog.log(CSM_Main.MOD_LOG, Level.WARN, "The Upgrade \"%s\" cannot be bound to the render ID! The render ID is already registered!", upgradeName);
            } else {
                UPGRADE_TO_RENDER_ID_MAP_.put(upgradeInst, (byte) cltRenderId);
                RENDER_ID_TO_UPGRADE_MAP_.put((byte) cltRenderId, upgradeInst);
            }
        }
    }

    public static ISoldierUpgrade getUpgradeFromName(String name) {
        return NAME_TO_UPGRADE_MAP_.get(name);
    }

    public static String getNameFromUpgrade(ISoldierUpgrade upgr) {
        return UPGRADE_TO_NAME_MAP_.get(upgr);
    }

    public static ISoldierUpgrade getUpgradeFromItem(ItemStack item) {
        if( item != null ) {
            Pair<Item, Integer> pair = Pair.with(item.getItem(), item.getItemDamage());
            if( ITEM_TO_UPGRADE_MAP_.containsKey(pair) ) {
                return ITEM_TO_UPGRADE_MAP_.get(pair);
            }
        }
        return null;
    }

    static {
        registerUpgrade("testUpg", new ItemStack(Item.getItemFromBlock(Blocks.command_block)), new TestUpgrade());
    }
}
