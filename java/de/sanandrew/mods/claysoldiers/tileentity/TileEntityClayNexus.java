/*******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP, SilverChiren and CliffracerX
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.tileentity;

import cpw.mods.fml.common.FMLLog;
import de.sanandrew.core.manpack.util.NbtTypes;
import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.core.manpack.util.SAPUtils.RGBAValues;
import de.sanandrew.core.manpack.util.javatuples.Sextet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.entity.projectile.ISoldierProjectile;
import de.sanandrew.mods.claysoldiers.item.IMountDoll;
import de.sanandrew.mods.claysoldiers.item.ItemClayManDoll;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import de.sanandrew.mods.claysoldiers.util.BugfixHelper;
import de.sanandrew.mods.claysoldiers.util.CSM_Main;
import de.sanandrew.mods.claysoldiers.util.ModItems;
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
import org.apache.logging.log4j.Level;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

public class TileEntityClayNexus
    extends TileEntity implements IInventory
{
    public static final int SOLDIER_SLOT = 0;
    public static final int THROWABLE_SLOT = 1;
    public static final int MOUNT_SLOT = 2;

    public boolean isActive = false;
    private boolean prevRsPowerState = false;

    public int spawnSoldierInterval = 40;
    public int spawnThrowableInterval = 30;
    public int soldierSpawnCount = 10;
    public int mountSpawnCount = 10;
    public int maxSoldierCount = 10;

    public long ticksActive = 0L;

    public float spinAngle = 0F;
    public float prevSpinAngle = 0F;

    protected String customName;

    private ItemStack[] upgradeItems_ = new ItemStack[36];
    private ItemStack soldierSlot_;
    private ItemStack throwableSlot_;
    private ItemStack mountSlot_;
    private int spawningSoldierCounter_ = 0;
    private int prevSpawningSoldierCounter_ = 0;
    private float health_ = 20.0F;
    private float maxHealth_ = 20.0F;

    private ClaymanTeam tempClayTeam_ = ClaymanTeam.NULL_TEAM;
    private Class<? extends ISoldierProjectile<? extends EntityThrowable>> tempThrowableCls_ = null;
    private AxisAlignedBB searchArea_;
    private AxisAlignedBB damageArea_;

    public TileEntityClayNexus() { }

    @Override
    public void updateEntity() {
        if( this.searchArea_ == null || this.damageArea_ == null ) {
            this.searchArea_ = AxisAlignedBB.getBoundingBox(this.xCoord - 63.0D, this.yCoord - 63.0D, this.zCoord - 63.0D,
                                                            this.xCoord + 64.0D, this.yCoord + 64.0D, this.zCoord + 64.0D
            );
            this.damageArea_ = AxisAlignedBB.getBoundingBox(this.xCoord + 0.1D, this.yCoord + 0.1D, this.zCoord + 0.1D,
                                                            this.xCoord + 0.9D, this.yCoord + 0.9D, this.zCoord + 0.9D
            );
        }

        super.updateEntity();

        boolean isRsPowered = this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)
                              || this.worldObj.getIndirectPowerLevelTo(this.xCoord - 1, this.yCoord, this.zCoord,     1) > 0
                              || this.worldObj.getIndirectPowerLevelTo(this.xCoord,     this.yCoord, this.zCoord + 1, 1) > 0
                              || this.worldObj.getIndirectPowerLevelTo(this.xCoord,     this.yCoord, this.zCoord - 1, 1) > 0
                              || this.worldObj.getIndirectPowerLevelTo(this.xCoord + 1, this.yCoord, this.zCoord,     1) > 0;

        if( !this.worldObj.isRemote && !this.prevRsPowerState && isRsPowered ) {
            this.isActive = !this.isActive;
            this.markDirty();
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
        this.prevRsPowerState = isRsPowered;

        if( this.isActive ) {
            if( this.health_ <= 0.0F ) {
                this.isActive = false;
            } else {
                this.ticksActive++;
            }

            if( !this.worldObj.isRemote && this.health_ > 0.0F ) {
                if( this.ticksActive % 20 == 0 ) {
                    int dmgEnemies = this.countDamagingEnemies();
                    if( dmgEnemies > 0 ) {
                        float healthDamage = 0.5F;

                        if( dmgEnemies > 1 ) {
                            healthDamage = 0.125F * dmgEnemies;
                        }

                        this.health_ -= healthDamage;

                        if( this.health_ < 0.0F ) {
                            this.health_ = 0.0F;
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

                if( ticksActive % this.spawnThrowableInterval == 0 && this.throwableSlot_ != null ) {
                    List<EntityClayMan> clayMen = getEnemies(true);
                    if( clayMen.size() > 0 ) {
                        EntityClayMan target = clayMen.get(SAPUtils.RNG.nextInt(clayMen.size()));

                        double deltaX = target.posX - this.xCoord + 0.5F;
                        double deltaZ = target.posZ - this.zCoord + 0.5F;

                        try {
                            ISoldierProjectile<? extends EntityThrowable> projectile = this.tempThrowableCls_.getConstructor(World.class, double.class, double.class,
                                                                                                                             double.class)
                                                                                                             .newInstance(this.worldObj, this.xCoord + 0.5F,
                                                                                                                          this.yCoord + 0.875F, this.zCoord + 0.5F);
                            projectile.initProjectile(target, true, this.tempClayTeam_.getTeamName());
                            EntityThrowable throwable = projectile.getProjectileEntity();

                            double d2 = (target.posY + target.getEyeHeight()) - 0.10000000298023224D - throwable.posY;
                            float f1 = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ) * 0.2F;
                            this.worldObj.spawnEntityInWorld(throwable);
                            throwable.setThrowableHeading(deltaX, d2 + f1, deltaZ, 0.6F, 12F);
                        } catch( InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e ) {
                            FMLLog.log(CSM_Main.MOD_LOG, Level.ERROR, "%1$s cannot be instantiated! %1$s is not thrown to target!", this.tempThrowableCls_.getName());
                            e.printStackTrace();
                        }
                    }
                }

                if( this.soldierSlot_ != null ) {
                    if( this.ticksActive % this.spawnSoldierInterval == 0 && this.spawningSoldierCounter_ <= 0 ) {
                        this.spawningSoldierCounter_ = Math.min(this.soldierSpawnCount, this.maxSoldierCount - this.countTeammates());
                        this.prevSpawningSoldierCounter_ = this.spawningSoldierCounter_;
                    }

                    if( ticksActive % 5 == 0 ) {
                        if( this.spawningSoldierCounter_ > 0 ) {
                            this.spawningSoldierCounter_ = Math.min(this.prevSpawningSoldierCounter_, this.maxSoldierCount - this.countTeammates());
                            this.prevSpawningSoldierCounter_ = this.spawningSoldierCounter_ - 1;
                            if( this.spawningSoldierCounter_ > 0 ) {
                                ItemClayManDoll.spawnClayMan(this.worldObj, this.tempClayTeam_.getTeamName(), this.xCoord + 0.5F, this.yCoord + 0.2D,
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
            if( this.isActive && this.getHealth() > 0.0F ) {
                this.spinAngle += 4;
                ClaymanTeam team = ItemClayManDoll.getTeam(this.soldierSlot_);
                RGBAValues rgba = SAPUtils.getRgbaFromColorInt(team.getTeamColor());
                CSM_Main.proxy.spawnParticles(PacketParticleFX.FX_NEXUS, Sextet.with((double)this.xCoord, (double)this.yCoord, (double)this.zCoord,
                                                                                     rgba.getRed() / 255.0F, rgba.getGreen() / 255.0F, rgba.getBlue() / 255.0F));
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
                return soldierSlot_;
            case THROWABLE_SLOT:
                return throwableSlot_;
            case MOUNT_SLOT:
                return mountSlot_;
            default:
                return this.upgradeItems_[slot - 3];
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
        return this.hasCustomInventoryName() ? this.customName : CSM_Main.MOD_ID + ":container.nexus";
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
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this
                && p_70300_1_.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() { }

    @Override
    public void closeInventory() { }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        switch( slot ) {
            case SOLDIER_SLOT:
                return stack == null || stack.getItem() == ModItems.dollSoldier;
            case THROWABLE_SLOT:
                return stack == null || SoldierUpgrades.getUpgradeFromItem(stack) instanceof IThrowableUpgrade;
            case MOUNT_SLOT:
                return stack == null || stack.getItem() instanceof IMountDoll;
            default:
                return stack == null || SoldierUpgrades.getUpgradeFromItem(stack) != null;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        this.upgradeItems_ = new ItemStack[36];

        NBTTagList nbttaglist = nbt.getTagList("items", NbtTypes.NBT_COMPOUND);
        for( int i = 0; i < nbttaglist.tagCount(); i++ ) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int slot = nbttagcompound1.getByte("slot") & 255;

            if( slot >= 0 && slot < this.getSizeInventory() ) {
                this.setStackInSlot(slot, ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }

        this.isActive = nbt.getBoolean("active");
        this.health_ = nbt.getFloat("health");
        this.maxHealth_ = nbt.getFloat("maxHealth");
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
        nbt.setFloat("health", this.health_);
        nbt.setFloat("maxHealth", this.maxHealth_);
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
    }

    public float getHealth() {
        return this.health_;
    }

    public float getMaxHealth() {
        return this.maxHealth_;
    }

    public void heal(float amount) {
        this.health_ += amount;
        if( this.health_ > this.maxHealth_ ) {
            this.health_ = this.maxHealth_;
        }
    }

    public void repair() {
        this.health_ = this.maxHealth_;
    }

    private int countTeammates() {
        @SuppressWarnings("unchecked")
        List<EntityClayMan> soldiers = this.worldObj.getEntitiesWithinAABB(EntityClayMan.class, this.searchArea_);
        int cnt = 0;
        for( EntityClayMan dodger : soldiers ) {
            if( dodger.getClayTeam().equals(this.tempClayTeam_.getTeamName()) ) {
                cnt++;
            }
        }
        return cnt;
    }

    private List<EntityClayMan> getEnemies(boolean mustBeSeen) {
        @SuppressWarnings("unchecked")
        List<EntityClayMan> soldiers = this.worldObj.getEntitiesWithinAABB(EntityClayMan.class, this.searchArea_);
        Iterator<EntityClayMan> iterator = soldiers.iterator();
        while( iterator.hasNext() ) {
            EntityClayMan roomie = iterator.next();
            if( roomie.getClayTeam().equals(this.tempClayTeam_.getTeamName()) || (mustBeSeen && !this.canEntityBeSeen(roomie)) ) {
                iterator.remove();
            }
        }
        return soldiers;
    }

    private int countDamagingEnemies() {
        @SuppressWarnings("unchecked")
        List<EntityClayMan> soldiers = this.worldObj.getEntitiesWithinAABB(EntityClayMan.class, this.damageArea_);
        int cnt = 0;
        for( EntityClayMan dodger : soldiers ) {
            if( !dodger.getClayTeam().equals(this.tempClayTeam_.getTeamName()) ) {
                cnt++;
            }
        }
        return cnt;
    }

    private void setStackInSlot(int slot, ItemStack stack) {
        switch( slot ) {
            case SOLDIER_SLOT:
                this.soldierSlot_ = stack;
                this.tempClayTeam_ = ItemClayManDoll.getTeam(stack);
                break;
            case THROWABLE_SLOT:
                ASoldierUpgrade upg = SoldierUpgrades.getUpgradeFromItem(stack);
                this.tempThrowableCls_ = stack != null && upg instanceof IThrowableUpgrade ? ((IThrowableUpgrade) upg).getThrowableClass() : null;
                this.throwableSlot_ = stack;
                break;
            case MOUNT_SLOT:
                this.mountSlot_ = stack;
                break;
            default:
                this.upgradeItems_[slot - 3] = stack;
        }
    }

    public boolean canEntityBeSeen(Entity target) {
        return this.worldObj.func_147447_a(Vec3.createVectorHelper(this.xCoord + 0.5D, this.yCoord + 0.9D, this.zCoord + 0.5D),
                                           Vec3.createVectorHelper(target.posX, target.posY + (double) target.getEyeHeight(), target.posZ),
                                           false, true, false) == null;
    }
}
