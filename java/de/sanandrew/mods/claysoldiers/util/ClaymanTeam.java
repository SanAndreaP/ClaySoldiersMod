package de.sanandrew.mods.claysoldiers.util;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author SanAndreasP
 * @version 1.0
 */
public class ClaymanTeam
{
    public static final String DEFAULT_TEAM = "clay";

    private static final Map<String, ClaymanTeam> TEAMS_ = Maps.newHashMap();
    private static final List<String> TEAM_NAMES_ = new ArrayList<>();

    // NOTE: use http://www.colorpicker.com/ to pick a fitting color
    static {
        registerTeam(DEFAULT_TEAM, 0x808080,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/gray.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/gray.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/gray.png" }
        );
        registerTeam("red", 0xF00000,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/red.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/red.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/red.png" }
        );
        registerTeam("orange", 0xFF8000,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/orange.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/orange.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/orange.png" }
        );
        registerTeam("yellow", 0xFFFF00,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/yellow.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/yellow.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/yellow.png" }
        );
        registerTeam("lime", 0x00FF00,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/lime.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/lime.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/lime.png" }
        );
        registerTeam("green", 0x008000,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/green.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/green.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/green.png" }
        );
        registerTeam("cyan", 0x00FFFF,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/cyan.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/cyan.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/cyan.png" }
        );
        registerTeam("blue", 0x0000FF,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/blue.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/blue.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/blue.png" }
        );
        registerTeam("purple", 0xB000FF,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/purple.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/purple.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/purple.png" }
        );
        registerTeam("brown", 0x6B4423,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/brown.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/brown.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/brown.png" }
        );
        registerTeam("black", 0x1B1B1B,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/black.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/black.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/black.png" }
        );
        registerTeam("white", 0xFFFFFF,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/white.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/white.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/white.png" }
        );
        registerTeam("melon", CSM_Main.MOD_ID + ":doll_melon", 0xFFFFFF,
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers/melon.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_rare/melon.png" },
                     new String[] { CSM_Main.MOD_ID + ":textures/entity/soldiers_unique/melon.png" }
        );
    }

    private String name_;
    private ResourceLocation[] texturesDefault_;
    private ResourceLocation[] texturesRare_;
    private ResourceLocation[] texturesUnique_;
    private String icon_;
    private int iconColor_;

    @SideOnly(Side.CLIENT)
    private IIcon iconInstance_;

    private ClaymanTeam(String teamName, String iconTexture, int iconColor, String[] defTextures, String[] rareTextures, String[] uniqueTextures) {
        this.name_ = teamName;
        this.icon_ = iconTexture;
        this.iconColor_ = iconColor;

        this.texturesDefault_ = new ResourceLocation[defTextures.length];
        for( int i = 0; i < defTextures.length; i++ ) {
            this.texturesDefault_[i] = new ResourceLocation(defTextures[i]);
        }

        if( rareTextures != null ) {
            this.texturesRare_ = new ResourceLocation[rareTextures.length];
            for( int i = 0; i < rareTextures.length; i++ ) {
                this.texturesRare_[i] = new ResourceLocation(rareTextures[i]);
            }
        }

        if( uniqueTextures != null ) {
            this.texturesUnique_ = new ResourceLocation[uniqueTextures.length];
            for( int i = 0; i < uniqueTextures.length; i++ ) {
                this.texturesUnique_[i] = new ResourceLocation(uniqueTextures[i]);
            }
        }
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

    public int getIconColor() {
        return this.iconColor_;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconInstance() {
        return this.iconInstance_;
    }

    public static void registerTeam(String teamName, String iconTexture, String[] defTextures, String[] rareTextures, String[] uniqueTextures) {
        registerTeam(teamName, iconTexture, 0xFFFFFF, defTextures, rareTextures, uniqueTextures);
    }

    public static void registerTeam(String teamName, int iconColor, String[] defTextures, String[] rareTextures, String[] uniqueTextures) {
        registerTeam(teamName, CSM_Main.MOD_ID + ":doll_clay", iconColor, defTextures, rareTextures, uniqueTextures);
    }

    public static void registerTeam(String teamName, String iconTexture, int iconColor, String[] defTextures, String[] rareTextures, String[] uniqueTextures) {
        if( TEAMS_.containsKey(teamName) ) {
            FMLLog.log(CSM_Main.MOD_LOG, Level.WARN, "A mod has overridden the soldier team \"%s\"!", teamName);
        } else {
            TEAM_NAMES_.add(teamName);
        }
        TEAMS_.put(teamName, new ClaymanTeam(teamName, iconTexture, iconColor, defTextures, rareTextures, uniqueTextures));
    }

    public static ClaymanTeam getTeamFromName(String name) {
        return TEAMS_.get(name);
    }

    public static List<String> getTeamNames() {
        return new ArrayList<>(TEAM_NAMES_);
    }

    @SideOnly(Side.CLIENT)
    public static void registerIcons(IIconRegister iconRegister) {
        Map<String, IIcon> registeredIcons = Maps.newHashMap();
        for( ClaymanTeam team : TEAMS_.values() ) {
            if( registeredIcons.containsKey(team.getIconTexture()) ) {
                team.iconInstance_ = registeredIcons.get(team.getIconTexture());
            } else {
                team.iconInstance_ = iconRegister.registerIcon(team.getIconTexture());
                registeredIcons.put(team.getIconTexture(), team.iconInstance_);
            }
        }
    }
}
