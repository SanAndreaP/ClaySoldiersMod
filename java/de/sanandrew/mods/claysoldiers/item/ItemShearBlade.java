package de.sanandrew.mods.claysoldiers.item;

import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class ItemShearBlade
    extends Item
{
    public ItemShearBlade() {
        super();
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(CSM_Main.MOD_ID + ":shear_blade");
    }
}
