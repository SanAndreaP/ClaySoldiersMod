/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.model.item;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumClayHorseType;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumGeckoType;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumTurtleType;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumWoolBunnyType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

abstract class MeshDefOrdinal<T extends Enum<T>>
        implements MeshDef
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
        return this.modelRes.values().toArray(new ModelResourceLocation[0]);
    }

    static final class Horse
            extends MeshDefOrdinal<EnumClayHorseType>
    {
        Horse() {
            Arrays.stream(EnumClayHorseType.VALUES).filter(type -> type.visible).forEach(type -> {
                ResourceLocation resLoc = new ResourceLocation(CsmConstants.ID, "mounts/horse" + (type.cstItemSuffix == null ? "" : '_' + type.cstItemSuffix));
                ModelResourceLocation modelRes = new ModelResourceLocation(resLoc, "inventory");
                this.modelRes.put(type.ordinal(), modelRes);
            });
        }

        @Override
        public EnumClayHorseType getType(ItemStack stack) { return ItemRegistry.DOLL_HORSE.getType(stack); }
    }

    static final class Pegasus
            extends MeshDefOrdinal<EnumClayHorseType>
    {
        Pegasus() {
            Arrays.stream(EnumClayHorseType.VALUES).filter(type -> type.visible).forEach(type -> {
                ResourceLocation resLoc = new ResourceLocation(CsmConstants.ID, "mounts/pegasus" + (type.cstItemSuffix == null ? "" : '_' + type.cstItemSuffix));
                ModelResourceLocation modelRes = new ModelResourceLocation(resLoc, "inventory");
                this.modelRes.put(type.ordinal(), modelRes);
            });
        }

        @Override
        public EnumClayHorseType getType(ItemStack stack) { return ItemRegistry.DOLL_PEGASUS.getType(stack); }
    }

    static final class Turtle
            extends MeshDefOrdinal<EnumTurtleType>
    {
        Turtle() {
            Arrays.stream(EnumTurtleType.VALUES).filter(type -> type.visible).forEach(type -> {
                ResourceLocation resLoc = new ResourceLocation(CsmConstants.ID, "mounts/turtle" + (type.cstItemSuffix == null ? "" : '_' + type.cstItemSuffix));
                ModelResourceLocation modelRes = new ModelResourceLocation(resLoc, "inventory");
                this.modelRes.put(type.ordinal(), modelRes);
            });
        }

        @Override
        public EnumTurtleType getType(ItemStack stack) { return ItemRegistry.DOLL_TURTLE.getType(stack); }
    }

    static final class Bunny
            extends MeshDefOrdinal<EnumWoolBunnyType>
    {
        Bunny() {
            Arrays.stream(EnumWoolBunnyType.VALUES).filter(type -> type.visible).forEach(type -> {
                ResourceLocation resLoc = new ResourceLocation(CsmConstants.ID, "mounts/bunny");
                ModelResourceLocation modelRes = new ModelResourceLocation(resLoc, "inventory");
                this.modelRes.put(type.ordinal(), modelRes);
            });
        }

        @Override
        public EnumWoolBunnyType getType(ItemStack stack) { return ItemRegistry.DOLL_BUNNY.getType(stack); }
    }

    static final class Gecko
            extends MeshDefOrdinal<EnumGeckoType>
    {
        Gecko() {
            Arrays.stream(EnumGeckoType.VALUES).filter(type -> type.visible).forEach(type -> {
                ResourceLocation resLoc = new ResourceLocation(CsmConstants.ID, "mounts/gecko");
                ModelResourceLocation modelRes = new ModelResourceLocation(resLoc, "inventory");
                this.modelRes.put(type.ordinal(), modelRes);
            });
        }

        @Override
        public EnumGeckoType getType(ItemStack stack) { return ItemRegistry.DOLL_GECKO.getType(stack); }
    }
}
