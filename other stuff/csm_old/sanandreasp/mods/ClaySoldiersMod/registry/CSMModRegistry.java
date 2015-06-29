/*******************************************************************************************************************
* Name: CSMModRegistry.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.registry;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import sanandreasp.core.manpack.managers.SAPConfigManager;
import sanandreasp.core.manpack.managers.SAPLanguageManager;
import sanandreasp.core.manpack.managers.SAPUpdateManager;
import sanandreasp.mods.ManagerPackHelper;
import sanandreasp.mods.ClaySoldiersMod.client.entity.EntityClayCam;
import sanandreasp.mods.ClaySoldiersMod.client.packet.PacketHandlerClient;
import sanandreasp.mods.ClaySoldiersMod.client.registry.Handler_ClientTicks;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;
import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayNexus;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityBunny;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityGecko;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityHorse;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityPegasus;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntitySnowball;
import sanandreasp.mods.ClaySoldiersMod.entity.mount.EntityTurtle;
import sanandreasp.mods.ClaySoldiersMod.entity.projectile.EntityFireball;
import sanandreasp.mods.ClaySoldiersMod.entity.projectile.EntityGravelChunk;
import sanandreasp.mods.ClaySoldiersMod.item.ItemArenaPlacer;
import sanandreasp.mods.ClaySoldiersMod.item.ItemBunny;
import sanandreasp.mods.ClaySoldiersMod.item.ItemClayDisruptor;
import sanandreasp.mods.ClaySoldiersMod.item.ItemClayMan;
import sanandreasp.mods.ClaySoldiersMod.item.ItemDebugShield;
import sanandreasp.mods.ClaySoldiersMod.item.ItemGecko;
import sanandreasp.mods.ClaySoldiersMod.item.ItemGlobal;
import sanandreasp.mods.ClaySoldiersMod.item.ItemHorses;
import sanandreasp.mods.ClaySoldiersMod.item.ItemNexus;
import sanandreasp.mods.ClaySoldiersMod.item.ItemTurtle;
import sanandreasp.mods.ClaySoldiersMod.packet.PacketHandlerCommon;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.UpgradeRegistry;

import com.google.common.collect.Maps;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.relauncher.SideOnly;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.FMLPacket;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.common.registry.EntityRegistry.EntityRegistration;
import net.minecraftforge.classloading.FMLForgePlugin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

@Mod(modid = CSMModRegistry.modID, name = "Clay Soldiers Mod", version = "2.0.0", dependencies="after:sanandreasp.core.manpack.ManPackLoadingPlugin")
@NetworkMod(clientSideRequired = true, serverSideRequired = false,
			clientPacketHandlerSpec=@SidedPacketHandler(channels=CSMModRegistry.channelID, packetHandler=PacketHandlerClient.class),
			serverPacketHandlerSpec=@SidedPacketHandler(channels=CSMModRegistry.channelID, packetHandler=PacketHandlerCommon.class))
public class CSMModRegistry
{
	public static final String modID = "ClaySoldiersMod";
	public static final String channelID = "ClaySoldiers";
	public static final String proxyCmn = "sanandreasp.mods.ClaySoldiersMod.registry.CommonProxy";
	public static final String proxyClt = "sanandreasp.mods.ClaySoldiersMod.client.registry.ClientProxy";

	@Instance("ClaySoldiersMod") public static CSMModRegistry instance;

	@SidedProxy(clientSide=CSMModRegistry.proxyClt, serverSide=CSMModRegistry.proxyCmn)
	public static CommonProxy proxy;

	public static Item clayDisruptor;
	public static Item greyDoll;
	public static Item horseDoll;
	public static Item pegasusDoll;
	public static Item bunnyDoll;
	public static Item geckoDoll;
	public static Item brickDoll;
	public static Item clayCookie;
	public static Item shield;
	public static Item turtleDoll;
	public static Item nexus;
	public static Item shearBlade;
	public static Item brickLump;
	public static Item arenaPlacer;

	public static UpgradeRegistry clayUpgRegistry;

	public static CreativeTabs claySoldierTab;

    public static int[] itemIDs = new int[] {
    	6849, // disruptor
    	6850, // soldier doll
    	6851, // horse doll
    	6852, // pegasus doll
    	6853, // brick doll
    	6854, // clay cookie
    	6855, // turtle doll
    	6856, // bunny doll
    	6857, // gecko doll
    	6860, // shield
    	6862,  // ClayNexus
    	6863,  // shearBlade
    	6861,  // brickLump
    	6858  // arenaPlacer
    };

	public static ManagerPackHelper manHelper = new ManagerPackHelper();

    private void setIDs() {
        String[] itemNames = new String[] {
        	"disruptor", "dollSoldier", "horseDoll", "pegasusDoll", "brickDoll", "clayCookie",
        	"turtleDoll", "bunnyDoll", "geckoDoll", "shield", "clayNexus", "shearBlade", "brickLump", "arenaPlacer"
        };

    	manHelper.getCfgMan().addStaticItemIDs(itemNames, itemIDs);

    	manHelper.getCfgMan().loadConfig();

    	itemIDs = manHelper.getCfgMan().getItemIDs(itemNames);
    }

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		manHelper.checkManPack(event.getModMetadata().name);

		if (!manHelper.loading) return;

		manHelper.initMan(
				new SAPConfigManager("ClaySoldiersMod", "ClaySoldiers.txt", "/sanandreasp/"),
				new SAPLanguageManager("/sanandreasp/ClaySoldiers/", "1.0", "Clay Soldiers Mod"),
				new SAPUpdateManager("Clay Soldiers Mod", 2, 0, 0, "http://dl.dropbox.com/u/56920617/ClaySoldiersMod2_latest.txt", "http://www.minecraftforum.net/topic/964897-")
		);

		claySoldierTab = new CreativeTabs("ClaySoldiers") {
			@Override
			@SideOnly(Side.CLIENT)
			public int getTabIconItemIndex() {
				return CSMModRegistry.greyDoll.itemID;
			}
		};

		setIDs();

		SoldierTeams.initDefTeams();

		// Items
			clayDisruptor = (new ItemClayDisruptor(itemIDs[0]-256)).setUnlocalizedName("claydisruptor").setCreativeTab(this.claySoldierTab);
			greyDoll = (new ItemClayMan(itemIDs[1]-256, 0)).setUnlocalizedName("claydoll").setCreativeTab(this.claySoldierTab);
			horseDoll = (new ItemHorses(itemIDs[2]-256, 0)).setUnlocalizedName("horsedoll").setCreativeTab(this.claySoldierTab);
			pegasusDoll = (new ItemHorses(itemIDs[3]-256, 1)).setUnlocalizedName("pegasusdoll").setCreativeTab(this.claySoldierTab);
			bunnyDoll = (new ItemBunny(itemIDs[4]-256)).setUnlocalizedName("bunnydoll").setCreativeTab(this.claySoldierTab);
			geckoDoll = (new ItemGecko(itemIDs[5]-256)).setUnlocalizedName("geckodoll").setCreativeTab(this.claySoldierTab);
			brickDoll = (new ItemGlobal(itemIDs[6]-256)).setIconFile("ClaySoldiersMod:dollBrick").setUnlocalizedName("brickDoll").setCreativeTab(this.claySoldierTab);
			clayCookie = (new ItemGlobal(itemIDs[7]-256)).setIconFile("ClaySoldiersMod:noms").setUnlocalizedName("clayCookie").setCreativeTab(this.claySoldierTab);
			turtleDoll = (new ItemTurtle(itemIDs[8]-256)).setUnlocalizedName("clayturtle").setCreativeTab(this.claySoldierTab);
			shield = (new ItemDebugShield(itemIDs[9]-256)).setUnlocalizedName("clayshield");
			nexus = (new ItemNexus(itemIDs[10]-256)).setUnlocalizedName("claynexus").setCreativeTab(this.claySoldierTab);
			shearBlade = (new ItemGlobal(itemIDs[11]-256)).setIconFile("ClaySoldiersMod:shearBlade").setUnlocalizedName("clayShearBlade").setCreativeTab(this.claySoldierTab);
			brickLump = (new ItemGlobal(itemIDs[12]-256)).setIconFile("ClaySoldiersMod:brickLump").setUnlocalizedName("brickLump").setCreativeTab(this.claySoldierTab);
			arenaPlacer = (new ItemArenaPlacer(itemIDs[13]-256)).setUnlocalizedName("arenaPlacer").setCreativeTab(this.claySoldierTab);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		if (!manHelper.loading) return;

		proxy.registerRenderInformation();
		NetworkRegistry.instance().registerGuiHandler(instance, new GuiHandler());
		TickRegistry.registerTickHandler(new Handler_ServerTicks(), Side.SERVER);
		if (FMLCommonHandler.instance().getSide().isClient())
			TickRegistry.registerTickHandler(new Handler_ClientTicks(), Side.CLIENT);
//		claySoldierTab.setBackgroundImageName(this.CSMTAB_TEXTURE);

		// Dispenser handler
		BlockDispenser.dispenseBehaviorRegistry.putObject(greyDoll, new Handler_BehaviorDispenseItem());
		BlockDispenser.dispenseBehaviorRegistry.putObject(horseDoll, new Handler_BehaviorDispenseItem());
		BlockDispenser.dispenseBehaviorRegistry.putObject(pegasusDoll, new Handler_BehaviorDispenseItem());
		BlockDispenser.dispenseBehaviorRegistry.putObject(bunnyDoll, new Handler_BehaviorDispenseItem());
		BlockDispenser.dispenseBehaviorRegistry.putObject(turtleDoll, new Handler_BehaviorDispenseItem());
		BlockDispenser.dispenseBehaviorRegistry.putObject(geckoDoll, new Handler_BehaviorDispenseItem());

		EntityRegistry.registerModEntity(EntityClayMan.class, "CSM_ClaySoldier", 0, this, 64, 1, true);
		EntityRegistry.registerModEntity(EntityGravelChunk.class, "CSM_GravelChunk", 1, this, 64, 1, true);
		EntityRegistry.registerModEntity(EntitySnowball.class, "CSM_SnowChunk", 2, this, 64, 1, true);
		EntityRegistry.registerModEntity(EntityFireball.class, "CSM_FireChunk", 3, this, 64, 1, true);
		EntityRegistry.registerModEntity(EntityHorse.class, "CSM_ClayHorse", 4, this, 64, 1, true);
		EntityRegistry.registerModEntity(EntityPegasus.class, "CSM_ClayPegasus", 5, this, 64, 1, true);
		EntityRegistry.registerModEntity(EntityBunny.class, "CSM_Bunny", 6, this, 64, 1, true);
		EntityRegistry.registerModEntity(EntityTurtle.class, "CSM_ClayTurtle", 7, this, 64, 1, true);
		EntityRegistry.registerModEntity(EntityGecko.class, "CSM_Gecko", 8, this, 64, 1, true);
		EntityRegistry.registerModEntity(EntityClayNexus.class, "CSM_ClayNexus", 9, this, 64, 1, true);

		this.clayUpgRegistry = new UpgradeRegistry();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent evt) {
		if (!manHelper.loading) return;

	// Language stuff

		manHelper.getLangMan().addLangProp(clayDisruptor,"Clay Disruptor");
		manHelper.getLangMan().addLangProp(brickDoll, "Lifeless Brick Doll");
		manHelper.getLangMan().addLangProp(clayCookie, "Clay Cookie");
		manHelper.getLangMan().addLangProp(nexus, "Clay Nexus");
		manHelper.getLangMan().addLangProp(shearBlade, "Shear Blade");
		manHelper.getLangMan().addLangProp(brickLump, "Brick Lump");
		manHelper.getLangMan().addLangProp("itemGroup.ClaySoldiers", "Clay Soldier Items");

//		String soldierNames[] = new String[] {
//				"Clay Soldier", "Red Soldier", "Yellow Soldier", "Green Soldier", "Blue Soldier",
//				"Orange Soldier", "Purple Soldier", "Pink Soldier", "Brown Soldier", "White Soldier",
//				"Black Soldier", "Cyan Soldier", "Light Grey Soldier", "Lime Soldier", "Light Blue Soldier",
//				"Magenta Soldier", "Melon Soldier", "Pumpkin Soldier", "Coal Soldier", "Redstone Soldier"
//			};
		String horseNames[] = new String[] {
				"Dirt Horse", "Sand Horse", "Gravel Horse", "Snow Horse", "Grass Horse", "Lapis Horse",
				"Clay Horse", "Carrot Horse"
			};
		String pegasiNames[] = new String[] {
				"Dirt Pegasus", "Sand Pegasus", "Gravel Pegasus", "Snow Pegasus", "Grass Pegasus",
				"Lapis Pegasus", "Clay Pegasus", "Carrot Pegasus"
			};
		String bunnyNames[] = new String[] {
				"White Bunny", "Orange Bunny", "Magenta Bunny", "Light Blue Bunny",
				"Yellow Bunny", "Lime Bunny", "Pink Bunny", "Gray Bunny", "Light Gray Bunny", "Cyan Bunny",
				"Purple Bunny", "Blue Bunny", "Brown Bunny", "Green Bunny", "Red Bunny", "Black Bunny"
			};
		String turtleNames[] = new String[] {
				"Stone Turtle", "Mossy Turtle", "Netherrack Turtle",
				"Melon Turtle", "Sandstone Turtle", "Endstone Turtle", "Pumpkin Turtle"
			};

        for( int sldInd : SoldierTeams.getTeamsList().keySet() )
        	if( sldInd < 4096 )
        		manHelper.getLangMan().addLangProp(greyDoll.getUnlocalizedName(new ItemStack(greyDoll,1,sldInd)) + ".name", SoldierTeams.getTeamsList().get(sldInd).localName);
		int i;
        for (i = 0; i < horseNames.length; i++)
        	manHelper.getLangMan().addLangProp(horseDoll.getUnlocalizedName(new ItemStack(horseDoll,1,i)) + ".name", horseNames[i]);
        for (i = 0; i < pegasiNames.length; i++)
        	manHelper.getLangMan().addLangProp(pegasusDoll.getUnlocalizedName(new ItemStack(pegasusDoll,1,i)) + ".name", pegasiNames[i]);
        for (i = 0; i < bunnyNames.length; i++)
        	manHelper.getLangMan().addLangProp(bunnyDoll.getUnlocalizedName(new ItemStack(bunnyDoll,1,i)) + ".name", bunnyNames[i]);
        for (i = 0; i < turtleNames.length; i++)
        	manHelper.getLangMan().addLangProp(turtleDoll.getUnlocalizedName(new ItemStack(turtleDoll,1,i)) + ".name", turtleNames[i]);

        String geckoPrefix[] = new String[] { "Oakwood", "Birchwood", "Pinewood", "Junglewood" };

        for (i = 0; i < 16; i++)
        	manHelper.getLangMan().addLangProp(geckoDoll.getUnlocalizedName(new ItemStack(geckoDoll,1,i)) + ".name", getGeckoNameFromIndex(i, geckoPrefix, 0));

        manHelper.getLangMan().loadLangs();

    // Crafting and Smelting

        GameRegistry.addRecipe(new ItemStack(greyDoll, 4, 0),
    			"$",
    			"#",
    			'$', Block.slowSand,
    			'#', Block.blockClay
		);

		GameRegistry.addRecipe(new ItemStack(brickDoll, 4, 0),
			"$",
			"#",
			'$', Block.slowSand,
			'#', Block.brick
		);

		ItemStack horsePegasiMaterial[] = new ItemStack[] {
				new ItemStack(Block.dirt),
				new ItemStack(Block.sand),
				new ItemStack(Block.gravel),
				new ItemStack(Block.blockSnow),
				new ItemStack(Block.tallGrass, 1, OreDictionary.WILDCARD_VALUE),
				new ItemStack(Block.blockLapis),
				new ItemStack(Block.blockClay),
				new ItemStack(Item.carrot)
		};

		for (i = 0; i < horsePegasiMaterial.length; i++) {
			GameRegistry.addRecipe(new ItemStack(horseDoll, 2, i),
				"#$#",
				"# #",
				'$', Block.slowSand,
				'#', horsePegasiMaterial[i]
			);

			GameRegistry.addRecipe(new ItemStack(pegasusDoll, 2, i),
				" @ ",
				"#$#",
				"# #",
				'$', Block.slowSand,
				'#', horsePegasiMaterial[i],
				'@', Item.feather
			);
		}

		GameRegistry.addRecipe(new ItemStack(clayDisruptor, 1, 0),
			"#$#",
			"#@#",
			'$', Item.stick,
			'#', Item.clay,
			'@', Item.redstone
		);

		ItemStack turtleMaterial[] = new ItemStack[] {
				new ItemStack(Block.cobblestone, 1),
				new ItemStack(Block.cobblestoneMossy, 1),
				new ItemStack(Block.netherrack, 1),
				new ItemStack(Block.melon, 1),
				new ItemStack(Block.sandStone, 1),
				new ItemStack(Block.whiteStone, 1),
				new ItemStack(Block.pumpkin, 1)
		};

		for (i = 0; i < turtleMaterial.length; i++) {
			GameRegistry.addRecipe(new ItemStack(turtleDoll, 2, i),
				" ##",
				"$$#",
				'$', Block.slowSand,
				'#', turtleMaterial[i]
			);
		}

		GameRegistry.addSmelting(greyDoll.itemID, new ItemStack(brickDoll, 1), 0F);

		GameRegistry.addShapelessRecipe(new ItemStack(shearBlade, 2),
            new ItemStack(Item.shears, 1)
        );

		GameRegistry.addShapelessRecipe(new ItemStack(Item.shears, 1),
            new ItemStack(shearBlade, 1),
            new ItemStack(shearBlade, 1)
        );

		GameRegistry.addShapelessRecipe(new ItemStack(clayCookie, 16),
            new ItemStack(Item.clay, 1), new ItemStack(Item.sugar, 1)
        );

		int soldierDyeIndex[] = new int[] {
				1, 11, 2, 4, 14, 5, 9, 3, 15, 0, 6, 7, 10, 12, 13
		};

		for (i = 0; i < soldierDyeIndex.length; i++) {
			GameRegistry.addShapelessRecipe(new ItemStack(greyDoll, 1, i+1), new Object[] {
	            new ItemStack(greyDoll, 1), new ItemStack(Item.dyePowder, 1, soldierDyeIndex[i])
	        });
		}

		GameRegistry.addShapelessRecipe(new ItemStack(greyDoll, 1, 0),
            new ItemStack(brickDoll, 1), new ItemStack(Item.ghastTear, 1)
        );

		GameRegistry.addShapelessRecipe(new ItemStack(greyDoll, 4, 16),
            new ItemStack(greyDoll, 1), new ItemStack(greyDoll, 1), new ItemStack(greyDoll, 1), new ItemStack(greyDoll, 1), new ItemStack(Block.melon, 1, 0)
        );

		GameRegistry.addShapelessRecipe(new ItemStack(greyDoll, 4, 17),
            new ItemStack(greyDoll, 1), new ItemStack(greyDoll, 1), new ItemStack(greyDoll, 1), new ItemStack(greyDoll, 1), new ItemStack(Block.pumpkin, 1, 0)
        );

		GameRegistry.addShapelessRecipe(new ItemStack(greyDoll, 1, 16),
            new ItemStack(greyDoll, 1), new ItemStack(Item.melonSeeds, 1)
        );

		GameRegistry.addShapelessRecipe(new ItemStack(greyDoll, 1, 17),
            new ItemStack(greyDoll, 1), new ItemStack(Item.pumpkinSeeds, 1)
        );

		GameRegistry.addShapelessRecipe(new ItemStack(greyDoll, 1, 18),
            new ItemStack(greyDoll, 1), new ItemStack(Item.coal, 1)
        );

		GameRegistry.addShapelessRecipe(new ItemStack(greyDoll, 1, 18),
            new ItemStack(greyDoll, 1), new ItemStack(Item.coal, 1, 1)
        );

		GameRegistry.addShapelessRecipe(new ItemStack(greyDoll, 1, 19),
            new ItemStack(greyDoll, 1), new ItemStack(Item.redstone, 1)
        );

		for (i = 0; i < 20; i++) {
			GameRegistry.addRecipe(new ItemStack(greyDoll, 8, 0),
				"###", "#X#", "###",
				'#', new ItemStack(greyDoll, 1, i),
				'X', new ItemStack(Item.bucketWater, 1)
			);
			GameRegistry.addShapelessRecipe(new ItemStack(greyDoll, 1, 0),
				new ItemStack(greyDoll, 1, i),
				new ItemStack(Item.bucketWater, 1)
			);
		}

		for (i = 0; i < 16; i++) {
			GameRegistry.addRecipe(new ItemStack(bunnyDoll, 4, i),
				"#X#",
				'#', new ItemStack(Block.cloth, 1, i),
				'X', new ItemStack(Block.slowSand, 1)
			);
		}

		ItemStack geckoMaterial[] = new ItemStack[] {
				new ItemStack(Block.sapling, 1, 0),
				new ItemStack(Block.sapling, 1, 2),
				new ItemStack(Block.sapling, 1, 1),
				new ItemStack(Block.sapling, 1, 3)
		};

		for (i = 0; i < geckoMaterial.length; i++) {
			for (int j = 0; j < geckoMaterial.length; j++) {
				int dmg = i*4 + j;
				GameRegistry.addRecipe(new ItemStack(geckoDoll, 2, dmg),
					"1", "2", "3",
					'1', geckoMaterial[i],
					'2', new ItemStack(Block.slowSand, 1),
					'3', geckoMaterial[j]
				);
			}
		}

		GameRegistry.addRecipe(new ItemStack(nexus, 1),
			"CDC", "SOS", "OOO",
			'C', new ItemStack(Item.clay, 1),
			'D', new ItemStack(Item.diamond, 1),
			'S', new ItemStack(Block.slowSand, 1),
			'O', new ItemStack(Block.obsidian, 1)
		);

		GameRegistry.addShapelessRecipe(new ItemStack(Item.brick, 1), new ItemStack(this.brickLump, 1));
	}

	private String getGeckoNameFromIndex(int ind, String[] prefStrings, int lang) {
		int x = 0, y = 0;

		if (ind < 4) {
			x = ind;
			y = 0;
		} else if (ind < 8) {
			x = ind-4;
			y = 1;
		} else if (ind < 12) {
			x = ind-8;
			y = 2;
		} else {
			x = ind-12;
			y = 3;
		}

		return prefStrings[x] + "-" + prefStrings[y] + "-" + "Gecko";
	}

	public static int getWaveTime(EntityPlayer player) {
		if (waveTimes.containsKey(player.username)) {
			return waveTimes.get(player.username);
		} else {
			waveTimes.put(player.username, Integer.valueOf(0));
			return 0;
		}
	}

	public static void setWaveTime(EntityPlayer player, int waveSec) {
		waveTimes.put(player.username, Integer.valueOf(waveSec));
	}

	public static EntityPlayer prevPlayer;
	public static EntityClayCam claycam;
	public static Map<String, Integer> waveTimes = Maps.newHashMap();
	public static boolean showTheHUD, showTheGUY;
}
