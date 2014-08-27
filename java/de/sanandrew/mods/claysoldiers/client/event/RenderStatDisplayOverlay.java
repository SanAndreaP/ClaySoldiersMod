/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.event;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.core.manpack.util.SAPUtils.RGBAValues;
import de.sanandrew.core.manpack.util.client.ItemRenderHelper;
import de.sanandrew.core.manpack.util.javatuples.Quartet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityHorseMount;
import de.sanandrew.mods.claysoldiers.entity.mount.EnumHorseType;
import de.sanandrew.mods.claysoldiers.item.ItemClayManDoll;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.ModConfig;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RenderStatDisplayOverlay
    extends Gui
{
    private FontRenderer titleRenderer = null;
    private FontRenderer statRenderer = null;

    @SubscribeEvent
    public void renderText(RenderGameOverlayEvent.Text event) {
        Minecraft mc = Minecraft.getMinecraft();

        if( this.titleRenderer == null ) {
            this.titleRenderer = mc.fontRenderer;
        }

        if( this.statRenderer == null ) {
            this.statRenderer = new FontRenderer(mc.gameSettings, new ResourceLocation("textures/font/ascii.png"), mc.renderEngine, true);
            if( mc.gameSettings.language != null ) {
                this.statRenderer.setBidiFlag(mc.getLanguageManager().isCurrentLanguageBidirectional());
            }
        }

        if( !mc.gameSettings.showDebugInfo && mc.thePlayer.getCurrentEquippedItem() != null
                && mc.thePlayer.getCurrentEquippedItem().getItem() == RegistryItems.statDisplay )
        {
            this.renderSoldiers(mc);
            this.renderMounts(mc);
        }
    }

    private void renderSoldiers(Minecraft mc) {
        @SuppressWarnings("unchecked")
        List<EntityClayMan> soldiers = mc.theWorld.getEntitiesWithinAABB(EntityClayMan.class, this.getRangeAabbFromPlayer(mc.thePlayer));
        List<Quartet<Integer, String, Integer, ItemStack>> teams = new ArrayList<>(); // team background color, team name, team count

        Map<String, Integer> teamCounts = Maps.newHashMap();
        for( EntityClayMan dex : soldiers ) {
            String team = dex.getClayTeam();
            if( teamCounts.containsKey(team) ) {
                teamCounts.put(team, teamCounts.get(team) + 1);
            } else {
                teamCounts.put(team, 1);
            }
        }

        for( Entry<String, Integer> team : teamCounts.entrySet() ) {
            ClaymanTeam teamInst = ClaymanTeam.getTeam(team.getKey());
            ItemStack renderedItem = new ItemStack(RegistryItems.dollSoldier);
            ItemClayManDoll.setTeamForItem(team.getKey(), renderedItem);
            teams.add(Quartet.with(teamInst.getTeamColor(), renderedItem.getUnlocalizedName() + ".color", team.getValue(), renderedItem));
        }

        this.renderStats(mc, SAPUtils.getTranslated(RegistryItems.statDisplay.getUnlocalizedName() + ".title.soldiers"), teams, 5, 5);
    }

    private void renderMounts(Minecraft mc) {
        @SuppressWarnings("unchecked")
        List<EntityHorseMount> horses = mc.theWorld.getEntitiesWithinAABB(EntityHorseMount.class, this.getRangeAabbFromPlayer(mc.thePlayer));
        List<Quartet<Integer, String, Integer, ItemStack>> teams = new ArrayList<>();

        Map<String, Integer> teamCounts = Maps.newHashMap();
        for( EntityHorseMount dex : horses ) {
            String team = EnumHorseType.values[dex.getType()].toString();
            if( teamCounts.containsKey(team) ) {
                teamCounts.put(team, teamCounts.get(team) + 1);
            } else {
                teamCounts.put(team, 1);
            }
        }

        for( Entry<String, Integer> team : teamCounts.entrySet() ) {
            EnumHorseType teamInst = EnumHorseType.valueOf(team.getKey());
            teams.add(Quartet.with(teamInst.typeColor, team.getKey(), team.getValue(), (ItemStack) null));
        }

        this.renderStats(mc, SAPUtils.getTranslated(RegistryItems.statDisplay.getUnlocalizedName() + ".title.mounts"), teams, 110, 5);
    }

    private void renderStats(Minecraft mc, String title, List<Quartet<Integer, String, Integer, ItemStack>> teams, int xPos, int yPos) {
        this.drawGradientRect(xPos, yPos, xPos + 100, yPos + 13, 0x00000000, 0x80FFFFFF);

        this.titleRenderer.drawString(title, xPos + 50 - this.titleRenderer.getStringWidth(title) / 2 - 1, yPos + 1, 0x000000);
        this.titleRenderer.drawString(title, xPos + 50 - this.titleRenderer.getStringWidth(title) / 2    , yPos + 2, 0x000000);
        this.titleRenderer.drawString(title, xPos + 50 - this.titleRenderer.getStringWidth(title) / 2 + 1, yPos + 1, 0x000000);
        this.titleRenderer.drawString(title, xPos + 50 - this.titleRenderer.getStringWidth(title) / 2    , yPos    , 0x000000);
        this.titleRenderer.drawString(title, xPos + 50 - this.titleRenderer.getStringWidth(title) / 2    , yPos + 1, 0xFFFFFF);

        this.drawGradientRect(xPos - 1  , yPos-1  , xPos      , yPos + 13, 0x00000000, 0xC0000000);
        this.drawGradientRect(xPos + 100, yPos - 1, xPos + 101, yPos + 13, 0x00000000, 0xC0000000);

        int pos = 0;
        for( Quartet<Integer, String, Integer, ItemStack> team : teams ) {
            String text = SAPUtils.getTranslated(team.getValue1()) + ": " + team.getValue2().toString();
            drawRect(xPos, yPos + 13 + pos * 11, xPos + 100, yPos + 24 + pos * 11, 0x80FFFFFF);
            drawRect(xPos, yPos + 13 + pos * 11, xPos + 100, yPos + 23 + pos * 11, 0xC0000000 | team.getValue0());
            this.statRenderer.drawString(text, xPos + 50 - this.statRenderer.getStringWidth(text) / 2, yPos + 14 + pos * 11,
                                         this.getContrastTextColor(team.getValue0())
            );

            GL11.glPushMatrix();
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            ItemRenderHelper.renderItemInGui(mc, team.getValue3(), xPos * 2, (yPos + 14 + pos * 11) * 2);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();

            pos++;
        }

        drawRect(xPos - 1  , yPos + 13, xPos      , yPos + 13 + pos * 11, 0xC0000000);
        drawRect(xPos + 100, yPos + 13, xPos + 101, yPos + 13 + pos * 11, 0xC0000000);

        this.drawGradientRect(xPos - 1  , yPos + 13 + pos * 11, xPos      , yPos + 19 + pos * 11, 0xC0000000, 0x00000000);
        this.drawGradientRect(xPos + 100, yPos + 13 + pos * 11, xPos + 101, yPos + 19 + pos * 11, 0xC0000000, 0x00000000);
        this.drawGradientRect(xPos      , yPos + 13 + pos * 11, xPos + 100, yPos + 18 + pos * 11, 0x80FFFFFF, 0x00000000);
    }

    private AxisAlignedBB getRangeAabbFromPlayer(EntityPlayer player) {
        return AxisAlignedBB.getBoundingBox(player.posX - ModConfig.statItemRange, player.posY - ModConfig.statItemRange, player.posZ - ModConfig.statItemRange,
                                            player.posX + ModConfig.statItemRange, player.posY + ModConfig.statItemRange, player.posZ + ModConfig.statItemRange);
    }

    private int getContrastTextColor(int bkgColor) {
        RGBAValues splitClr = SAPUtils.getRgbaFromColorInt(bkgColor);
        int yiq = ((splitClr.getRed()*299) + (splitClr.getGreen()*587) + (splitClr.getBlue()*144)) / 1000;
        return (yiq >= 128) ? 0x000000 : 0xFFFFFF;
    }
}
