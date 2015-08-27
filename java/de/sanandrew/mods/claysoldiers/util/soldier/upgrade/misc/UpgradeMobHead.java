/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.util.soldier.upgrade.misc;

import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgradeInst;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.util.Constants.NBT;

public class UpgradeMobHead
        extends AUpgradeMisc
{
    @Override
    public void onClientUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        ItemStack headEquip = clayMan.getEquipmentInSlot(4);
        if( headEquip == null || headEquip.getItem() != Items.skull ) {
            ItemStack newSkull = new ItemStack(Items.skull, 1, upgradeInst.getNbtTag().getShort("itemMeta"));
            NBTTagCompound newNbt = null;

            if( upgradeInst.getNbtTag().hasKey("skullNbtTag", NBT.TAG_COMPOUND) ) {
                newNbt = new NBTTagCompound();
                newNbt.setTag("SkullOwner", upgradeInst.getNbtTag().getCompoundTag("skullNbtTag"));
            } else if( upgradeInst.getNbtTag().hasKey("skullNbtTag", NBT.TAG_STRING) ) {
                newNbt = new NBTTagCompound();
                newNbt.setString("SkullOwner", upgradeInst.getNbtTag().getString("skullNbtTag"));
            }

            if( newNbt != null ) {
                newSkull.setTagCompound(newNbt);
            }

            clayMan.setCurrentItemOrArmor(4, newSkull);
        }
    }

    @Override
    public void onPickup(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, ItemStack stack) {
        this.consumeItem(stack, upgradeInst);
        clayMan.playSound("random.pop", 1.0F, 1.0F);

        if( stack.hasTagCompound() ) {
            NBTTagCompound stackTag = stack.getTagCompound();
            if( stackTag.hasKey("SkullOwner", NBT.TAG_COMPOUND) ) {
                upgradeInst.getNbtTag().setTag("skullNbtTag", stackTag.getCompoundTag("SkullOwner"));
            } else if( stackTag.hasKey("SkullOwner", NBT.TAG_STRING) && !StringUtils.isNullOrEmpty(stackTag.getString("SkullOwner")) ) {
                upgradeInst.getNbtTag().setString("skullNbtTag", stackTag.getString("SkullOwner"));
            }
        }

        upgradeInst.getNbtTag().setShort("itemMeta", (short) stack.getItemDamage());
    }

    @Override
    public boolean shouldNbtSyncToClient(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        return true;
    }
}
