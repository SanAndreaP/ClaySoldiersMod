/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.util;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;

@SideOnly(Side.CLIENT)
public final class Textures
{
    public static IIcon s_shieldIcon;
    public static IIcon s_shieldStudIcon;
    public static IIcon s_starfruitShieldIcon;
    public static IIcon s_starfruitShieldStudIcon;

    public static final ResourceLocation CLAYMAN_LEATHER_ARMOR = new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/wearables/leather.png");
    public static final ResourceLocation CLAYMAN_PADDING = new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/wearables/padding.png");
    public static final ResourceLocation CLAYMAN_GUNPOWDER = new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/wearables/gunpowder.png");
    public static final ResourceLocation CLAYMAN_MAGMACREAM = new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/wearables/magmacream.png");
    public static final ResourceLocation CLAYMAN_SLIMEFEET = new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/wearables/slimefeet.png");
    public static final ResourceLocation CLAYMAN_CROWN = new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/wearables/crown.png");
    public static final ResourceLocation CLAYMAN_LILYPANTS = new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/wearables/lilypants.png");
    public static final ResourceLocation CLAYMAN_GOGGLES = new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/wearables/goggles.png");
    public static final ResourceLocation CLAYMAN_CAPE_BLANK = new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/wearables/cape_blank.png");
    public static final ResourceLocation CLAYMAN_CAPE_DIAMOND = new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/wearables/cape_diamond.png");
    public static final ResourceLocation CLAYMAN_GOLD_HOODIE = new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/wearables/gold_hoodie.png");

    public static final ResourceLocation NEXUS_TEXTURE = new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/nexus/normal.png");
    public static final ResourceLocation NEXUS_GLOWING = new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/nexus/glow_map.png");

    public static final ResourceLocation NEXUS_PARTICLE = new ResourceLocation(ClaySoldiersMod.MOD_ID, "textures/entity/nexus/particle.png");

    @SubscribeEvent
    public void onTextureStitchPre(TextureStitchEvent.Pre event) {
        if( event.map.getTextureType() == 1 ) {
            s_shieldIcon = event.map.registerIcon(ClaySoldiersMod.MOD_ID + ":shield");
            s_shieldStudIcon = event.map.registerIcon(ClaySoldiersMod.MOD_ID + ":shield_studs");
            s_starfruitShieldIcon = event.map.registerIcon(ClaySoldiersMod.MOD_ID + ":starfruit");
            s_starfruitShieldStudIcon = event.map.registerIcon(ClaySoldiersMod.MOD_ID + ":starfruit_studs");
        }
    }
}
