/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry.upgrade;

import de.sanandrew.mods.claysoldiers.api.IUpgradeRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.core.UpgradeBrick;
import de.sanandrew.mods.claysoldiers.registry.upgrade.core.UpgradeIronIngot;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeFlint;
import de.sanandrew.mods.claysoldiers.registry.upgrade.enhancement.UpgradeIronBlock;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeArrow;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeBlazeRod;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeBone;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeBowl;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeQuartz;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeShearBlade;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeSpeckledMelon;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeStick;
import de.sanandrew.mods.claysoldiers.registry.upgrade.hand.UpgradeThrowable;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeBrownMushroom;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeEgg;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeFeather;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeFireworkStar;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeFood;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGlowstone;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGoggles;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGoldNugget;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeGunpowder;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeLeather;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeMagmaCream;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeRabbitHide;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeRedMushroom;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeSkull;
import de.sanandrew.mods.claysoldiers.registry.upgrade.misc.UpgradeSugar;

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
    public static final UUID OH_QUARTZ = UUID.fromString("8751AE74-134C-44E1-9405-55ED05E3416E");
    public static final UUID OH_BOWL = UUID.fromString("5FD5F54F-23C3-4D65-8D46-31F599F1E6CE");

    public static final UUID EC_FLINT = UUID.fromString("63342EEB-932B-4330-9B60-C5E21434A0B8");
    public static final UUID EC_IRONBLOCK = UUID.fromString("05113D07-A86D-45EA-AC7F-52E34567197A");

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

    public static final UUID CR_IRONINGOT = UUID.fromString("6426F05F-36C5-4F83-9D69-200CCBBA141D");
    public static final UUID CR_BRICK = UUID.fromString("00B12AB5-1E8D-43CA-A136-4BBB5E5970E0");

    public static void initialize(IUpgradeRegistry registry) {
        registry.registerUpgrade(MH_STICK, new UpgradeStick());
        registry.registerUpgrade(MH_ARROW, new UpgradeArrow());
        registry.registerUpgrade(MH_BONE, new UpgradeBone());
        registry.registerUpgrade(MH_BLAZEROD, new UpgradeBlazeRod());
        registry.registerUpgrade(MOH_SHEARBLADE, new UpgradeShearBlade());
        registry.registerUpgrade(OH_GRAVEL, new UpgradeThrowable.Gravel());
        registry.registerUpgrade(OH_SNOW, new UpgradeThrowable.Snow());
        registry.registerUpgrade(OH_FIRECHARGE, new UpgradeThrowable.Firecharge());
        registry.registerUpgrade(MH_SPECKLEDMELON, new UpgradeSpeckledMelon());
        registry.registerUpgrade(OH_QUARTZ, new UpgradeQuartz());
        registry.registerUpgrade(OH_BOWL, new UpgradeBowl());

        registry.registerUpgrade(EC_FLINT, new UpgradeFlint());
        registry.registerUpgrade(EC_IRONBLOCK, new UpgradeIronBlock());

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

        registry.registerUpgrade(CR_IRONINGOT, new UpgradeIronIngot());
        registry.registerUpgrade(CR_BRICK, new UpgradeBrick());
    }
}
