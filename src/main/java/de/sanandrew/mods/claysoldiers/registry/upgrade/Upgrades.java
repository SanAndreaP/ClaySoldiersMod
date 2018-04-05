/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade;

import de.sanandrew.mods.claysoldiers.api.IUpgradeRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.behavior.UpgradeStandardBehavior;
import de.sanandrew.mods.claysoldiers.registry.upgrade.core.UpgradeBrick;
import de.sanandrew.mods.claysoldiers.registry.upgrade.core.UpgradeCactus;
import de.sanandrew.mods.claysoldiers.registry.upgrade.core.UpgradeIronIngot;
import de.sanandrew.mods.claysoldiers.registry.upgrade.core.UpgradeNetherBrick;
import de.sanandrew.mods.claysoldiers.registry.upgrade.core.UpgradeString;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeCoal;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeFlint;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeGoldIngot;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeIronBlock;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradePrismarineShard;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeSugarCane;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeWool;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeArrow;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeBlazeRod;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeBone;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeBowl;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeQuartz;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeShearBlade;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeSpeckledMelon;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeStick;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeThrowable;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeBlazePowder;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeBrownMushroom;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeButton;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeClay;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeConcretePowder;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeDiamond;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeDiamondBlock;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeEgg;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeEnderPearl;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeFeather;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeFireworkStar;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeFood;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGhastTear;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGlowstone;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGoggles;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGoldNugget;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGunpowder;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeLeather;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeLilyPad;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeMagmaCream;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradePaper;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradePrismarineCrystal;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeRabbitFoot;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeRabbitHide;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeRedMushroom;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeRedstone;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeSkull;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeSlimeball;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeSugar;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeWheatSeeds;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.UUID;

public final class Upgrades
{
    public static final UUID MH_STICK = UUID.fromString("31F0A3DB-F1A7-4418-9EA6-A9D0C900EB41");
    public static final UUID MH_ARROW = UUID.fromString("5CBFDDAB-B082-4DFF-A6DE-D207E068D9AD");
    public static final UUID MH_BONE = UUID.fromString("26CDF550-CE64-42FE-95DA-F36027B9EF08");
    public static final UUID MH_BLAZEROD = UUID.fromString("9EAF320D-1C8C-40F2-B8E4-6A4C18F9248E");
    public static final UUID MH_SPECKLEDMELON = UUID.fromString("062F5085-A46F-4CEA-8642-076D8A15A20B");
    public static final UUID MOH_SHEARBLADE = UUID.fromString("5CDCD4F9-1C94-485D-B043-2F9A779CF454");
    public static final UUID OH_GRAVEL = UUID.fromString("4A0232D9-3BE0-48BC-9BDB-DFE3EE458628");
    public static final UUID OH_SNOW = UUID.fromString("8F5DA9C0-9613-4816-B96A-8B6089B1140D");
    public static final UUID OH_FIRECHARGE = UUID.fromString("77F625B6-8C1D-405A-9EF5-50C25BFF7C03");
    public static final UUID OH_EMERALD = UUID.fromString("C2FD483D-DD45-4FFC-881A-1CF1A3AC9D00");
    public static final UUID OH_QUARTZ = UUID.fromString("8751AE74-134C-44E1-9405-55ED05E3416E");
    public static final UUID OH_BOWL = UUID.fromString("5FD5F54F-23C3-4D65-8D46-31F599F1E6CE");

    public static final UUID BH_WHEAT = UUID.fromString("14ADDFAB-F1E0-4002-8EEB-A469D8643F3C");
    public static final UUID BH_NETHERWART = UUID.fromString("3A11E52E-72FF-4197-A9EA-B890F3945944");
    public static final UUID BH_FERMSPIDEREYE = UUID.fromString("D2F734E1-2226-4B82-9ABA-FBB25E3DF80F");
    public static final UUID BH_ROTTENFLESH = UUID.fromString("61F2B921-9F98-4B9C-B662-5FE7FB5BED75");
    public static final UUID BH_SPONGE = UUID.fromString("CB4C3945-4D5F-4D51-AF2B-28740D6D0180");

