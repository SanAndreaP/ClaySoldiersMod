/*
 * ****************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 * *****************************************************************************************************************
 */
package de.sanandrew.mods.claysoldiers.compat.jei;

import com.google.common.collect.ImmutableList;
import de.sanandrew.mods.claysoldiers.item.ItemDisruptor;
import de.sanandrew.mods.claysoldiers.registry.ItemRegistry;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@JEIPlugin
@SideOnly(Side.CLIENT)
public class JeiPlugin extends BlankModPlugin {
    public JeiPlugin() {}

    @Override
    public void register(IModRegistry registry) {
//        registry.addRecipeCategories(new AssemblyRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeHandlers(new DisruptorRecipeHandler());
        registry.addRecipes(ImmutableList.of(new DisruptorRecipeHandler.DisruptorRecipe(ItemDisruptor.DisruptorType.CLAY),
                                             new DisruptorRecipeHandler.DisruptorRecipe(ItemDisruptor.DisruptorType.HARDENED),
                                             new DisruptorRecipeHandler.DisruptorRecipe(ItemDisruptor.DisruptorType.OBSIDIAN)));
//
//        registry.addRecipeHandlers(new AssemblyRecipeHandler());
//
//        registry.addRecipes(TurretAssemblyRegistry.INSTANCE.getRecipeList());
//
//        registry.addRecipeCategoryCraftingItem(new ItemStack(BlockRegistry.turret_assembly), AssemblyRecipeCategory.UID);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.registerSubtypeInterpreter(ItemRegistry.disruptor, itemStack -> ItemDisruptor.getType(itemStack).name());
//        subtypeRegistry.registerSubtypeInterpreter(ItemRegistry.turret_placer, itemStack -> {
//            TurretInfo stype = ItemTurret.getTurretInfo(itemStack);
//            return stype != null ? stype.getUUID().toString() : null;
//        });
//
//        subtypeRegistry.registerSubtypeInterpreter(ItemRegistry.turret_upgrade, itemStack -> UpgradeRegistry.INSTANCE.getUpgradeUUID(itemStack).toString());
//
//        subtypeRegistry.registerSubtypeInterpreter(ItemRegistry.turret_ammo, itemStack -> AmmoRegistry.INSTANCE.getType(itemStack).getId().toString());
//
//        subtypeRegistry.registerSubtypeInterpreter(ItemRegistry.repair_kit, itemStack -> RepairKitRegistry.INSTANCE.getType(itemStack).getUUID().toString());
    }
}
