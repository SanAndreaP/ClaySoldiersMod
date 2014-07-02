package de.sanandrew.mods.claysoldiers.client.util;

import cpw.mods.fml.client.registry.RenderingRegistry;
import de.sanandrew.mods.claysoldiers.client.render.entity.RenderClayMan;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.util.CommonProxy;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;

/**
 * Created by SanAndreasP on 02.07.2014.
 */
public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityClayMan.class, new RenderClayMan());
    }
}