    public static final UUID EM_FLINT = UUID.fromString("63342EEB-932B-4330-9B60-C5E21434A0B8");
    public static final UUID EM_IRONBLOCK = UUID.fromString("05113D07-A86D-45EA-AC7F-52E34567197A");
    public static final UUID EM_PRISMARINESHARD = UUID.fromString("0580F0B7-7293-4EF6-B966-CD29E23B3B43");
    public static final UUID EM_WOOL = UUID.fromString("18AFA548-DCF4-431E-A62E-96743FADC1B4");
    public static final UUID EM_SUGARCANE = UUID.fromString("F4F8224E-14E6-470F-AC7F-8D08B0B0FFB3");
    public static final UUID EM_COAL = UUID.fromString("B93A32C1-D29E-4936-855D-1D6795A033E7");
    public static final UUID EM_GOLDINGOT = UUID.fromString("BB415900-C367-4BC9-AC89-165DF5D43A4A");

    public static final UUID MC_EGG = UUID.fromString("4613D60F-B53C-4E75-99CA-0E2176B6D58D");
    public static final UUID MC_GLOWSTONE = UUID.fromString("6D1D540B-84DC-4009-BF29-134089104A3C");
    public static final UUID MC_FEATHER = UUID.fromString("453077F6-2930-49A1-A2EF-B1A9B0F8B55C");
    public static final UUID MC_FOOD = UUID.fromString("12A95822-000A-427A-9975-B492923162B7");
    public static final UUID MC_GLASS = UUID.fromString("F6C44798-5E23-4430-910E-E6CAE1305D58");
    public static final UUID MC_LEATHER = UUID.fromString("D5A7486E-B9D9-4298-B134-2FDCCD569036");
    public static final UUID MC_RABBITHIDE = UUID.fromString("ADD2D447-CDE2-43B1-AE9D-7EC301A9ECEA");
    public static final UUID MC_SUGAR = UUID.fromString("BF18CCDE-39A3-43D4-ABB1-322348AB0F0B");
    public static final UUID MC_MAGMACREAM = UUID.fromString("BACE82A7-4E3F-4EB1-81E3-730CF937D6AD");
    public static final UUID MC_GOLDNUGGET = UUID.fromString("4346F97A-6515-41EA-910A-A51866F39926");
    public static final UUID MC_REDMUSHROOM = UUID.fromString("DA241BE1-DB72-4662-B730-5F0D69D2D211");
    public static final UUID MC_GUNPOWDER = UUID.fromString("E6FC4C61-AE8F-4E41-8227-6AA3050F9D92");
    public static final UUID MC_FIREWORKSTAR = UUID.fromString("24C51CC1-D18F-412C-95A3-68C4C2F6CA12");
    public static final UUID MC_BROWNMUSHROOM = UUID.fromString("CE191A16-BD49-4F1E-BC37-940E82AAB2C9");
    public static final UUID MC_SKULL = UUID.fromString("4A2DBE3C-0323-4F96-A6EA-1A767C4C5C3B");
    public static final UUID MC_PAPER = UUID.fromString("632F57F9-0C85-4E51-B2B0-C2B565764507");
    public static final UUID MC_CONCRETEPOWDER = UUID.fromString("E78AA14E-4B9E-4AD3-A3A2-B15DCC4DFC0B");
    public static final UUID MC_BUTTON = UUID.fromString("AC93C3F9-E332-4FDE-ABA2-A1F63A46FACC");
    public static final UUID MC_CLAY = UUID.fromString("7BF219FE-2382-4DA9-BA6D-1C98CF35F0E6");
    public static final UUID MC_GHASTTEAR = UUID.fromString("A8680ACB-9103-4204-B377-8DDD1F83A9EB");
    public static final UUID MC_REDSTONE = UUID.fromString("2400DACC-9A48-40B6-BB27-5E6F53874702");
    public static final UUID MC_SLIMEBALL = UUID.fromString("D1AE80A1-A010-475A-A84A-93F0C3AAC93D");
    public static final UUID MC_DIAMOND = UUID.fromString("1E133692-2D78-4BF1-AD47-0982F1D55779");
    public static final UUID MC_DIAMONDBLOCK = UUID.fromString("C96C691E-E9C1-4EA5-8F01-7FE5A7269E24");
    public static final UUID MC_ENDERPEARL = UUID.fromString("EEF80CB3-FAF7-4544-BEB2-15833235CDC3");
    public static final UUID MC_WHEATSEEDS = UUID.fromString("CF75CEE1-AFBB-4F37-9936-28EC455F1258");
    public static final UUID MC_BLAZEPOWDER = UUID.fromString("B351F5B8-ABEA-45D5-8E2F-C972493EFB28");
    public static final UUID MC_LILYPAD = UUID.fromString("B95C8951-1393-4DBC-8DA5-F23AC140BEF7");
    public static final UUID MC_RABBITFOOT = UUID.fromString("B45956F9-9A18-4A7F-8D2A-23E729DA4563");
    public static final UUID MC_PRISMCRYSTALS = UUID.fromString("257E914C-4E72-47C0-908B-C9FC40FCACB2");

