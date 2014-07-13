package de.sanandrew.mods.claysoldiers.client.gui;

import cpw.mods.fml.client.IModGuiFactory;
import de.sanandrew.mods.claysoldiers.client.gui.config.GuiConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.Set;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class ModGuiFactory
    implements IModGuiFactory
{
    @Override
    public void initialize(Minecraft minecraftInstance) { }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return GuiConfigScreen.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }
}
