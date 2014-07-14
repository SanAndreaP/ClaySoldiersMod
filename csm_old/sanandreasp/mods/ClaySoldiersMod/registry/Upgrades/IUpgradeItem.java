/*******************************************************************************************************************
* Name: IUpgradeItem.java
* Author: SanAndreasP
* Copyright: SanAndreasP and SilverChiren
* License: Attribution-NonCommercial-ShareAlike 3.0 Unported (http://creativecommons.org/licenses/by-nc-sa/3.0/)
*******************************************************************************************************************/

package sanandreasp.mods.ClaySoldiersMod.registry.Upgrades;

import java.util.Random;

import sanandreasp.mods.ClaySoldiersMod.entity.EntityClayMan;

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

public interface IUpgradeItem
{
    /**
     * The pickup method for the upgrade. Fired everytime a soldier tries to pick up an itemData.
     **/
    public void onPickup(IUpgradeEntity entity, EntityItem item, NBTTagCompound nbt);

    public void initUpgrade(IUpgradeEntity entity, NBTTagCompound nbt);

	/**
	 * The update method for the upgrade. Fired everytime a soldier updates.
	 **/
	public void onUpdate(IUpgradeEntity entity);

	/**
	 * The attack method for the upgrade. Fired every time a soldier hits something.
	 **/
	public float onAttack(IUpgradeEntity attacker, EntityLivingBase target, float initAmount);

    /**
     * The hit method for the upgrade. Fired every time a soldier gets hit.
     **/
    public float onHit(IUpgradeEntity attacker, DamageSource source, float initAmount);

	/**
	 * The drop method for the upgrade. Fired once a soldier dies.
	 **/
	public void onDrop(IUpgradeEntity entity, Random rnd);

    /**
     * The target method for the upgrade. Fired every time a soldier targets something.
     * Return null if you want to prevent targeting.
     **/
    public Entity onTargeting(IUpgradeEntity attacker, Entity target);

    /**
     * The target method for the upgrade. Fired every time a soldier gets targeted.
     * Return null if you want to prevent targeting.
     **/
    public Entity onTargeted(IUpgradeEntity target, Entity attacker);

    public float getTargetRange(IUpgradeEntity attacker);

    /**
     * The render equipped method for the upgrade. Fired every frame.
     * CLIENT-SIDE ONLY!
     **/
    @SideOnly(Side.CLIENT)
    public void onRenderEquipped(IUpgradeEntity entity, RenderManager manager, float partTicks, ModelBase model);
    /**
     * The pre render method for the upgrade holder. Fired every frame.
     * CLIENT-SIDE ONLY!
     **/
    @SideOnly(Side.CLIENT)
    public void onPreRender(IUpgradeEntity entity, RenderManager manager, float partTicks, ModelBase model);
    /**
     * The post render method for the upgrade holder. Fired every frame.
     * CLIENT-SIDE ONLY!
     **/
    @SideOnly(Side.CLIENT)
    public void onPostRender(IUpgradeEntity entity, RenderManager manager, float partTicks, ModelBase model);

	/**
	 * The break method for the upgrade. Fired once a soldier breaks its upgrade.
	 **/
	public void onBreak(IUpgradeEntity entity, Random rnd);

	/**
	 * The ItemStack which is supposed to be the upgrading itemData. Will be collected by the soldier.
	 **/
	public ItemStack getItemStack(IUpgradeEntity attacker);

	/**
	 * The ItemStack which shall be held when the soldier is upgraded. Return null for no held itemData.
	 * CLIENT-SIDE ONLY!
	 **/
	@SideOnly(Side.CLIENT)
	public ItemStack getHeldItem(IUpgradeEntity attacker);

	/** The upgrade type:<br>
	 * - 0: left-handed<br>
	 * - 1: right-handed<br>
	 * - 2: core<br>
	 * - 3: miscellaneous<br>
	 * - 4: behavior
	 **/
	public int getType();

	/**
	 * A check for the soldier if the upgrade is compatible with another upgrade.
	 **/
	public boolean isCompatibleWith(IUpgradeItem upgrade);
}
