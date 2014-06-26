/*******************************************************************************************************************
 * Name: ItemDebugShield.java
 * Author: SanAndreasP
 * Copyright: SanAndreasP and SilverChiren
 * License: Attribution-NonCommercial-ShareAlike 3.0 Unported
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/)
 *******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDebugShield extends ItemGlobal
{
    
    @SideOnly(Side.CLIENT)
    private Icon studdedIcon, starIcon, studStarIcon;
    private Icon[] icoList;
    
    public ItemDebugShield(int par1)
    {
        super(par1);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("ClaySoldiersMod:shield");
        this.studdedIcon =
                par1IconRegister.registerIcon("ClaySoldiersMod:shields");
        this.starIcon =
                par1IconRegister.registerIcon("ClaySoldiersMod:starfruitSlice");
        this.studStarIcon =
                par1IconRegister.registerIcon("ClaySoldiersMod:starfruitStuds");
        this.icoList = new Icon[4];
        this.icoList[0] = this.itemIcon;
        this.icoList[1] = this.studdedIcon;
        this.icoList[2] = this.starIcon;
        this.icoList[3] = this.studStarIcon;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int par1)
    {
        return this.icoList[par1];
    }
    
}
