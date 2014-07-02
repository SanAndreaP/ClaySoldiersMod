/*******************************************************************************************************************
* Name: MiscUpgrade.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.misc;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeEntity;
import sanandreasp.mods.ClaySoldiersMod.registry.Upgrades.IUpgradeItem;

public abstract class MiscUpgrade implements IUpgradeItem {
    
	@Override
	public final int getType() {
		return 3;
	}
    
    @Override
    public void onPickup(IUpgradeEntity entity, EntityItem item, NBTTagCompound nbt) {
        entity.getEntity().playSound("random.pop",
                0.2F,
                ((entity.getEntity().getRNG().nextFloat() - entity.getEntity().getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F
        );
        item.getEntityItem().stackSize--;
        if( item.getEntityItem().stackSize <= 0 ) {
            entity.getEntity().onItemPickup(item, 1);
            item.setDead();
        }
        this.initUpgrade(entity, nbt);
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onPreRender(IUpgradeEntity entity, RenderManager manager, float partTicks, ModelBase model) {
        
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onPostRender(IUpgradeEntity entity, RenderManager manager, float partTicks, ModelBase model) {
        
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void onRenderEquipped(IUpgradeEntity entity, RenderManager manager, float partTicks, ModelBase model) {
        
    }

    @Override
    public void onUpdate(IUpgradeEntity entity) {

    }

    @Override
    public void onDrop(IUpgradeEntity entity, Random rnd) {
        entity.getEntity().entityDropItem(new ItemStack(this.getItemStack(entity).itemID, this.getItemStack(entity).getItemDamage(), 1), 0.0F);
    }

    @Override
    public void onBreak(IUpgradeEntity entity, Random rnd) {

    }

	@Override
	public boolean isCompatibleWith(IUpgradeItem upgrade) {
		return true;
	}
    
    @Override
    public Entity onTargeting(IUpgradeEntity attacker, Entity target) {
        return target;
    }
    
    @Override
    public Entity onTargeted(IUpgradeEntity target, Entity attacker) {
        return target.getEntity();
    }
    
    @Override
    public float onHit(IUpgradeEntity entity, DamageSource source, float initAmount) {
        return initAmount;
    }
    
    @Override
    public float onAttack(IUpgradeEntity entity, EntityLivingBase target, float initAmount) {
        return initAmount;
    }
    
    @Override
    public float getTargetRange(IUpgradeEntity entity) {
        return 0.0F;
    }
}
