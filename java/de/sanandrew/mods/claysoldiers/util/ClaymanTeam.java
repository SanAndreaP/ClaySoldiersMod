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

    static {
        registerTeam(DEFAULT_TEAM, 0x808080, new String[] {"claysoldiers:textures/entity/soldiers/gray.png"}, null, null);
        registerTeam("red", 0xB24444, new String[] {"claysoldiers:textures/entity/soldiers/red.png"}, null, null);
        registerTeam("yellow", 0xD2D228, new String[] {"claysoldiers:textures/entity/soldiers/yellow.png"}, null, null);
        registerTeam("green", 0x309630, new String[] {"claysoldiers:textures/entity/soldiers/green.png"}, null, null);
        registerTeam("blue", 0x3458A4, new String[] {"claysoldiers:textures/entity/soldiers/blue.png"}, null, null);
    }

    private String name_;
    private ResourceLocation[] texturesDefault_;
    private ResourceLocation[] texturesRare_ = null;
    private ResourceLocation[] texturesUnique_ = null;
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
        registerTeam(teamName, "claysoldiers:doll_clay", iconColor, defTextures, rareTextures, uniqueTextures);
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
