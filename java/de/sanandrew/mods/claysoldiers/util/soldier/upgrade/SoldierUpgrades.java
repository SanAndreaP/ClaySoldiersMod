/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.FMLLog;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.behavior.UpgradeFermSpiderEye;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.behavior.UpgradeNetherwart;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.behavior.UpgradeWheat;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.core.*;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.lefthand.*;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc.*;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc.enchantment.*;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.righthand.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Level;

import java.util.*;

public final class SoldierUpgrades
{
    public static final String UPG_EGG = "egg";
    public static final String UPG_BONE = "bone";
    public static final String UPG_BOWL = "bowl";
    public static final String UPG_CLAY = "clay";
    public static final String UPG_COAL = "coal";
    public static final String UPG_FOOD = "food";
    public static final String UPG_SNOW = "snow";
    public static final String UPG_WOOL = "wool";
    public static final String UPG_ARROW = "arrow";
    public static final String UPG_BRICK = "brick";
    public static final String UPG_FLINT = "flint";
    public static final String UPG_GLASS = "glass";
    public static final String UPG_PAPER = "paper";
    public static final String UPG_STICK = "stick";
    public static final String UPG_SUGAR = "sugar";
    public static final String UPG_WHEAT = "wheat";
    public static final String UPG_CACTUS = "string";
    public static final String UPG_GRAVEL = "gravel";
    public static final String UPG_STRING = "string";
    public static final String UPG_EMERALD = "emerald";
    public static final String UPG_FEATHER = "feather";
    public static final String UPG_LEATHER = "leather";
    public static final String UPG_BLAZEROD = "blazerod";
    public static final String UPG_LILYPADS = "lilypads";
    public static final String UPG_MOB_HEAD = "skull";
    public static final String UPG_REDSTONE = "redstone";
    public static final String UPG_GLOWSTONE = "glowstone";
    public static final String UPG_GOLDMELON = "goldmelon";
    public static final String UPG_GUNPOWDER = "gunpowder";
    public static final String UPG_SHEARLEFT = "shear_l";
    public static final String UPG_SUGARCANE = "sugarcane";
    public static final String UPG_ENDERPEARL = "enderpearl";
    public static final String UPG_FIRECHARGE = "firecharge";
    public static final String UPG_GOLD_INGOT = "gold_ingot";
    public static final String UPG_IRON_BLOCK = "iron_block";
    public static final String UPG_IRON_INGOT = "iron_ingot";
    public static final String UPG_MAGMACREAM = "magmacream";
    public static final String UPG_NETHERWART = "netherwart";
    public static final String UPG_SHEARRIGHT = "shear_r";
    public static final String UPG_SLIMEBALLS = "slimeball";
    public static final String UPG_WOODBUTTON = "woodbutton";
    public static final String UPG_GOLD_NUGGET = "gold_nugget";
    public static final String UPG_STONEBUTTON = "stonebutton";
    public static final String UPG_WHEAT_SEEDS = "wheat_seeds";
    public static final String UPG_BLAZE_POWDER = "blaze_powder";
    public static final String UPG_MUSHROOM_RED = "red_mushroom";
    public static final String UPG_NETHER_BRICK = "nether_brick";
    public static final String UPG_FERMSPIDEREYE = "spidereye_ferm";
    public static final String UPG_FIREWORK_STAR = "firework";
    public static final String UPG_NETHER_QUARTZ = "nether_quartz";
    public static final String UPG_MUSHROOM_BROWN = "brown_mushroom";
    public static final String UPG_DIAMOND_ITEM = "diamond";
    public static final String UPG_DIAMOND_BLOCK = "diamond_block";
    public static final String UPG_GHAST_TEAR = "ghasttear";

    private static final Map<String, ASoldierUpgrade> NAME_TO_UPGRADE_MAP_ = Maps.newHashMap();
    private static final Map<ASoldierUpgrade, String> UPGRADE_TO_NAME_MAP_ = Maps.newHashMap();
    private static final Map<Pair<Item, Integer>, ASoldierUpgrade> ITEM_TO_UPGRADE_MAP_ = Maps.newHashMap();
    private static final Map<ASoldierUpgrade, Byte> UPGRADE_TO_RENDER_ID_MAP_ = Maps.newHashMap();
    private static final Map<Byte, ASoldierUpgrade> RENDER_ID_TO_UPGRADE_MAP_ = Maps.newHashMap();

    private static byte currRenderId = 0;

    public static void registerUpgrade(String name, ItemStack item, ASoldierUpgrade instance) {
        registerUpgrade(name, item, instance, -1);
    }