    public static final UUID CR_IRONINGOT = UUID.fromString("6426F05F-36C5-4F83-9D69-200CCBBA141D");
    public static final UUID CR_BRICK = UUID.fromString("00B12AB5-1E8D-43CA-A136-4BBB5E5970E0");
    public static final UUID CR_STRING = UUID.fromString("60E5B0C2-7364-4FCD-9C6D-58E21E3B9E2B");
    public static final UUID CR_CACTUS = UUID.fromString("41544F9D-0DE9-4D90-9934-E3534F2AB8E1");
    public static final UUID CR_NETHERBRICK = UUID.fromString("89A63F20-48D8-4F5E-9ADD-7952260FEBFD");

    public static void initialize(IUpgradeRegistry registry) {
        registry.registerUpgrade(MH_STICK, new UpgradeStick());
        registry.registerUpgrade(MH_ARROW, new UpgradeArrow());
        registry.registerUpgrade(MH_BONE, new UpgradeBone());
        registry.registerUpgrade(MH_BLAZEROD, new UpgradeBlazeRod());
        registry.registerUpgrade(MOH_SHEARBLADE, new UpgradeShearBlade());
        registry.registerUpgrade(OH_GRAVEL, new UpgradeThrowable.Gravel());
        registry.registerUpgrade(OH_SNOW, new UpgradeThrowable.Snow());
        registry.registerUpgrade(OH_FIRECHARGE, new UpgradeThrowable.Firecharge());
        registry.registerUpgrade(OH_EMERALD, new UpgradeThrowable.Emerald());
        registry.registerUpgrade(MH_SPECKLEDMELON, new UpgradeSpeckledMelon());
        registry.registerUpgrade(OH_QUARTZ, new UpgradeQuartz());
        registry.registerUpgrade(OH_BOWL, new UpgradeBowl());

        registry.registerUpgrade(BH_WHEAT, new UpgradeStandardBehavior("wheat", new ItemStack(Items.WHEAT)));
        registry.registerUpgrade(BH_NETHERWART, new UpgradeStandardBehavior("netherwart", new ItemStack(Items.NETHER_WART)));
        registry.registerUpgrade(BH_FERMSPIDEREYE, new UpgradeStandardBehavior("fermentedspidereye", new ItemStack(Items.FERMENTED_SPIDER_EYE)));
        registry.registerUpgrade(BH_ROTTENFLESH, new UpgradeStandardBehavior("rottenflesh", new ItemStack(Items.ROTTEN_FLESH)));
        registry.registerUpgrade(BH_SPONGE, new UpgradeStandardBehavior("sponge", new ItemStack(Blocks.SPONGE, 1, OreDictionary.WILDCARD_VALUE)));

        registry.registerUpgrade(EM_FLINT, new UpgradeFlint());
        registry.registerUpgrade(EM_IRONBLOCK, new UpgradeIronBlock());
        registry.registerUpgrade(EM_PRISMARINESHARD, new UpgradePrismarineShard());
        registry.registerUpgrade(EM_WOOL, new UpgradeWool());
        registry.registerUpgrade(EM_SUGARCANE, new UpgradeSugarCane());
        registry.registerUpgrade(EM_COAL, new UpgradeCoal());
        registry.registerUpgrade(EM_GOLDINGOT, new UpgradeGoldIngot());

        registry.registerUpgrade(MC_EGG, new UpgradeEgg());
        registry.registerUpgrade(MC_GLOWSTONE, new UpgradeGlowstone());
        registry.registerUpgrade(MC_FEATHER, new UpgradeFeather());
        registry.registerUpgrade(MC_FOOD, new UpgradeFood());
        registry.registerUpgrade(MC_GLASS, new UpgradeGoggles());
        registry.registerUpgrade(MC_LEATHER, new UpgradeLeather());
        registry.registerUpgrade(MC_RABBITHIDE, new UpgradeRabbitHide());
        registry.registerUpgrade(MC_SUGAR, new UpgradeSugar());
        registry.registerUpgrade(MC_MAGMACREAM, new UpgradeMagmaCream());
        registry.registerUpgrade(MC_GOLDNUGGET, new UpgradeGoldNugget());
        registry.registerUpgrade(MC_REDMUSHROOM, new UpgradeRedMushroom());
        registry.registerUpgrade(MC_GUNPOWDER, new UpgradeGunpowder());
        registry.registerUpgrade(MC_FIREWORKSTAR, new UpgradeFireworkStar());
        registry.registerUpgrade(MC_BROWNMUSHROOM, new UpgradeBrownMushroom());
        registry.registerUpgrade(MC_SKULL, new UpgradeSkull());
        registry.registerUpgrade(MC_PAPER, new UpgradePaper());
        registry.registerUpgrade(MC_CONCRETEPOWDER, new UpgradeConcretePowder());
        registry.registerUpgrade(MC_BUTTON, new UpgradeButton());
        registry.registerUpgrade(MC_CLAY, new UpgradeClay());
        registry.registerUpgrade(MC_GHASTTEAR, new UpgradeGhastTear());
        registry.registerUpgrade(MC_REDSTONE, new UpgradeRedstone());
        registry.registerUpgrade(MC_SLIMEBALL, new UpgradeSlimeball());
        registry.registerUpgrade(MC_DIAMOND, new UpgradeDiamond());
        registry.registerUpgrade(MC_DIAMONDBLOCK, new UpgradeDiamondBlock());
        registry.registerUpgrade(MC_ENDERPEARL, new UpgradeEnderPearl());
        registry.registerUpgrade(MC_WHEATSEEDS, new UpgradeWheatSeeds());
        registry.registerUpgrade(MC_BLAZEPOWDER, new UpgradeBlazePowder());
        registry.registerUpgrade(MC_LILYPAD, new UpgradeLilyPad());
        registry.registerUpgrade(MC_RABBITFOOT, new UpgradeRabbitFoot());
        registry.registerUpgrade(MC_PRISMCRYSTALS, new UpgradePrismarineCrystal());

        registry.registerUpgrade(CR_IRONINGOT, new UpgradeIronIngot());
        registry.registerUpgrade(CR_BRICK, new UpgradeBrick());
        registry.registerUpgrade(CR_STRING, new UpgradeString());
        registry.registerUpgrade(CR_CACTUS, new UpgradeCactus());
        registry.registerUpgrade(CR_NETHERBRICK, new UpgradeNetherBrick());
    }
}
