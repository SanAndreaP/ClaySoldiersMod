/*
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.claysoldiers.client.util;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.ITeam;
import de.sanandrew.mods.claysoldiers.item.EnumShieldTypes;
import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import de.sanandrew.mods.claysoldiers.item.ItemHorseMount;
import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumClayHorseType;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public final class ModelRegistry
{
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) throws Exception {
        setStandardModel(ItemRegistry.DOLL_BRICK_SOLDIER);
        setStandardModel(ItemRegistry.SHEAR_BLADE);
        setCustomMeshModel(ItemRegistry.DOLL_SOLDIER, new MeshDefUUID.Soldier());
        setCustomMeshModel(ItemRegistry.DOLL_HORSE, new MeshDefOrdinal.Horse());
        setCustomMeshModel(ItemRegistry.DISRUPTOR, new MeshDefDisruptor());

        for( EnumShieldTypes type : EnumShieldTypes.VALUES ) {
            setStandardModel(ItemRegistry.SOLDIER_SHIELD, type.damageVal, type.modelName);
        }
    }

    private static void setStandardModel(Item item) {
        ResourceLocation regName = item.getRegistryName();
        if( regName != null ) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(regName, "inventory"));
        }
    }

    private static void setStandardModel(Item item, int meta, String model) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(CsmConstants.ID, model), "inventory"));
    }

    private static void setStandardModel(Block item) {
        Item itm = Item.getItemFromBlock(item);
        if( itm != Items.AIR ) {
            setStandardModel(itm);
        }
    }

    private static void setCustomMeshModel(Item item, MeshDef<?> mesher) {
        ModelLoader.setCustomMeshDefinition(item, mesher.getMeshDef());
        ModelBakery.registerItemVariants(item, mesher.getResLocations());
    }

    private interface MeshDef<T extends ItemMeshDefinition> {
        ResourceLocation[] getResLocations();
        T getMeshDef();
    }

    private static class MeshDefDisruptor
            implements ItemMeshDefinition, MeshDef
    {
        public final Map<ItemDisruptor.DisruptorType, ModelResourceLocation> modelRes = new HashMap<>();

        public MeshDefDisruptor() {
            for( ItemDisruptor.DisruptorType type : ItemDisruptor.DisruptorType.VALUES ) {
                if( type != ItemDisruptor.DisruptorType.UNKNOWN ) {
                    ModelResourceLocation modelRes = new ModelResourceLocation(new ResourceLocation(CsmConstants.ID, "disruptors/" + type.name), "inventory");
                    this.modelRes.put(type, modelRes);
                }
            }
        }

        @Override
        public ModelResourceLocation getModelLocation(ItemStack stack) {
            ItemDisruptor.DisruptorType type = ItemDisruptor.getType(stack);
            return this.modelRes.get(type);
        }

        public ResourceLocation[] getResLocations() {
            return this.modelRes.values().toArray(new ModelResourceLocation[this.modelRes.size()]);
        }

        @Override
        public ItemMeshDefinition getMeshDef() {
            return this;
        }
    }

    private static abstract class MeshDefUUID<T>
            implements ItemMeshDefinition, MeshDef
    {
        public final Map<UUID, ModelResourceLocation> modelRes = new HashMap<>();

        @Override
        public ModelResourceLocation getModelLocation(ItemStack stack) {
            T type = getType(stack);
            ResourceLocation regName = stack.getItem().getRegistryName();
            return type != null ? this.modelRes.get(getId(type)) : regName != null ? new ModelResourceLocation(regName, "inventory") : null;
        }

        public abstract T getType(ItemStack stack);
        public abstract UUID getId(T type);

        public ResourceLocation[] getResLocations() {
            return this.modelRes.values().toArray(new ModelResourceLocation[this.modelRes.size()]);
        }

        @Override
        public ItemMeshDefinition getMeshDef() {
            return this;
        }

        static final class Soldier
                extends MeshDefUUID<ITeam>
        {
            Soldier() {
                for( ITeam info : TeamRegistry.INSTANCE.getTeams() ) {
                    ModelResourceLocation modelRes = new ModelResourceLocation(info.getItemModel(), "inventory");
                    this.modelRes.put(info.getId(), modelRes);
                }
            }

            @Override
            public ITeam getType(ItemStack stack) { return TeamRegistry.INSTANCE.getTeam(stack); }

            @Override
            public UUID getId(ITeam type) { return type.getId(); }
        }
    }

    private static abstract class MeshDefOrdinal<T extends Enum<T>>
            implements ItemMeshDefinition, MeshDef
    {
        public final Map<Integer, ModelResourceLocation> modelRes = new HashMap<>();

        @Override
        public ModelResourceLocation getModelLocation(ItemStack stack) {
            T type = getType(stack);
            ResourceLocation regName = stack.getItem().getRegistryName();
            return type != null ? this.modelRes.get(type.ordinal()) : regName != null ? new ModelResourceLocation(regName, "inventory") : null;
        }

        public abstract T getType(ItemStack stack);

        public ResourceLocation[] getResLocations() {
            return this.modelRes.values().toArray(new ModelResourceLocation[this.modelRes.size()]);
        }

        @Override
        public ItemMeshDefinition getMeshDef() {
            return this;
        }

        static final class Horse
                extends MeshDefOrdinal<EnumClayHorseType>
        {
            Horse() {
                Arrays.stream(EnumClayHorseType.VALUES).filter(type -> type.visible).forEach(type -> {
                    ResourceLocation resLoc = new ResourceLocation(CsmConstants.ID, "mounts/" + MiscUtils.defIfNull(type.cstItemTexture, "horse"));
                    ModelResourceLocation modelRes = new ModelResourceLocation(resLoc, "inventory");
                    this.modelRes.put(type.ordinal(), modelRes);
                });
            }

            @Override
            public EnumClayHorseType getType(ItemStack stack) { return ItemHorseMount.getType(stack); }
        }
    }
}
