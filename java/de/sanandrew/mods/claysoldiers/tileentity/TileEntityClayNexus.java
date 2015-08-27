/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.tileentity;

import cpw.mods.fml.common.FMLLog;
import de.sanandrew.core.manpack.util.helpers.SAPUtils;
import de.sanandrew.core.manpack.util.helpers.SAPUtils.RGBAValues;
import de.sanandrew.core.manpack.util.javatuples.Sextet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.projectile.ISoldierProjectile;
import de.sanandrew.mods.claysoldiers.item.IMountDoll;
import de.sanandrew.mods.claysoldiers.item.ItemClayManDoll;
import de.sanandrew.mods.claysoldiers.network.packet.EnumParticleFx;
import de.sanandrew.mods.claysoldiers.util.BugfixHelper;
import de.sanandrew.mods.claysoldiers.util.ClaySoldiersMod;
import de.sanandrew.mods.claysoldiers.util.RegistryItems;
import de.sanandrew.mods.claysoldiers.util.soldier.ClaymanTeam;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.ASoldierUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.IThrowableUpgrade;
import de.sanandrew.mods.claysoldiers.util.soldier.upgrade.SoldierUpgrades;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import org.apache.logging.log4j.Level;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

public class TileEntityClayNexus
        extends TileEntity
        implements IInventory
{
    public static final int SOLDIER_SLOT = 0;
    public static final int THROWABLE_SLOT = 1;
    public static final int MOUNT_SLOT = 2;

    public boolean isActive = false;
    private boolean p_prevRsPowerState = false;

    public int spawnSoldierInterval = 40;
    public int spawnThrowableInterval = 30;
    public int soldierSpawnCount = 10;
    public int mountSpawnCount = 10;
    public int maxSoldierCount = 10;

    public long ticksActive = 0L;

    public float spinAngle = 0.0F;
    public float prevSpinAngle = 0.0F;

    protected String customName;

    private ItemStack[] p_upgradeItems = new ItemStack[36];
    private ItemStack p_soldierSlot;
    private ItemStack p_throwableSlot;
    private ItemStack p_mountSlot;
    private int p_spawningSoldierCounter = 0;
    private int p_prevSpawningSoldierCounter = 0;
    private float p_health = 20.0F;
    private float p_maxHealth = 20.0F;

    private ClaymanTeam p_tempClayTeam = ClaymanTeam.NULL_TEAM;
    private Class<? extends ISoldierProjectile<? extends EntityThrowable>> p_tempThrowableCls = null;
    private AxisAlignedBB p_searchArea;
    private AxisAlignedBB p_damageArea;

    public TileEntityClayNexus() {
    }

    @Override
    public void updateEntity() {
        if( this.p_searchArea == null || this.p_damageArea == null ) {
            this.p_searchArea = AxisAlignedBB.getBoundingBox(this.xCoord - 63.0D, this.yCoord - 63.0D, this.zCoord - 63.0D,
                                                            this.xCoord + 64.0D, this.yCoord + 64.0D, this.zCoord + 64.0D
            );
            this.p_damageArea = AxisAlignedBB.getBoundingBox(this.xCoord + 0.1D, this.yCoord + 0.1D, this.zCoord + 0.1D,
                                                            this.xCoord + 0.9D, this.yCoord + 0.9D, this.zCoord + 0.9D
            );
        }

        super.updateEntity();

        boolean isRsPowered = this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)
                || this.worldObj.getIndirectPowerLevelTo(this.xCoord - 1, this.yCoord, this.zCoord, 1) > 0
                || this.worldObj.getIndirectPowerLevelTo(this.xCoord, this.yCoord, this.zCoord + 1, 1) > 0
                || this.worldObj.getIndirectPowerLevelTo(this.xCoord, this.yCoord, this.zCoord - 1, 1) > 0
                || this.worldObj.getIndirectPowerLevelTo(this.xCoord + 1, this.yCoord, this.zCoord, 1) > 0;

        if( !this.worldObj.isRemote && !this.p_prevRsPowerState && isRsPowered ) {
            this.isActive = !this.isActive;
            this.markDirty();
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
        this.p_prevRsPowerState = isRsPowered;

        if( this.isActive ) {
            if( this.p_health <= 0.0F ) {
                this.isActive = false;
            } else {
                this.ticksActive++;
            }

            if( !this.worldObj.isRemote && this.p_health > 0.0F ) {
                if( this.ticksActive % 20 == 0 ) {
                    int dmgEnemies = this.countDamagingEnemies();
                    if( dmgEnemies > 0 ) {
                        float healthDamage = 0.5F;

                        if( dmgEnemies > 1 ) {
                            healthDamage = 0.125F * dmgEnemies;
                        }

                        this.p_health -= healthDamage;

                        if( this.p_health < 0.0F ) {
                            this.p_health = 0.0F;
                        }

                        this.markDirty();
                        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
                    }

                    List<EntityClayMan> enemies = this.getEnemies(true);
                    for( EntityClayMan dalek : enemies ) {
                        if( !dalek.hasPath() ) {
                            dalek.setPathToEntity(BugfixHelper.getEntityPathToXYZ(this.worldObj, dalek, this.xCoord, this.yCoord, this.zCoord, 64.0F,
                                                                                  true, false, false, true)
                            );
                        }
                    }
                }

                if( ticksActive % this.spawnThrowableInterval == 0 && this.p_throwableSlot != null ) {
                    List<EntityClayMan> clayMen = getEnemies(true);
                    if( clayMen.size() > 0 ) {
                        EntityClayMan target = clayMen.get(SAPUtils.RNG.nextInt(clayMen.size()));

                        double deltaX = target.posX - this.xCoord + 0.5F;
                        double deltaZ = target.posZ - this.zCoord + 0.5F;

                        try {
                            ISoldierProjectile<? extends EntityThrowable> projectile =
                                    this.p_tempThrowableCls.getConstructor(World.class, double.class, double.class, double.class)
                                                          .newInstance(this.worldObj, this.xCoord + 0.5F, this.yCoord + 0.875F, this.zCoord + 0.5F);
                            projectile.initProjectile(target, true, this.p_tempClayTeam.getTeamName());
                            EntityThrowable throwable = projectile.getProjectileEntity();

                            double d2 = (target.posY + target.getEyeHeight()) - 0.10000000298023224D - throwable.posY;
                            float f1 = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ) * 0.2F;
                            this.worldObj.spawnEntityInWorld(throwable);
                            throwable.setThrowableHeading(deltaX, d2 + f1, deltaZ, 0.6F, 12.0F);
                        } catch( InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e ) {
                            FMLLog.log(ClaySoldiersMod.MOD_LOG, Level.ERROR, "%1$s cannot be instantiated! %1$s is not thrown to target!", this.p_tempThrowableCls.getName());
                            e.printStackTrace();
                        }
                    }
                }

                if( this.p_soldierSlot != null ) {
                    if( this.ticksActive % this.spawnSoldierInterval == 0 && this.p_spawningSoldierCounter <= 0 ) {
                        this.p_spawningSoldierCounter = Math.min(this.soldierSpawnCount, this.maxSoldierCount - this.countTeammates());
                        this.p_prevSpawningSoldierCounter = this.p_spawningSoldierCounter;
                    }

                    if( ticksActive % 5 == 0 ) {
                        if( this.p_spawningSoldierCounter > 0 ) {
                            this.p_spawningSoldierCounter = Math.min(this.p_prevSpawningSoldierCounter, this.maxSoldierCount - this.countTeammates());
                            this.p_prevSpawningSoldierCounter = this.p_spawningSoldierCounter - 1;
                            if( this.p_spawningSoldierCounter > 0 ) {
                                ItemClayManDoll.spawnClayMan(this.worldObj, this.p_tempClayTeam.getTeamName(), this.xCoord + 0.5F, this.yCoord + 0.2D,
                                                             this.zCoord + 0.5F
                                ).nexusSpawn = true;
                            }
                        }
                    }
                }
            }
        }

        if( this.worldObj.isRemote ) {
            this.prevSpinAngle = this.spinAngle;
            if( this.isActive && this.p_health > 0.0F ) {
                this.spinAngle += 4;
                RGBAValues rgba = SAPUtils.getRgbaFromColorInt(ItemClayManDoll.getTeam(this.p_soldierSlot).getTeamColor());
                ClaySoldiersMod.proxy.spawnParticles(EnumParticleFx.FX_NEXUS, Sextet.with((double) this.xCoord, (double) this.yCoord, (double) this.zCoord,
                                                                                          rgba.getRed() / 255.0F, rgba.getGreen() / 255.0F, rgba.getBlue() / 255.0F)
                );
            } else if( this.spinAngle % 90 != 0 ) {
                this.spinAngle += 2;
            }

            if( this.spinAngle >= 360 ) {
                this.prevSpinAngle = -1;
                this.spinAngle = 0;
            }
        }
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public int getSizeInventory() {
        return 39;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        switch( slot ) {
            case SOLDIER_SLOT:
                return p_soldierSlot;
            case THROWABLE_SLOT:
                return p_throwableSlot;
            case MOUNT_SLOT:
                return p_mountSlot;
            default:
                return this.p_upgradeItems[slot - 3];
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int reduceAmount) {
        ItemStack stack = this.getStackInSlot(slot);

        if( stack != null ) {
            if( stack.stackSize <= reduceAmount ) {
                this.setStackInSlot(slot, null);
                this.markDirty();
                return stack;
            } else {
                ItemStack splitStack = stack.splitStack(reduceAmount);

                if( stack.stackSize == 0 ) {
                    this.setStackInSlot(slot, null);
                }

                this.markDirty();
                return splitStack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = this.getStackInSlot(slot);
        if( stack != null ) {
            this.setStackInSlot(slot, null);
            return stack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack newStack) {
        this.setStackInSlot(slot, newStack);

        if( newStack != null && newStack.stackSize > this.getInventoryStackLimit() ) {
            newStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : ClaySoldiersMod.MOD_ID + ":container.nexus";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.customName != null;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this
               && player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        switch( slot ) {
            case SOLDIER_SLOT:
                return stack == null || stack.getItem() == RegistryItems.dollSoldier;
            case THROWABLE_SLOT:
                return stack == null || SoldierUpgrades.getUpgrade(stack) instanceof IThrowableUpgrade;
            case MOUNT_SLOT:
                return stack == null || stack.getItem() instanceof IMountDoll;
            default:
                return stack == null || SoldierUpgrades.getUpgrade(stack) != null;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.p_upgradeItems = new ItemStack[36];

        NBTTagList nbttaglist = nbt.getTagList("items", NBT.TAG_COMPOUND);
        for( int i = 0; i < nbttaglist.tagCount(); i++ ) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int slot = nbttagcompound1.getByte("slot") & 255;

            if( slot >= 0 && slot < this.getSizeInventory() ) {
                this.setStackInSlot(slot, ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }

        this.isActive = nbt.getBoolean("active");
        this.p_health = nbt.getFloat("health");
        this.p_maxHealth = nbt.getFloat("maxHealth");
        this.spawnSoldierInterval = nbt.getInteger("spawnSldInterval");
        this.spawnThrowableInterval = nbt.getInteger("spawnThrwInterval");
        this.soldierSpawnCount = nbt.getInteger("spawnSldCount");
        this.mountSpawnCount = nbt.getInteger("spawnMntCount");
        this.maxSoldierCount = nbt.getInteger("maxSldCount");

        if( nbt.hasKey("customName") ) {
            customName = nbt.getString("customName");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        NBTTagList nbttaglist = new NBTTagList();
        for( int slot = 0; slot < this.getSizeInventory(); slot++ ) {
            ItemStack slotStack = this.getStackInSlot(slot);
            if( slotStack != null ) {
                NBTTagCompound itemTag = new NBTTagCompound();
                itemTag.setByte("slot", (byte) slot);
                slotStack.writeToNBT(itemTag);
                nbttaglist.appendTag(itemTag);
            }
        }

        nbt.setTag("items", nbttaglist);

        nbt.setBoolean("active", this.isActive);
        nbt.setFloat("health", this.p_health);
        nbt.setFloat("maxHealth", this.p_maxHealth);
        nbt.setInteger("spawnSldInterval", this.spawnSoldierInterval);
        nbt.setInteger("spawnThrwInterval", this.spawnThrowableInterval);
        nbt.setInteger("spawnSldCount", this.soldierSpawnCount);
        nbt.setInteger("spawnMntCount", this.mountSpawnCount);
        nbt.setInteger("maxSldCount", this.maxSoldierCount);

        if( this.hasCustomInventoryName() ) {
            nbt.setString("customName", this.customName);
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
        this.worldObj.notifyBlockChange(xCoord, yCoord, zCoord, blockType);
    }

    public float getHealth() {
        return this.p_health;
    }

    public float getMaxHealth() {
        return this.p_maxHealth;
    }

    public void heal(float amount) {
        this.p_health += amount;
        if( this.p_health > this.p_maxHealth ) {
            this.p_health = this.p_maxHealth;
        }
    }

    public void repair() {
        this.p_health = this.p_maxHealth;
    }

    public boolean canEntityBeSeen(Entity target) {
        return this.worldObj.func_147447_a(Vec3.createVectorHelper(this.xCoord + 0.5D, this.yCoord + 0.9D, this.zCoord + 0.5D),
                                           Vec3.createVectorHelper(target.posX, target.posY + target.getEyeHeight(), target.posZ), false, true, false) == null;
    }

    private int countTeammates() {
        @SuppressWarnings("unchecked")
        List<EntityClayMan> soldiers = this.worldObj.getEntitiesWithinAABB(EntityClayMan.class, this.p_searchArea);
        int cnt = 0;
        for( EntityClayMan dodger : soldiers ) {
            if( dodger.getClayTeam().equals(this.p_tempClayTeam.getTeamName()) ) {
                cnt++;
            }
        }
        return cnt;
    }

    private List<EntityClayMan> getEnemies(boolean mustBeSeen) {
        @SuppressWarnings("unchecked")
        List<EntityClayMan> soldiers = this.worldObj.getEntitiesWithinAABB(EntityClayMan.class, this.p_searchArea);
        Iterator<EntityClayMan> iterator = soldiers.iterator();
        while( iterator.hasNext() ) {
            EntityClayMan roomie = iterator.next();
            if( roomie.getClayTeam().equals(this.p_tempClayTeam.getTeamName()) || (mustBeSeen && !this.canEntityBeSeen(roomie)) ) {
                iterator.remove();
            }
        }
        return soldiers;
    }

    private int countDamagingEnemies() {
        @SuppressWarnings("unchecked")
        List<EntityClayMan> soldiers = this.worldObj.getEntitiesWithinAABB(EntityClayMan.class, this.p_damageArea);
        int cnt = 0;
        for( EntityClayMan dodger : soldiers ) {
            if( !dodger.getClayTeam().equals(this.p_tempClayTeam.getTeamName()) ) {
                cnt++;
            }
        }
        return cnt;
    }

    private void setStackInSlot(int slot, ItemStack stack) {
        switch( slot ) {
            case SOLDIER_SLOT:
                this.p_soldierSlot = stack;
                this.p_tempClayTeam = ItemClayManDoll.getTeam(stack);
                break;
            case THROWABLE_SLOT:
                ASoldierUpgrade upg = SoldierUpgrades.getUpgrade(stack);
                this.p_tempThrowableCls = stack != null && upg instanceof IThrowableUpgrade ? ((IThrowableUpgrade) upg).getThrowableClass() : null;
                this.p_throwableSlot = stack;
                break;
            case MOUNT_SLOT:
                this.p_mountSlot = stack;
                break;
            default:
                this.p_upgradeItems[slot - 3] = stack;
        }
    }
}