    public static void registerUpgrade(String name, ItemStack item, ASoldierUpgrade instance, int clientRenderId) {
        registerUpgrade(name, new ItemStack[] { item }, instance, clientRenderId);
    }

    public static void registerUpgrade(String name, ItemStack[] items, ASoldierUpgrade instance) {
        registerUpgrade(name, items, instance, -1);
    }

    public static void registerUpgrade(String name, ItemStack[] items, ASoldierUpgrade instance, int clientRenderId) {
        NAME_TO_UPGRADE_MAP_.put(name, instance);
        UPGRADE_TO_NAME_MAP_.put(instance, name);
        for( ItemStack upgradeItem : items ) {
            if( upgradeItem != null ) {
                ITEM_TO_UPGRADE_MAP_.put(Pair.with(upgradeItem.getItem(), upgradeItem.getItemDamage()), instance);
            }
        }

        if( clientRenderId >= 0 ) {
            if( clientRenderId > 127 ) {
                FMLLog.log(ClaySoldiersMod.MOD_LOG, Level.WARN, "The Upgrade \"%s\" cannot be bound to the render ID! The render ID is greater than 127!", name);
            } else if( RENDER_ID_TO_UPGRADE_MAP_.containsKey((byte) clientRenderId) ) {
                FMLLog.log(ClaySoldiersMod.MOD_LOG, Level.WARN, "The Upgrade \"%s\" cannot be bound to the render ID! The render ID is already registered!", name);
            } else {
                UPGRADE_TO_RENDER_ID_MAP_.put(instance, (byte) clientRenderId);
                RENDER_ID_TO_UPGRADE_MAP_.put((byte) clientRenderId, instance);
            }
        }
    }

    public static ASoldierUpgrade getUpgrade(String name) {
        return NAME_TO_UPGRADE_MAP_.get(name);
    }

    public static String getName(ASoldierUpgrade upgrade) {
        return UPGRADE_TO_NAME_MAP_.get(upgrade);
    }

    public static ASoldierUpgrade getUpgrade(ItemStack item) {
        if( item != null ) {
            Pair<Item, Integer> itemData = Pair.with(item.getItem(), OreDictionary.WILDCARD_VALUE);
            if( ITEM_TO_UPGRADE_MAP_.containsKey(itemData) ) {
                return ITEM_TO_UPGRADE_MAP_.get(itemData);
            } else {
                itemData = Pair.with(item.getItem(), item.getItemDamage());
                if( ITEM_TO_UPGRADE_MAP_.containsKey(itemData) ) {
                    return ITEM_TO_UPGRADE_MAP_.get(itemData);
                }
            }
        }

        return null;
    }

    public static byte getRenderId(ASoldierUpgrade upgrade) {
        if( UPGRADE_TO_RENDER_ID_MAP_.containsKey(upgrade) ) {
            return UPGRADE_TO_RENDER_ID_MAP_.get(upgrade);
        } else {
            return -1;
        }
    }

    public static ASoldierUpgrade getUpgrade(int renderId) {
        return RENDER_ID_TO_UPGRADE_MAP_.get((byte) renderId);
    }

    public static Set<Byte> getRegisteredRenderIds() {
        return RENDER_ID_TO_UPGRADE_MAP_.keySet();
    }

    public static byte getNewRenderId() {
        if( currRenderId == 127 ) {
            throw new RenderIdException();
        }
        return currRenderId++;
    }

