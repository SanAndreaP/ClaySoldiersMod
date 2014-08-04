package de.sanandrew.mods.claysoldiers.util.soldier.upgrade;

import com.google.common.collect.Maps;
import com.google.common.primitives.Bytes;
import cpw.mods.fml.common.FMLLog;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import de.sanandrew.mods.claysoldiers.util.ModItems;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.behavior.UpgradeFermSpiderEye;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.behavior.UpgradeNetherwart;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.behavior.UpgradeWheat;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.core.*;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.lefthand.*;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc.*;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.righthand.UpgradeBlazeRod;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.righthand.UpgradeGoldMelon;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.righthand.UpgradeShearBladeRight;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.righthand.UpgradeStick;
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
        registerUpgrade(name, new ItemStack[]{item}, instance, clientRenderId);
    }

    public static void registerUpgrade(String name, ItemStack[] items, ASoldierUpgrade instance) {
        registerUpgrade(name, items, instance, -1);
    }

    public static void registerUpgrade(String name, ItemStack[] items, ASoldierUpgrade instance, int clientRenderId) {
        NAME_TO_UPGRADE_MAP_.put(name, instance);
        UPGRADE_TO_NAME_MAP_.put(instance, name);
        for( ItemStack upgradeItem : items ) {
            ITEM_TO_UPGRADE_MAP_.put(Pair.with(upgradeItem.getItem(), upgradeItem.getItemDamage()), instance);
        }

        if( clientRenderId >= 0 ) {
            if( clientRenderId > 127 ) {
                FMLLog.log(CSM_Main.MOD_LOG, Level.WARN, "The Upgrade \"%s\" cannot be bound to the render ID! The render ID is greater than 127!", name);
            } else if( RENDER_ID_TO_UPGRADE_MAP_.containsKey((byte) clientRenderId) ) {
                FMLLog.log(CSM_Main.MOD_LOG, Level.WARN, "The Upgrade \"%s\" cannot be bound to the render ID! The render ID is already registered!", name);
            } else {
                UPGRADE_TO_RENDER_ID_MAP_.put(instance, (byte) clientRenderId);
                RENDER_ID_TO_UPGRADE_MAP_.put((byte) clientRenderId, instance);
            }
        }
    }

    public static ASoldierUpgrade getUpgrade(String name) {
        return NAME_TO_UPGRADE_MAP_.get(name);
    }

    public static String getNameFromUpgrade(ASoldierUpgrade upgrade) {
        return UPGRADE_TO_NAME_MAP_.get(upgrade);
    }

    public static ASoldierUpgrade getUpgradeFromItem(ItemStack item) {
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

    public static byte getRenderIdFromUpgrade(ASoldierUpgrade upgrade) {
        if( UPGRADE_TO_RENDER_ID_MAP_.containsKey(upgrade) ) {
            return UPGRADE_TO_RENDER_ID_MAP_.get(upgrade);
        } else {
            return -1;
        }
    }

    public static ASoldierUpgrade getUpgradeFromRenderId(int renderId) {
        return RENDER_ID_TO_UPGRADE_MAP_.get((byte) renderId);
    }

    public static byte[] getRegisteredRenderIds() {
        return Bytes.toArray(RENDER_ID_TO_UPGRADE_MAP_.keySet());
    }

    public static byte getNewRenderId() {
        if( currRenderId == 127 ) {
            throw new RenderIdException();
        }
        return currRenderId++;
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
    public static final String UPG_IRON_INGOT = "iron_ingot";
    public static final String UPG_GLOWSTONE = "glowstone";
    public static final String UPG_GUNPOWDER = "gunpowder";
    public static final String UPG_BRICK = "brick";
    public static final String UPG_SLIMEBALLS = "slimeball";
    public static final String UPG_GRAVEL = "gravel";
    public static final String UPG_SNOW = "snow";
    public static final String UPG_FIRECHARGE = "firecharge";
    public static final String UPG_EMERALD = "emerald";
    public static final String UPG_BOWL = "bowl";
    public static final String UPG_STRING = "string";
    public static final String UPG_CACTUS = "string";
    public static final String UPG_NETHER_BRICK = "nether_brick";
    public static final String UPG_CLAY = "clay";
    public static final String UPG_GOLD_NUGGET = "gold_nugget";
    public static final String UPG_LILYPADS = "lilypads";
    public static final String UPG_GOLDMELON = "goldmelon";

    static {
        registerUpgrade(UPG_STICK, new ItemStack(Items.stick), new UpgradeStick(), getNewRenderId());
        registerUpgrade(UPG_BLAZEROD, new ItemStack(Items.blaze_rod), new UpgradeBlazeRod(), getNewRenderId());
        registerUpgrade(UPG_LEATHER, new ItemStack(Items.leather), new UpgradeLeather(), getNewRenderId());
        registerUpgrade(UPG_WOOL, new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), new UpgradeWool(), getNewRenderId());
        registerUpgrade(UPG_COAL, new ItemStack(Items.coal, 1, OreDictionary.WILDCARD_VALUE), new UpgradeCoal());
        registerUpgrade(UPG_EGG, new ItemStack(Items.egg), new UpgradeEgg(), getNewRenderId());
        registerUpgrade(UPG_WOODBUTTON, new ItemStack(Blocks.wooden_button, 1, OreDictionary.WILDCARD_VALUE), new UpgradeWoodButton(), getNewRenderId());
        registerUpgrade(UPG_STONEBUTTON, new ItemStack(Blocks.stone_button, 1, OreDictionary.WILDCARD_VALUE), new UpgradeStoneButton(), getNewRenderId());
        registerUpgrade("shear_helper", new ItemStack[] {
                                new ItemStack(ModItems.shearBlade),
                                new ItemStack(Items.shears)
                        }, new UpgradeHelperShearBlade());
        registerUpgrade(UPG_SHEARLEFT, new ItemStack(ModItems.shearBlade, 1, 1), new UpgradeShearBladeLeft(), getNewRenderId());
        registerUpgrade(UPG_SHEARRIGHT, new ItemStack(ModItems.shearBlade, 1, 1), new UpgradeShearBladeRight(), getNewRenderId());
        registerUpgrade(UPG_WHEAT, new ItemStack(Items.wheat), new UpgradeWheat());
        registerUpgrade(UPG_NETHERWART, new ItemStack(Items.nether_wart), new UpgradeNetherwart());
        registerUpgrade(UPG_FERMSPIDEREYE, new ItemStack(Items.fermented_spider_eye), new UpgradeFermSpiderEye());
        registerUpgrade(UPG_SUGAR, new ItemStack(Items.sugar), new UpgradeSugar());
        registerUpgrade(UPG_IRON_INGOT, new ItemStack(Items.iron_ingot), new UpgradeIronIngot(), getNewRenderId());
        registerUpgrade(UPG_GLOWSTONE, new ItemStack[] {
                                new ItemStack(Items.glowstone_dust),
                                new ItemStack(Blocks.glowstone)
                        }, new UpgradeGlowstone(), getNewRenderId());
        registerUpgrade(UPG_GUNPOWDER, new ItemStack[] {
                                new ItemStack(Items.gunpowder),
                                new ItemStack(Blocks.tnt)
                        }, new UpgradeGunpowder(), getNewRenderId());
        registerUpgrade(UPG_BRICK, new ItemStack(Items.brick), new UpgradeBrick(), getNewRenderId());
        registerUpgrade(UPG_SLIMEBALLS, new ItemStack(Items.slime_ball), new UpgradeSlimeball());
        registerUpgrade(UPG_GRAVEL, new ItemStack(Blocks.gravel), new UpgradeGravel(), getNewRenderId());
        registerUpgrade(UPG_SNOW, new ItemStack[] {
                                new ItemStack(Blocks.snow),
                                new ItemStack(Blocks.snow_layer),
                                new ItemStack(Items.snowball)
                        }, new UpgradeSnow(), getNewRenderId());
        registerUpgrade(UPG_FIRECHARGE, new ItemStack(Items.fire_charge), new UpgradeFirecharge(), getNewRenderId());
        registerUpgrade(UPG_EMERALD, new ItemStack[] {
                                new ItemStack(Blocks.emerald_block),
                                new ItemStack(Items.emerald)
                        }, new UpgradeEmerald(), getNewRenderId());
        registerUpgrade(UPG_BOWL, new ItemStack(Items.bowl), new UpgradeBowl(), getNewRenderId());
        registerUpgrade(UPG_STRING, new ItemStack(Items.string), new UpgradeString());
        registerUpgrade(UPG_CACTUS, new ItemStack(Blocks.cactus), new UpgradeCactus());
        registerUpgrade(UPG_NETHER_BRICK, new ItemStack [] {
                                new ItemStack(Blocks.nether_brick),
                                new ItemStack(Items.netherbrick)
                        }, new UpgradeNetherBrick());
        registerUpgrade(UPG_CLAY, new ItemStack(Items.clay_ball), new UpgradeClay());
        registerUpgrade(UPG_GOLD_NUGGET, new ItemStack(Items.gold_nugget), new UpgradeGoldNugget(), getNewRenderId());
        registerUpgrade(UPG_LILYPADS, new ItemStack(Blocks.waterlily), new UpgradeLilyPads(), getNewRenderId());
        registerUpgrade(UPG_GOLDMELON, new ItemStack(Items.speckled_melon), new UpgradeGoldMelon(), getNewRenderId());

        FMLLog.log(CSM_Main.MOD_LOG, Level.DEBUG, "There are %d upgrades registered by default! %d of them use client renderers!", NAME_TO_UPGRADE_MAP_.size(),
                   currRenderId + 1
        );
    }

    public static class RenderIdException extends RuntimeException {
        public RenderIdException() {
            super("There are no more render IDs for soldier upgrade available!");
        }
    }
}
