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
import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import de.sanandrew.mods.claysoldiers.registry.TeamRegistry;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public final class ModelRegistry
{
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) throws Exception {
        setStandardModel(ItemRegistry.doll_brick_soldier);
        setStandardModel(ItemRegistry.shear_blade);
//        setStandardModel(ItemRegistry.turret_info);
//        setStandardModel(ItemRegistry.assembly_upg_filter);
//        setStandardModel(ItemRegistry.assembly_upg_auto);
//        setStandardModel(ItemRegistry.assembly_upg_speed);
//        setStandardModel(BlockRegistry.electrolyte_generator);
//        setStandardModel(BlockRegistry.turret_assembly);
//
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTurretAssembly.class, new RenderTurretAssembly());
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityElectrolyteGenerator.class, new RenderElectrolyteGenerator());
    }

    public static void registerModelsInit() {
        setCustomMeshModel(ItemRegistry.doll_soldier, new MeshDefUUID.Soldier());
        setCustomMeshModel(ItemRegistry.disruptor, new MeshDefDisruptor());
//        setCustomMeshModel(ItemRegistry.turret_ammo, new MeshDefUUID.Ammo());
//        setCustomMeshModel(ItemRegistry.turret_upgrade, new MeshDefUUID.Upgrade());
//        setCustomMeshModel(ItemRegistry.repair_kit, new MeshDefUUID.Repkit());
    }

    private static void setStandardModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    private static void setStandardModel(Block item) {
        Item itm = Item.getItemFromBlock(item);
        if( itm != null ) {
            setStandardModel(itm);
        }
    }

    private static void setCustomMeshModel(Item item, MeshDef mesher) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, mesher.getMeshDef());
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
            return type != null ? this.modelRes.get(getId(type)) : new ModelResourceLocation(stack.getItem().getRegistryName(), "inventory");
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
}