    public static void initialize() {
        // upgrades no single items and render ID
        registerUpgrade(UPG_SHEARLEFT, (ItemStack) null, new UpgradeShearBladeLeft(), getNewRenderId());
        registerUpgrade(UPG_SHEARRIGHT, (ItemStack) null, new UpgradeShearBladeRight(), getNewRenderId());
        registerUpgrade(UPG_GLASS, (ItemStack) null, new UpgradeGlass(), getNewRenderId());
        registerUpgrade(UPG_WOOL, (ItemStack) null, new UpgradeWool(), getNewRenderId());

        // upgrades with single items and render ID
        registerUpgrade(UPG_STICK, new ItemStack(Items.stick), new UpgradeStick(), getNewRenderId());
        registerUpgrade(UPG_BLAZEROD, new ItemStack(Items.blaze_rod), new UpgradeBlazeRod(), getNewRenderId());
        registerUpgrade(UPG_LEATHER, new ItemStack(Items.leather), new UpgradeLeather(), getNewRenderId());
        registerUpgrade(UPG_EGG, new ItemStack(Items.egg), new UpgradeEgg(), getNewRenderId());
        registerUpgrade(UPG_WOODBUTTON, new ItemStack(Blocks.wooden_button, 1, OreDictionary.WILDCARD_VALUE), new UpgradeWoodButton(), getNewRenderId());
        registerUpgrade(UPG_STONEBUTTON, new ItemStack(Blocks.stone_button, 1, OreDictionary.WILDCARD_VALUE), new UpgradeStoneButton(), getNewRenderId());
        registerUpgrade(UPG_IRON_INGOT, new ItemStack(Items.iron_ingot), new UpgradeIronIngot(), getNewRenderId());
        registerUpgrade(UPG_BRICK, new ItemStack(Items.brick), new UpgradeBrick(), getNewRenderId());
        registerUpgrade(UPG_GRAVEL, new ItemStack(Blocks.gravel), new UpgradeGravel(), getNewRenderId());
        registerUpgrade(UPG_FIRECHARGE, new ItemStack(Items.fire_charge), new UpgradeFirecharge(), getNewRenderId());
        registerUpgrade(UPG_BOWL, new ItemStack(Items.bowl), new UpgradeBowl(), getNewRenderId());
        registerUpgrade(UPG_GOLD_NUGGET, new ItemStack(Items.gold_nugget), new UpgradeGoldNugget(), getNewRenderId());
        registerUpgrade(UPG_LILYPADS, new ItemStack(Blocks.waterlily), new UpgradeLilyPads(), getNewRenderId());
        registerUpgrade(UPG_GOLDMELON, new ItemStack(Items.speckled_melon), new UpgradeGoldMelon(), getNewRenderId());
        registerUpgrade(UPG_FLINT, new ItemStack(Items.flint), new UpgradeFlint(), getNewRenderId());
        registerUpgrade(UPG_FEATHER, new ItemStack(Items.feather), new UpgradeFeather(), getNewRenderId());
        registerUpgrade(UPG_IRON_BLOCK, new ItemStack(Blocks.iron_block), new UpgradeIronBlock(), getNewRenderId());
        registerUpgrade(UPG_BONE, new ItemStack(Items.bone), new UpgradeBone(), getNewRenderId());
        registerUpgrade(UPG_NETHER_QUARTZ, new ItemStack(Items.quartz), new UpgradeNetherQuartz(), getNewRenderId());
        registerUpgrade(UPG_ENDERPEARL, new ItemStack(Items.ender_pearl), new UpgradeEnderpearl(), getNewRenderId());
        registerUpgrade(UPG_MAGMACREAM, new ItemStack(Items.magma_cream), new UpgradeMagmacream(), getNewRenderId());
        registerUpgrade(UPG_MOB_HEAD, new ItemStack(Items.skull, 1, OreDictionary.WILDCARD_VALUE), new UpgradeMobHead(), getNewRenderId());
        registerUpgrade(UPG_FIREWORK_STAR, new ItemStack(Items.firework_charge, 1, OreDictionary.WILDCARD_VALUE), new UpgradeFireworkStar(), getNewRenderId());
        registerUpgrade(UPG_GOLD_INGOT, new ItemStack(Items.gold_ingot), new UpgradeGoldIngot(), getNewRenderId());
        registerUpgrade(UPG_DIAMOND_ITEM, new ItemStack(Items.diamond), new UpgradeDiamond(), getNewRenderId());
        registerUpgrade(UPG_DIAMOND_BLOCK, new ItemStack(Blocks.diamond_block), new UpgradeDiamondBlock(), getNewRenderId());

        // upgrades with single items and no render ID
        registerUpgrade(UPG_COAL, new ItemStack(Items.coal, 1, OreDictionary.WILDCARD_VALUE), new UpgradeCoal());
        registerUpgrade(UPG_WHEAT, new ItemStack(Items.wheat), new UpgradeWheat());
        registerUpgrade(UPG_NETHERWART, new ItemStack(Items.nether_wart), new UpgradeNetherwart());
        registerUpgrade(UPG_FERMSPIDEREYE, new ItemStack(Items.fermented_spider_eye), new UpgradeFermSpiderEye());
        registerUpgrade(UPG_SUGAR, new ItemStack(Items.sugar), new UpgradeSugar());
        registerUpgrade(UPG_SLIMEBALLS, new ItemStack(Items.slime_ball), new UpgradeSlimeball());
        registerUpgrade(UPG_STRING, new ItemStack(Items.string), new UpgradeString());
        registerUpgrade(UPG_CACTUS, new ItemStack(Blocks.cactus), new UpgradeCactus());
        registerUpgrade(UPG_CLAY, new ItemStack(Items.clay_ball), new UpgradeClay());
        registerUpgrade(UPG_SUGARCANE, new ItemStack(Items.reeds), new UpgradeSugarCane());
        registerUpgrade(UPG_ARROW, new ItemStack(Items.arrow), new UpgradeArrow());
        registerUpgrade(UPG_WHEAT_SEEDS, new ItemStack(Items.wheat_seeds), new UpgradeWheatSeeds());
        registerUpgrade(UPG_MUSHROOM_RED, new ItemStack(Blocks.red_mushroom), new UpgradeRedMushroom());
        registerUpgrade(UPG_MUSHROOM_BROWN, new ItemStack(Blocks.brown_mushroom), new UpgradeBrownMushroom());
        registerUpgrade(UPG_BLAZE_POWDER, new ItemStack(Items.blaze_powder), new UpgradeBlazePowder());
        registerUpgrade("wool_helper", new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), new UpgradeHelperWool());
        registerUpgrade(UPG_GHAST_TEAR, new ItemStack(Items.ghast_tear), new UpgradeGhastTear());

