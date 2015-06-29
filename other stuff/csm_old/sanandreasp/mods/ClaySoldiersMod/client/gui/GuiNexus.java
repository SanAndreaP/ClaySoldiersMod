/*******************************************************************************************************************
 * Name:      GuiNexus.java
 * Author:    SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License:   Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.client.gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.src.*;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayNexus;
import sanandreasp.mods.ClaySoldiersMod.inventory.ContainerNexus;
import sanandreasp.mods.ClaySoldiersMod.registry.CSMModRegistry;

public class GuiNexus extends GuiContainer {
    public EntityClayNexus nexus;
    private byte site;
    
    private GuiTextField waveDurationSecTxt;
    private GuiTextField spawnSoldiersMaxTxt;
    private GuiTextField maxLivingSoldiersTxt;
    private GuiTextField randNoneItemsTxt;
    private GuiTextField maxHealthTxt;
    
    private static ResourceLocation textures[] = new ResourceLocation[] {
        new ResourceLocation("claysoldiersmod", "textures/guis/NexusGUI1.png"),
        new ResourceLocation("claysoldiersmod", "textures/guis/NexusGUI2.png")
    };

	public GuiNexus(EntityPlayer player, EntityClayNexus par2Nexus, boolean active) {
		super(new ContainerNexus(player.inventory, par2Nexus, active));
//		clientNexus = par2Nexus;
		nexus = par2Nexus;
		allowUserInput = true;
		xSize = 176;
		ySize = 222;
		site = active ? (byte)0 : (byte)1;
	}
	
	@Override
	public void initGui() {
		super.initGui();
        int l = guiLeft;
        int i1 = guiTop;
        
        GuiButton items1 = new GuiButton(0, l-40, i1, 40, 20, "Items");
        GuiButton config = new GuiButton(1, l-40, i1+20, 40, 20, "Config");
        
        items1.enabled = site != 0;
        config.enabled = site != 1;
        
        buttonList.add(items1);
        buttonList.add(config);
        
        if (site == 1) {
        	waveDurationSecTxt = new GuiTextField(fontRenderer, l + 10, 110+i1, 30, 15);
        	waveDurationSecTxt.setText(Integer.toString(nexus.getWaveDurSec()));
        	spawnSoldiersMaxTxt = new GuiTextField(fontRenderer, l + 10, 110+i1+20, 30, 15);
        	spawnSoldiersMaxTxt.setText(Integer.toString(nexus.getMaxSpwnSoldiers()));
        	maxLivingSoldiersTxt = new GuiTextField(fontRenderer, l + 10, 110+i1+40, 30, 15);
        	maxLivingSoldiersTxt.setText(Integer.toString(nexus.getMaxLvngSoldiers()));
        	randNoneItemsTxt = new GuiTextField(fontRenderer, l + 10, 110+i1+60, 30, 15);
        	randNoneItemsTxt.setText(Integer.toString(nexus.getChanceGetNone()));
        	maxHealthTxt = new GuiTextField(fontRenderer, l + 10, 110+i1+80, 30, 15);
        	maxHealthTxt.setText(Integer.toString((int)nexus.getMaxHealth()));
        	
        	GuiButton heal = new GuiButton(2, l+115, i1+20, 50, 20, "Heal");
        	heal.enabled = (int)nexus.getHealth() < (int)nexus.getMaxHealth();
        	buttonList.add(heal);
        	
        	GuiButton restore = new GuiButton(3, l+115, i1+45, 50, 20, "Restore");
        	restore.enabled = nexus.isDestroyed();
        	buttonList.add(restore);
        	
        	GuiButton rnditems = new GuiButton(4, l+115, i1+70, 50, 20, "Toggle");
        	buttonList.add(rnditems);
        }
    	
    	GuiButton done = new GuiButton(5, l-50, i1+ySize - 20, 50, 20, "Done");
    	buttonList.add(done);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
		this.mc.getTextureManager().bindTexture(this.textures[site]);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int l = guiLeft;
        int i1 = guiTop;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glEnable(2903 /*GL_COLOR_MATERIAL*/);
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        
        String s = "Clay Nexus";
        s += site == 0 ? " - Items" : site == 1 ? " - Config" : "";
        fontRenderer.drawString(s, l + (xSize / 2) - (fontRenderer.getStringWidth(s) / 2), i1 + 5, 0x606060);
        
        if (site == 0) {
	        s = "Spawning";
	        fontRenderer.drawString(s, l + 27, i1 + 23, 0x303030);
	        fontRenderer.drawString(s, l + 117, i1 + 23, 0x303030);
	        s = "Soldier";
	        fontRenderer.drawString(s, l + 27, i1 + 26+fontRenderer.FONT_HEIGHT, 0x303030);
	        s = "Mount";
	        fontRenderer.drawString(s, l + 117, i1 + 26+fontRenderer.FONT_HEIGHT, 0x303030);
        } else if (site == 1) {
            this.waveDurationSecTxt.drawTextBox();
	        s = "Wave Duration";
	        fontRenderer.drawString(s, l + 45, 110+i1, 0x303030);
	        s = "In Seconds";
	        fontRenderer.drawString(s, l + 45, 110+i1+fontRenderer.FONT_HEIGHT, 0x303030);
	        
	        this.spawnSoldiersMaxTxt.drawTextBox();
	        s = "Spawned Soldiers";
	        fontRenderer.drawString(s, l + 45, 20+110+i1, 0x303030);
	        s = "Each Spawn";
	        fontRenderer.drawString(s, l + 45, 20+110+i1+fontRenderer.FONT_HEIGHT, 0x303030);
	        
	        this.maxLivingSoldiersTxt.drawTextBox();
	        s = "Max. Spawned Soldiers";
	        fontRenderer.drawString(s, l + 45, 40+110+i1, 0x303030);
	        s = "On Field";
	        fontRenderer.drawString(s, l + 45, 40+110+i1+fontRenderer.FONT_HEIGHT, 0x303030);
	        
	        this.randNoneItemsTxt.drawTextBox();
	        s = "Random Value For";
	        fontRenderer.drawString(s, l + 45, 60+110+i1, 0x303030);
	        s = "Soldiers Getting No Item";
	        fontRenderer.drawString(s, l + 45, 60+110+i1+fontRenderer.FONT_HEIGHT, 0x303030);
	        
	        this.maxHealthTxt.drawTextBox();
	        s = "Max. Health For";
	        fontRenderer.drawString(s, l + 45, 80+110+i1, 0x303030);
	        s = "Clay Nexus";
	        fontRenderer.drawString(s, l + 45, 80+110+i1+fontRenderer.FONT_HEIGHT, 0x303030);
	        
	        s = "Health: " + Integer.toString((int)((ContainerNexus)inventorySlots).nexus.getHealth()) + "HP / " + Integer.toString((int)((ContainerNexus)inventorySlots).nexus.getMaxHealth()) + "HP";
	        fontRenderer.drawString(s, l + 10, i1 + 17+fontRenderer.FONT_HEIGHT, 0x303030);
	        
	        s = ((ContainerNexus)inventorySlots).nexus.isDestroyed() ? "\2474Destroyed!" : "\2472not Destroyed";
	        fontRenderer.drawString(s, l + 10, i1 + 17+25+fontRenderer.FONT_HEIGHT, 0xFFFFFF);
	        
	        s = "Random Item Choice:";
	        fontRenderer.drawString(s, l + 10, i1 + 17+48+fontRenderer.FONT_HEIGHT, 0x303030);
	        
	        s = ((ContainerNexus)inventorySlots).nexus.hasRandItems() ? "\2472enabled" : "\2474disabled";
	        fontRenderer.drawString(s, l + 10+5, i1 + 17+48+fontRenderer.FONT_HEIGHT*2, 0xFFFFFF);
        }
        
        ((GuiButton)buttonList.get(2)).enabled = (int)nexus.getHealth() < (int)nexus.getMaxHealth();
    }
	
    @Override
	public void updateScreen()
    {
    	super.updateScreen();
        if (site == 1) {
        	this.waveDurationSecTxt.updateCursorCounter();
        	this.spawnSoldiersMaxTxt.updateCursorCounter();
        	this.maxLivingSoldiersTxt.updateCursorCounter();
        	this.randNoneItemsTxt.updateCursorCounter();
        	this.maxHealthTxt.updateCursorCounter();
        }
    }
    
    @Override
	protected void mouseClicked(int par1, int par2, int par3)
    {
        super.mouseClicked(par1, par2, par3);
        if (site == 1) {
        	this.waveDurationSecTxt.mouseClicked(par1, par2, par3);
        	this.spawnSoldiersMaxTxt.mouseClicked(par1, par2, par3);
        	this.maxLivingSoldiersTxt.mouseClicked(par1, par2, par3);
        	this.randNoneItemsTxt.mouseClicked(par1, par2, par3);
        	this.maxHealthTxt.mouseClicked(par1, par2, par3);
        }
    }
    
    @Override
	protected void keyTyped(char par1, int par2)
    {
    	super.keyTyped(par1, par2);
        if (site == 1) {
        	this.waveDurationSecTxt.textboxKeyTyped(par1, par2);
        	if (this.waveDurationSecTxt.getText().matches("[0-9]{1,3}")) {
        		int i = Integer.valueOf(this.waveDurationSecTxt.getText());
        		if (i > 255) i = 255;
        		if (i < 1) i = 1;
        		nexus.setWaveDurSec(i);
        	}
        	
        	this.spawnSoldiersMaxTxt.textboxKeyTyped(par1, par2);
        	if (this.spawnSoldiersMaxTxt.getText().matches("[0-9]{1,3}")) {
        		int i = Integer.valueOf(this.spawnSoldiersMaxTxt.getText());
        		if (i > 255) i = 255;
        		if (i < 1) i = 1;
        		nexus.setMaxSpwnSoldiers(i);
        	}
        	
        	this.maxLivingSoldiersTxt.textboxKeyTyped(par1, par2);
        	if (this.maxLivingSoldiersTxt.getText().matches("[0-9]{1,3}")) {
        		int i = Integer.valueOf(this.maxLivingSoldiersTxt.getText());
        		if (i > 255) i = 255;
        		if (i < 1) i = 1;
        		nexus.setMaxLvngSoldiers(i);
        	}
        	
        	this.randNoneItemsTxt.textboxKeyTyped(par1, par2);
        	if (this.randNoneItemsTxt.getText().matches("[0-9]{1,3}")) {
        		int i = Integer.valueOf(this.randNoneItemsTxt.getText());
        		if (i > 255) i = 255;
        		if (i < 0) i = 0;
        		nexus.setChanceGetNone(i);
        	}
        	
        	this.maxHealthTxt.textboxKeyTyped(par1, par2);
        	if (this.maxHealthTxt.getText().matches("[0-9]{1,3}")) {
        		int i = Integer.valueOf(this.maxHealthTxt.getText());
        		if (i > 255) i = 255;
        		if (i < 1) i = 1;
        		nexus.setSrvMaxHealth(i);
        		nexus.setHealth(i);
        	}
    		
    		byte[] entityIDByte = getEntityIdAsByteArray();
        	
        	Packet250CustomPayload packet = new Packet250CustomPayload("ClaySoldiers", new byte[] {
        			entityIDByte[0], entityIDByte[1], entityIDByte[2], entityIDByte[3], (byte)0,
        			(byte)nexus.getWaveDurSec(), (byte)nexus.getMaxSpwnSoldiers(), (byte)nexus.getMaxLvngSoldiers(),
        			(byte)nexus.getChanceGetNone(), (byte)nexus.getHealth(), (byte)nexus.getMaxHealth()
        	});
        	
        	CSMModRegistry.proxy.sendToServer(packet);
        }
    }
	
    Container prevInvSlots;
    
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		super.actionPerformed(par1GuiButton);
		if (par1GuiButton.id == 1 && site == 0) {
			site = 1;
			prevInvSlots = inventorySlots;
			inventorySlots = new ContainerNexus(((ContainerNexus)prevInvSlots).playerInv, ((ContainerNexus)prevInvSlots).nexus, false);
//			((CSM_ContainerNexus)this.inventorySlots).isActive = false;
//			mc.thePlayer.closeScreen();
//			mc.thePlayer.openGui(CSM_ModRegistry.instance, 1, serverNexus.worldObj, MathHelper.ceiling_double_int(serverNexus.posX-0.5D), MathHelper.ceiling_double_int(serverNexus.posY-0.5D), MathHelper.ceiling_double_int(serverNexus.posZ-0.5D));
		}
		
		if (site == 1) {
			if (par1GuiButton.id == 0) {
				site = 0;
//				inventorySlots = new CSM_ContainerNexus(mc.thePlayer.inventory, serverNexus, true);
				inventorySlots = prevInvSlots;
//				inventorySlots = new CSM_ContainerNexus(mc.thePlayer.inventory, ((CSM_ContainerNexus)this.inventorySlots).nexus, true);
//				mc.thePlayer.openGui(CSM_ModRegistry.instance, 0, mc.theWorld, MathHelper.ceiling_double_int(serverNexus.posX-0.5D), MathHelper.ceiling_double_int(serverNexus.posY-0.5D), MathHelper.ceiling_double_int(serverNexus.posZ-0.5D));
			} else if (par1GuiButton.id == 2) {
				nexus.setHealth(nexus.getMaxHealth());
//				((CSM_ContainerNexus)nexusplayer.craftingInventory).nexus.setSrvHealth(((CSM_ContainerNexus)nexusplayer.craftingInventory).nexus.getSrvMaxHealth());
			} else if (par1GuiButton.id == 3) {
				nexus.setDestroyed(false);
			} else if (par1GuiButton.id == 4) {
				nexus.setRandItems(!nexus.hasRandItems());
			}
		}
		
		if (par1GuiButton.id == 5) {
            this.mc.thePlayer.closeScreen();
            return;
		}
		
		mc.displayGuiScreen(this);
		
		byte[] entityIDByte = getEntityIdAsByteArray();
    	
    	Packet250CustomPayload packet = new Packet250CustomPayload("ClaySoldiers", new byte[] {
    			entityIDByte[0], entityIDByte[1], entityIDByte[2], entityIDByte[3], (byte)1,
    			(byte)nexus.getHealth(), (byte)(nexus.isDestroyed() ? 1 : 0), (byte)(nexus.hasRandItems() ? 1 : 0)
    	});
    	
    	CSMModRegistry.proxy.sendToServer(packet);
    }
    
    private byte[] getEntityIdAsByteArray() {
    	return new byte[] {
    			(byte)(nexus.entityId & 255),
    			(byte)(((nexus.entityId) >> 8) & 255),
    			(byte)(((nexus.entityId) >> 16) & 255),
    			(byte)(((nexus.entityId) >> 24) & 255),
    	};
    }

}
