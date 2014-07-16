package de.sanandrew.mods.claysoldiers.client.event;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.core.manpack.util.SAPUtils.RGBAValues;
import de.sanandrew.core.manpack.util.javatuples.Triplet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.mounts.EntityHorseMount;
import de.sanandrew.mods.claysoldiers.entity.mounts.EnumHorseType;
import de.sanandrew.mods.claysoldiers.util.ModConfig;
import de.sanandrew.mods.claysoldiers.util.ModItems;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class RenderHudOverlayEvent
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
                && mc.thePlayer.getCurrentEquippedItem().getItem() == ModItems.statDisplay )
        {
            this.renderSoldiers(mc);
            this.renderMounts(mc);
        }
    }

    private void renderSoldiers(Minecraft mc) {
        @SuppressWarnings("unchecked")
        List<EntityClayMan> soldiers = mc.theWorld.getEntitiesWithinAABB(EntityClayMan.class, this.getRangeAabbFromPlayer(mc.thePlayer));
        List<Triplet<Integer, String, Integer>> teams = new ArrayList<>();

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
            ClaymanTeam teamInst = ClaymanTeam.getTeamFromName(team.getKey());
            teams.add(Triplet.with(teamInst.getTeamColor(), team.getKey(), team.getValue()));
        }
        renderStats(mc, "Soldiers", teams, 5, 5);
    }

    private void renderMounts(Minecraft mc) {
        @SuppressWarnings("unchecked")
        List<EntityHorseMount> horses = mc.theWorld.getEntitiesWithinAABB(EntityHorseMount.class, this.getRangeAabbFromPlayer(mc.thePlayer));
        List<Triplet<Integer, String, Integer>> teams = new ArrayList<>();

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
            teams.add(Triplet.with(teamInst.typeColor, team.getKey(), team.getValue()));
        }
        renderStats(mc, "Mounts", teams, 110, 5);
    }

    private void renderStats(Minecraft mc, String title, List<Triplet<Integer, String, Integer>> teams, int xPos, int yPos) {
        this.drawGradientRect(xPos, yPos, xPos + 100, yPos + 13, 0x00000000, 0x80FFFFFF);

        this.titleRenderer.drawString(title, xPos + 50 - this.titleRenderer.getStringWidth(title) / 2 - 1, yPos + 1, 0x000000);
        this.titleRenderer.drawString(title, xPos + 50 - this.titleRenderer.getStringWidth(title) / 2    , yPos + 2, 0x000000);
        this.titleRenderer.drawString(title, xPos + 50 - this.titleRenderer.getStringWidth(title) / 2 + 1, yPos + 1, 0x000000);
        this.titleRenderer.drawString(title, xPos + 50 - this.titleRenderer.getStringWidth(title) / 2    , yPos    , 0x000000);
        this.titleRenderer.drawString(title, xPos + 50 - this.titleRenderer.getStringWidth(title) / 2    , yPos + 1, 0xFFFFFF);

        this.drawGradientRect(xPos - 1  , yPos-1  , xPos      , yPos + 13, 0x00000000, 0xC0000000);
        this.drawGradientRect(xPos + 100, yPos - 1, xPos + 101, yPos + 13, 0x00000000, 0xC0000000);

        int pos = 0;
        for( Triplet<Integer, String, Integer> team : teams ) {
            String text = team.getValue1() + ": " + team.getValue2().toString();
            drawRect(xPos, yPos + 13 + pos * 11, xPos + 100, yPos + 24 + pos * 11, 0x80FFFFFF);
            drawRect(xPos, yPos + 13 + pos * 11, xPos + 100, yPos + 23 + pos * 11, 0xC0000000 | team.getValue0());
            this.statRenderer.drawString(text, xPos + 50 - this.statRenderer.getStringWidth(text) / 2, yPos + 14 + pos * 11,
                                         this.getContrastTextColor(team.getValue0())
            );
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
