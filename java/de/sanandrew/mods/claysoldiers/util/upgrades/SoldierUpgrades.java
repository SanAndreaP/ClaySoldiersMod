package de.sanandrew.mods.claysoldiers.util.upgrades;

import com.google.common.collect.Maps;
import com.google.common.primitives.Bytes;
import cpw.mods.fml.common.FMLLog;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import de.sanandrew.mods.claysoldiers.util.ModItems;
import de.sanandrew.mods.claysoldiers.util.upgrades.behavior.UpgradeFermSpiderEye;
import de.sanandrew.mods.claysoldiers.util.upgrades.behavior.UpgradeNetherwart;
import de.sanandrew.mods.claysoldiers.util.upgrades.behavior.UpgradeWheat;
import de.sanandrew.mods.claysoldiers.util.upgrades.core.UpgradeIronIngot;
import de.sanandrew.mods.claysoldiers.util.upgrades.lefthand.UpgradeShearBladeLeft;
import de.sanandrew.mods.claysoldiers.util.upgrades.misc.*;
import de.sanandrew.mods.claysoldiers.util.upgrades.righthand.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
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

    public static void registerUpgrade(String name, ItemStack item, ISoldierUpgrade instance) {
        registerUpgrade(name, item, instance, -1);
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

    public static String getNameFromUpgrade(ISoldierUpgrade upgrade) {
        return UPGRADE_TO_NAME_MAP_.get(upgrade);
    }

    public static ISoldierUpgrade getUpgradeFromItem(ItemStack item) {
        if( item != null ) {
            Pair<Item, Integer> pair = Pair.with(item.getItem(), OreDictionary.WILDCARD_VALUE);
            if( ITEM_TO_UPGRADE_MAP_.containsKey(pair) ) {
                return ITEM_TO_UPGRADE_MAP_.get(pair);
            } else {
                pair = Pair.with(item.getItem(), item.getItemDamage());
                if( ITEM_TO_UPGRADE_MAP_.containsKey(pair) ) {
                    return ITEM_TO_UPGRADE_MAP_.get(pair);
                }
            }
        }

        return null;
    }

    public static byte getRenderIdFromUpgrade(ISoldierUpgrade upgrade) {
        if( UPGRADE_TO_RENDER_ID_MAP_.containsKey(upgrade) ) {
            return UPGRADE_TO_RENDER_ID_MAP_.get(upgrade);
        } else {
            return -1;
        }
    }

    public static ISoldierUpgrade getUpgradeFromRenderId(int renderId) {
        return RENDER_ID_TO_UPGRADE_MAP_.get((byte) renderId);
    }

    public static byte[] getAvailableRenderIds() {
        return Bytes.toArray(RENDER_ID_TO_UPGRADE_MAP_.keySet());
    }

    public static final String UPG_STICK = "stick";
    public static final String UPG_BLAZEROD = "blazerod";
    public static final String UPG_LEATHER = "leather";
    public static final String UPG_WOOL = "wool";
    public static final String UPG_COAL = "coal";
    public static final String UPG_EGG = "egg";
    public static final String UPG_WOODBUTTON = "woodbutton";
    public static final String UPG_STONEBUTTON = "stonebutton";
    public static final String UPG_SHEARLEFT = "shear_l";
    public static final String UPG_SHEARRIGHT = "shear_r";
    public static final String UPG_WHEAT = "wheat";
    public static final String UPG_NETHERWART = "netherwart";
    public static final String UPG_FERMSPIDEREYE = "spidereye_ferm";
    public static final String UPG_SUGAR = "sugar";
    public static final String UPG_IRONINGOT = "ironingot";

    static {
        registerUpgrade(UPG_STICK, new ItemStack(Items.stick), new UpgradeStick(), 0);
        registerUpgrade(UPG_BLAZEROD, new ItemStack(Items.blaze_rod), new UpgradeBlazeRod(), 1);
        registerUpgrade(UPG_LEATHER, new ItemStack(Items.leather), new UpgradeLeather(), 2);
        registerUpgrade(UPG_WOOL, new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), new UpgradeWool(), 3);
        registerUpgrade(UPG_COAL, new ItemStack(Items.coal, 1, OreDictionary.WILDCARD_VALUE), new UpgradeCoal());
        registerUpgrade(UPG_EGG, new ItemStack(Items.egg), new UpgradeEgg(), 4);
        registerUpgrade(UPG_WOODBUTTON, new ItemStack(Blocks.wooden_button, 1, OreDictionary.WILDCARD_VALUE), new UpgradeWoodButton(), 5);
        registerUpgrade(UPG_STONEBUTTON, new ItemStack(Blocks.stone_button, 1, OreDictionary.WILDCARD_VALUE), new UpgradeStoneButton(), 6);
        registerUpgrade("shear_helper", new ItemStack(ModItems.shearBlade), new UpgradeHelperShearBlade());
        registerUpgrade("shear_helper", new ItemStack(Items.shears), new UpgradeHelperShearBlade());
        registerUpgrade(UPG_SHEARLEFT, new ItemStack(ModItems.shearBlade, 1, 1), new UpgradeShearBladeLeft(), 7);
        registerUpgrade(UPG_SHEARRIGHT, new ItemStack(ModItems.shearBlade, 1, 1), new UpgradeShearBladeRight(), 8);
        registerUpgrade(UPG_WHEAT, new ItemStack(Items.wheat), new UpgradeWheat());
        registerUpgrade(UPG_NETHERWART, new ItemStack(Items.nether_wart), new UpgradeNetherwart());
        registerUpgrade(UPG_FERMSPIDEREYE, new ItemStack(Items.fermented_spider_eye), new UpgradeFermSpiderEye());
        registerUpgrade(UPG_SUGAR, new ItemStack(Items.sugar), new UpgradeSugar());
        registerUpgrade(UPG_IRONINGOT, new ItemStack(Items.iron_ingot), new UpgradeIronIngot(), 9);
//        registerUpgrade("testUpg", new ItemStack(Item.getItemFromBlock(Blocks.command_block)), new TestUpgrade(), 0);
    }
}
