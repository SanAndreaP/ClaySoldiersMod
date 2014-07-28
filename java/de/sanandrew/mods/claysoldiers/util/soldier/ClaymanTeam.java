/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ClaymanTeam
{
    public static final ClaymanTeam NULL_TEAM = new ClaymanTeam("nullTeam");

    private static final Map<String, ClaymanTeam> TEAMS_ = Maps.newHashMap();
    private static final List<String> TEAM_NAMES_FOR_DOLLS_ = new ArrayList<>();

    // NOTE: use http://www.colorpicker.com/ to pick a fitting color
    static {
        TEAMS_.put(NULL_TEAM.getTeamName(), NULL_TEAM);

        registerTeam("clay", 0xA6A6A6,
                     new String[]{ CSM_Main.MOD_ID + ":textures/entity/soldiers/lightgray.png" },
                     new String[]{ CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/lightgray.png" },
                     null
        ).useTeamColorAsItemColor();
        registerTeam("white", 0xFFFFFF,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/white.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/white.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/white.png",
                                    CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/white2.png"}
        ).useTeamColorAsItemColor();
        registerTeam("gray", 0x5F5F5F,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/gray.png" },
                     null,
                     null
        ).useTeamColorAsItemColor();
        registerTeam("black", 0x1A1A1A,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/black.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/black.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/black.png" }
        ).useTeamColorAsItemColor();
        registerTeam("brown", 0x703710,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/brown.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/brown.png",
                                    CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/brown2.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/brown.png",
                                    CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/brown2.png" }
        ).useTeamColorAsItemColor();
        registerTeam("red", 0xE02121,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/red.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/red.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/red.png" }
        ).useTeamColorAsItemColor();
        registerTeam("orange", 0xEC6C14,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/orange.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/orange.png" },
                     null
        ).useTeamColorAsItemColor();
        registerTeam("yellow", 0xFFFF00,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/yellow.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/yellow.png",
                                    CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/yellow2.png",
                                    CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/yellow3.png" },
                     null
        ).useTeamColorAsItemColor();
        registerTeam("lime", 0x94ff00,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/lime.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/lime.png" },
                     null
        ).useTeamColorAsItemColor();
        registerTeam("green", 0x24902D,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/green.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/green.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/green.png",
                                    CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/green2.png" }
        ).useTeamColorAsItemColor();
        registerTeam("cyan", 0x14ECEC,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/cyan.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/cyan.png" },
                     null
        ).useTeamColorAsItemColor();
        registerTeam("lightblue", 0x75AED7,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/lightblue.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/lightblue.png" },
                     null
        ).useTeamColorAsItemColor();
        registerTeam("blue", 0x4343d7,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/blue.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/blue.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/blue.png" }
        ).useTeamColorAsItemColor();
        registerTeam("purple", 0x8E00FF,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/purple.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/purple.png",
                                    CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/purple2.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/purple.png",
                                    CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/purple2.png" }
        ).useTeamColorAsItemColor();
        registerTeam("magenta", 0xEC14DD,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/magenta.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/magenta.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/magenta.png" }
        ).useTeamColorAsItemColor();
        registerTeam("pink", 0xE69BD2,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/pink.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/pink.png" },
                     null
        ).useTeamColorAsItemColor();
        registerTeam("melon", 0x91D400, CSM_Main.MOD_ID + ":doll_melon",
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/melon.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/melon.png" },
                     null
        );
    }

    private String name_;
    private ResourceLocation[] texturesDefault_;
    private ResourceLocation[] texturesRare_;
    private ResourceLocation[] texturesUnique_;
    private String icon_;
    private int iconColor_;
    private int teamColor_;

    @SideOnly(Side.CLIENT)
    private IIcon iconInstance_;

    /** DO NOT USE THIS! THIS IS ONlY FOR THE DUMMY "NULL-Team"! */
    private ClaymanTeam(String teamName) {
        this.name_ = teamName;
        this.teamColor_ = 0xFFFFFF;
        this.iconColor_ = 0xFFFFFF;
    }

    private ClaymanTeam(String teamName, int teamColor, String iconTexture, String[] defTextures, String[] rareTextures, String[] uniqueTextures)
            throws ClaymanTeamRegistrationException
    {
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

        this.name_ = teamName;
        this.icon_ = iconTexture;
        this.teamColor_ = teamColor;
        this.iconColor_ = 0xFFFFFF;

        if( defTextures != null ) {
            if( defTextures.length == 0 ) {
                throw new ClaymanTeamRegistrationException("defTextures cannot be empty!");
            }

            this.texturesDefault_ = new ResourceLocation[defTextures.length];
            for( int i = 0; i < defTextures.length; i++ ) {
                this.texturesDefault_[i] = new ResourceLocation(defTextures[i]);
            }
        } else {
            throw new ClaymanTeamRegistrationException("defTextures cannot be null!");
        }

        if( rareTextures != null && rareTextures.length > 0 ) {
            this.texturesRare_ = new ResourceLocation[rareTextures.length];
            for( int i = 0; i < rareTextures.length; i++ ) {
                this.texturesRare_[i] = new ResourceLocation(rareTextures[i]);
            }
        } else {
            this.texturesRare_ = new ResourceLocation[0];
        }

        if( uniqueTextures != null && uniqueTextures.length > 0 ) {
            this.texturesUnique_ = new ResourceLocation[uniqueTextures.length];
            for( int i = 0; i < uniqueTextures.length; i++ ) {
                this.texturesUnique_[i] = new ResourceLocation(uniqueTextures[i]);
            }
        } else {
            this.texturesUnique_ = new ResourceLocation[0];
        }
    }

    public ClaymanTeam useTeamColorAsItemColor() {
        this.iconColor_ = this.teamColor_;
        return this;
    }

    public String getTeamName() {
        return this.name_;
    }

    public ResourceLocation[] getDefaultTextures() {
        return this.texturesDefault_;
    }

    public ResourceLocation[] getRareTextures() {
        return this.texturesRare_;
    }

    public ResourceLocation[] getUniqueTextures() {
        return this.texturesUnique_;
    }

    public String getIconTexture() {
        return this.icon_;
    }

    public int getTeamColor() {
        return this.teamColor_;
    }

    public int getIconColor() {
        return this.iconColor_;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconInstance() {
        return this.iconInstance_;
    }

    public static ClaymanTeam registerTeam(String teamName, int teamColor, String[] defTextures, String[] rareTextures, String[] uniqueTextures) {
        return registerTeam(teamName, teamColor, CSM_Main.MOD_ID + ":doll_clay", defTextures, rareTextures, uniqueTextures);
    }

    public static ClaymanTeam registerTeam(String teamName, int teamColor, String iconTexture, String[] defTextures, String[] rareTextures, String[] uniqueTextures) {
        try {
            ClaymanTeam inst = new ClaymanTeam(teamName, teamColor, iconTexture, defTextures, rareTextures, uniqueTextures);
            if( TEAMS_.containsKey(teamName) ) {
                FMLLog.log(CSM_Main.MOD_LOG, Level.WARN, "A mod has overridden the soldier team \"%s\"!", teamName);
            } else {
                TEAM_NAMES_FOR_DOLLS_.add(teamName);
            }
            TEAMS_.put(teamName, inst);
            return inst;
        } catch( ClaymanTeamRegistrationException ex ) {
            FMLLog.log(CSM_Main.MOD_LOG, Level.ERROR, "There was an error while trying to register the soldier team %s:", teamName);
            ex.printStackTrace();
            FMLLog.log(CSM_Main.MOD_LOG, Level.ERROR, "This team will not be registered!");
            return null;
        }
    }

    public static ClaymanTeam getTeamFromName(String name) {
        return TEAMS_.get(name);
    }

    public static List<String> getTeamNamesForDolls() {
        return new ArrayList<>(TEAM_NAMES_FOR_DOLLS_);
    }

    @SideOnly(Side.CLIENT)
    public static void registerIcons(IIconRegister iconRegister) {
        Map<String, IIcon> registeredIcons = Maps.newHashMap();
        for( ClaymanTeam team : TEAMS_.values() ) {
            if( TEAM_NAMES_FOR_DOLLS_.contains(team.getTeamName()) ) {
                if( registeredIcons.containsKey(team.getIconTexture()) ) {
                    team.iconInstance_ = registeredIcons.get(team.getIconTexture());
                } else {
                    team.iconInstance_ = iconRegister.registerIcon(team.getIconTexture());
                    registeredIcons.put(team.getIconTexture(), team.iconInstance_);
                }
            }
        }
    }

    private class ClaymanTeamRegistrationException extends Exception {
        public ClaymanTeamRegistrationException(String message) {
            super(message);
        }
    }
}
