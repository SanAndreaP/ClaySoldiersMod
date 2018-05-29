/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.model.item;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.EnumMap;
import java.util.Map;

class MeshDefDisruptor
        implements MeshDef
{
    public final Map<ItemDisruptor.DisruptorType, ModelResourceLocation> modelRes = new EnumMap<>(ItemDisruptor.DisruptorType.class);

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
        return this.modelRes.values().toArray(new ModelResourceLocation[0]);
    }
}
