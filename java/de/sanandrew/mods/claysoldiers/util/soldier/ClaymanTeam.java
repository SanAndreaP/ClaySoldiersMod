/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.FMLLog;
import de.sanandrew.core.manpack.util.helpers.ItemUtils;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ClaymanTeam
{
    public static final ClaymanTeam NULL_TEAM = new ClaymanTeam();

    private static final Map<String, ClaymanTeam> TEAMS_ = Maps.newHashMap();
    private static final List<String> TEAM_NAMES_FOR_DOLLS_ = new ArrayList<>();

    private static boolean isInitialized = false;
    private String p_name;
    private ResourceLocation[] p_texturesDefault;
    private ResourceLocation[] p_texturesRare;
    private ResourceLocation[] p_texturesUnique;
    private String p_icon;
    private int p_iconColor;
    private int p_teamColor;
    private ItemStack p_teamItem;
    private IIcon p_iconInstance;

    /**
     * DO NOT USE THIS! THIS IS ONlY FOR THE DUMMY "NULL-Team"!
     */
    private ClaymanTeam() {
        this.p_name = "nullTeam";
        this.p_teamColor = 0xFFFFFF;
        this.p_iconColor = 0xFFFFFF;
        this.p_teamItem = new ItemStack(Blocks.command_block);
    }

    private ClaymanTeam(String teamName, int teamColor, ItemStack teamItem, String iconTexture, String[] defTextures, String[] rareTextures, String[] uniqueTextures)
            throws ClaymanTeamRegistrationException {
        if( teamName == null ) {
            throw new ClaymanTeamRegistrationException("teamName cannot be null!");
        } else if( teamName.isEmpty() ) {
            throw new ClaymanTeamRegistrationException("teamName cannot be empty!");
        }

        if( iconTexture == null ) {
            throw new ClaymanTeamRegistrationException("iconTexture cannot be null!");
        } else if( iconTexture.isEmpty() ) {
            throw new ClaymanTeamRegistrationException("iconTexture cannot be empty!");
        }

        if( teamItem == null ) {
            throw new ClaymanTeamRegistrationException("teamItem cannot be null!");
        }

        this.p_name = teamName;
        this.p_icon = iconTexture;
        this.p_teamColor = teamColor;
        this.p_iconColor = 0xFFFFFF;
        this.p_teamItem = teamItem;

        if( defTextures != null ) {
            if( defTextures.length == 0 ) {
                throw new ClaymanTeamRegistrationException("defTextures cannot be empty!");
            }

            this.p_texturesDefault = new ResourceLocation[defTextures.length];
            for( int i = 0; i < defTextures.length; i++ ) {
                this.p_texturesDefault[i] = new ResourceLocation(defTextures[i]);
            }
        } else {
            throw new ClaymanTeamRegistrationException("defTextures cannot be null!");
        }

        if( rareTextures != null && rareTextures.length > 0 ) {
            this.p_texturesRare = new ResourceLocation[rareTextures.length];
            for( int i = 0; i < rareTextures.length; i++ ) {
                this.p_texturesRare[i] = new ResourceLocation(rareTextures[i]);
            }
        } else {
            this.p_texturesRare = new ResourceLocation[0];
        }

        if( uniqueTextures != null && uniqueTextures.length > 0 ) {
            this.p_texturesUnique = new ResourceLocation[uniqueTextures.length];
            for( int i = 0; i < uniqueTextures.length; i++ ) {
                this.p_texturesUnique[i] = new ResourceLocation(uniqueTextures[i]);
            }
        } else {
            this.p_texturesUnique = new ResourceLocation[0];
        }
    }

    // NOTE: use http://www.colorpicker.com/ to pick a fitting color
    public static void initialize() {
        if( isInitialized ) {
            FMLLog.log(ClaySoldiersMod.MOD_LOG, Level.WARN, "Something tried to re-initialize the clayman teams! This is not allowed and should be checked!");
            return;
        } else {
            isInitialized = true;
        }

        TEAMS_.put(NULL_TEAM.p_name, NULL_TEAM);

        registerTeam("clay", 0x999999, new ItemStack(Items.dye, 1, 7),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/lightgray.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/lightgray.png" },
                     null
        ).useTeamColorAsItemColor();
        registerTeam("black", 0x191919, new ItemStack(Items.dye, 1, 0),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/black.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/black.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_unique/black.png" }
        ).useTeamColorAsItemColor();
        registerTeam("red", 0xCC4646, new ItemStack(Items.dye, 1, 1),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/red.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/red.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_unique/red.png" }
        ).useTeamColorAsItemColor();
        registerTeam("green", 0x667F33, new ItemStack(Items.dye, 1, 2),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/green.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/green.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_unique/green.png",
                                    ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_unique/green2.png"
                     }
        ).useTeamColorAsItemColor();
        registerTeam("brown", 0x664C33, new ItemStack(Items.dye, 1, 3),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/brown.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/brown.png",
                                    ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/brown2.png"
                     },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_unique/brown.png",
                                    ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_unique/brown2.png"
                     }
        ).useTeamColorAsItemColor();
        registerTeam("blue", 0x334CB2, new ItemStack(Items.dye, 1, 4),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/blue.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/blue.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_unique/blue.png" }
        ).useTeamColorAsItemColor();
        registerTeam("purple", 0x7F3FB2, new ItemStack(Items.dye, 1, 5),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/purple.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/purple.png",
                                    ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/purple2.png"
                     },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_unique/purple.png",
                                    ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_unique/purple2.png"
                     }
        ).useTeamColorAsItemColor();
        registerTeam("cyan", 0x4C7F99, new ItemStack(Items.dye, 1, 6),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/cyan.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/cyan.png" },
                     null
        ).useTeamColorAsItemColor();
        registerTeam("gray", 0x4C4C4C, new ItemStack(Items.dye, 1, 8),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/gray.png" },
                     null,
                     null
        ).useTeamColorAsItemColor();
        registerTeam("pink", 0xF27FA5, new ItemStack(Items.dye, 1, 9),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/pink.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/pink.png" },
                     null
        ).useTeamColorAsItemColor();
        registerTeam("lime", 0x7FCC19, new ItemStack(Items.dye, 1, 10),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/lime.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/lime.png" },
                     null
        ).useTeamColorAsItemColor();
        registerTeam("yellow", 0xE5E533, new ItemStack(Items.dye, 1, 11),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/yellow.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/yellow.png",
                                    ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/yellow2.png",
                                    ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/yellow3.png"
                     },
                     null
        ).useTeamColorAsItemColor();
        registerTeam("lightblue", 0x6699D8, new ItemStack(Items.dye, 1, 12),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/lightblue.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/lightblue.png" },
                     null
        ).useTeamColorAsItemColor();
        registerTeam("magenta", 0xE000FF, new ItemStack(Items.dye, 1, 13),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/magenta.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/magenta.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_unique/magenta.png" }
        ).useTeamColorAsItemColor();
        registerTeam("orange", 0xD87F33, new ItemStack(Items.dye, 1, 14),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/orange.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/orange.png" },
                     null
        ).useTeamColorAsItemColor();
        registerTeam("white", 0xFFFFFF, new ItemStack(Items.dye, 1, 15),
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/white.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/white.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_unique/white.png",
                                    ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_unique/white2.png"
                     }
        ).useTeamColorAsItemColor();
        registerTeam("melon", 0x91D400, new ItemStack(Blocks.melon_block), ClaySoldiersMod.MOD_ID + ":doll_melon",
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/melon.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/melon.png" },
                     null
        );
        registerTeam("pumpkin", 0xDE8509, new ItemStack(Blocks.pumpkin), ClaySoldiersMod.MOD_ID + ":doll_pumpkin",
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/pumpkin.png",
                                    ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/pumpkin2.png"
                     },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/pumpkin.png",
                                    ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/pumpkin2.png"
                     },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_unique/pumpkin.png",
                                    ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_unique/pumpkin2.png"
                     }
        );
        registerTeam("coal", 0x252525, new ItemStack(Blocks.torch), ClaySoldiersMod.MOD_ID + ":doll_coal",
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/coal.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/coal.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_unique/coal.png" }
        );
        registerTeam("redstone", 0xD90707, new ItemStack(Blocks.redstone_torch), ClaySoldiersMod.MOD_ID + ":doll_redstone",
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/redstone.png",
                                    ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/redstone2.png"
                     },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers_rare/redstone.png" },
                     new String[] { ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/redstone.png",
                                    ClaySoldiersMod.MOD_ID + ":textures/entity/soldiers/redstone2.png"
                     }
        );
    }

    public static ClaymanTeam registerTeam(String teamName, int teamColor, ItemStack teamItem, String[] defTextures, String[] rareTextures, String[] uniqueTextures) {
        return registerTeam(teamName, teamColor, teamItem, ClaySoldiersMod.MOD_ID + ":doll_clay", defTextures, rareTextures, uniqueTextures);
    }

    public static ClaymanTeam registerTeam(String teamName, int teamColor, ItemStack teamItem, String iconTexture, String[] defTextures, String[] rareTextures,
                                           String[] uniqueTextures) {
        try {
            ClaymanTeam inst = new ClaymanTeam(teamName, teamColor, teamItem, iconTexture, defTextures, rareTextures, uniqueTextures);
            if( TEAMS_.containsKey(teamName) ) {
                FMLLog.log(ClaySoldiersMod.MOD_LOG, Level.WARN, "A mod has overridden the soldier team \"%s\"!", teamName);
            } else {
                TEAM_NAMES_FOR_DOLLS_.add(teamName);
            }
            TEAMS_.put(teamName, inst);

            return inst;
        } catch( ClaymanTeamRegistrationException ex ) {
            FMLLog.log(ClaySoldiersMod.MOD_LOG, Level.ERROR, "There was an error while trying to register the soldier team %s:", teamName);
            ex.printStackTrace();
            FMLLog.log(ClaySoldiersMod.MOD_LOG, Level.ERROR, "This team will not be registered!");

            return null;
        }
    }

    public static ClaymanTeam getTeam(String name) {
        return TEAMS_.get(name);
    }

    public static ClaymanTeam getTeam(ItemStack stack) {
        if( stack == null ) {
            return NULL_TEAM;
        }

        for( ClaymanTeam team : TEAMS_.values() ) {
            if( ItemUtils.areStacksEqual(stack, team.getTeamItem(), true) ) {
                return team;
            }
        }

        return NULL_TEAM;
    }

    public static List<String> getTeamNamesForDolls() {
        return new ArrayList<>(TEAM_NAMES_FOR_DOLLS_);
    }

    public static void registerIcons(IIconRegister iconRegister) {
        Map<String, IIcon> registeredIcons = Maps.newHashMap();
        for( ClaymanTeam team : TEAMS_.values() ) {
            if( TEAM_NAMES_FOR_DOLLS_.contains(team.p_name) ) {
                if( registeredIcons.containsKey(team.p_icon) ) {
                    team.p_iconInstance = registeredIcons.get(team.p_icon);
                } else {
                    team.p_iconInstance = iconRegister.registerIcon(team.p_icon);
                    registeredIcons.put(team.p_icon, team.p_iconInstance);
                }
            }
        }
    }

    public ClaymanTeam useTeamColorAsItemColor() {
        this.p_iconColor = this.p_teamColor;

        return this;
    }

    public String getTeamName() {
        return this.p_name;
    }

    public ResourceLocation[] getDefaultTextures() {
        return this.p_texturesDefault;
    }

    public ResourceLocation[] getRareTextures() {
        return this.p_texturesRare;
    }

    public ResourceLocation[] getUniqueTextures() {
        return this.p_texturesUnique;
    }

    public String getIconTexture() {
        return this.p_icon;
    }

    public int getTeamColor() {
        return this.p_teamColor;
    }

    public int getIconColor() {
        return this.p_iconColor;
    }

    public ItemStack getTeamItem() {
        return this.p_teamItem.copy();
    }

    public IIcon getIconInstance() {
        return this.p_iconInstance;
    }

    private static class ClaymanTeamRegistrationException
            extends Exception
    {
        public ClaymanTeamRegistrationException(String message) {
            super(message);
        }
    }
}