        // upgrades with multiple items and render ID
        registerUpgrade(UPG_GLOWSTONE, new ItemStack[] {
                                new ItemStack(Items.glowstone_dust),
                                new ItemStack(Blocks.glowstone)
                        }, new UpgradeGlowstone(), getNewRenderId()
        );
        registerUpgrade(UPG_GUNPOWDER, new ItemStack[] {
                                new ItemStack(Items.gunpowder),
                                new ItemStack(Blocks.tnt)
                        }, new UpgradeGunpowder(), getNewRenderId()
        );
        registerUpgrade(UPG_SNOW, new ItemStack[] {
                                new ItemStack(Blocks.snow),
                                new ItemStack(Blocks.snow_layer),
                                new ItemStack(Items.snowball)
                        }, new UpgradeSnow(), getNewRenderId()
        );
        registerUpgrade(UPG_EMERALD, new ItemStack[] {
                                new ItemStack(Blocks.emerald_block),
                                new ItemStack(Items.emerald)
                        }, new UpgradeEmerald(), getNewRenderId()
        );
        registerUpgrade(UPG_PAPER, new ItemStack[] {
                                new ItemStack(Items.paper),
                                new ItemStack(Items.book)
                        }, new UpgradePaper(), getNewRenderId()
        );

        // upgrades with multiple items and no render ID
        registerUpgrade("shear_helper", new ItemStack[] {
                                new ItemStack(RegistryItems.shearBlade),
                                new ItemStack(Items.shears)
                        }, new UpgradeHelperShearBlade()
        );
        registerUpgrade(UPG_NETHER_BRICK, new ItemStack[] {
                                new ItemStack(Blocks.nether_brick),
                                new ItemStack(Items.netherbrick)
                        }, new UpgradeNetherBrick()
        );
        registerUpgrade("glass_helper", new ItemStack[] {
                                new ItemStack(Blocks.glass),
                                new ItemStack(Blocks.glass_pane),
                                new ItemStack(Blocks.stained_glass, 1, OreDictionary.WILDCARD_VALUE),
                                new ItemStack(Blocks.stained_glass_pane, 1, OreDictionary.WILDCARD_VALUE),
                                new ItemStack(Items.glass_bottle)
                        }, new UpgradeHelperGlass()
        );
        registerUpgrade(UPG_FOOD, getFoodItems(), new UpgradeFood());
        registerUpgrade(UPG_REDSTONE, new ItemStack[] {
                                new ItemStack(Items.redstone),
                                new ItemStack(Blocks.redstone_block)
                        }, new UpgradeRedstone()
        );
    }

    public static void logUpgradeCount() {
        FMLLog.log(ClaySoldiersMod.MOD_LOG, Level.DEBUG, "There are %d soldier upgrades registered. %d of them use client renderers!", NAME_TO_UPGRADE_MAP_.size(),
                   currRenderId + 1
        );
    }

    private static ItemStack[] getFoodItems() {
        List<ItemStack> stackList = new ArrayList<>();

        Iterator iter = Item.itemRegistry.iterator();
        for( Object elem = null; iter.hasNext(); elem = iter.next() ) {
            if( elem instanceof ItemFood && !UpgradeFood.isFoodExcluded((ItemFood) elem) ) {
                stackList.add(new ItemStack((ItemFood) elem, 1, OreDictionary.WILDCARD_VALUE));
            }
        }

        return stackList.toArray(new ItemStack[stackList.size()]);
    }

    public static class RenderIdException
            extends RuntimeException
    {
        public RenderIdException() {
            super("There are no more render IDs for soldier upgrade available!");
        }
    }
}
