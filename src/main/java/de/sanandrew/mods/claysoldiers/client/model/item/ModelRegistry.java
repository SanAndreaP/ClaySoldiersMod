/*
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.claysoldiers.client.model.item;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.item.EnumShieldTypes;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = CsmConstants.ID, value = Side.CLIENT)
public final class ModelRegistry
{
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        setStandardModel(ItemRegistry.DOLL_BRICK_SOLDIER);
        setCustomMeshModel(ItemRegistry.DOLL_SOLDIER, new MeshDefUUID.Soldier());
        setCustomMeshModel(ItemRegistry.DOLL_HORSE, new MeshDefOrdinal.Horse());
        setCustomMeshModel(ItemRegistry.DOLL_PEGASUS, new MeshDefOrdinal.Pegasus());
        setCustomMeshModel(ItemRegistry.DOLL_TURTLE, new MeshDefOrdinal.Turtle());
        setCustomMeshModel(ItemRegistry.DOLL_BUNNY, new MeshDefOrdinal.Bunny());
        setCustomMeshModel(ItemRegistry.DOLL_GECKO, new MeshDefOrdinal.Gecko());
        setCustomMeshModel(ItemRegistry.DISRUPTOR, new MeshDefDisruptor());

        for( EnumShieldTypes type : EnumShieldTypes.VALUES ) {
            setStandardModel(ItemRegistry.SOLDIER_SHIELD, type.damageVal, type.modelName);
        }
        setStandardModel(ItemRegistry.SHEAR_BLADE, 0, "shear_blade");
        setStandardModel(ItemRegistry.SHEAR_BLADE, 1, "shear_blade_prism");
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

    private static void setCustomMeshModel(Item item, MeshDef mesher) {
        ModelLoader.setCustomMeshDefinition(item, mesher);
        ModelBakery.registerItemVariants(item, mesher.getResLocations());
    }
}
