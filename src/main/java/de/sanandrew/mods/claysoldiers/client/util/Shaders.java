/* ******************************************************************************************************************
 * Authors:   SanAndreasP
 * Copyright: SanAndreasP
 * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
 *                http://creativecommons.org/licenses/by-nc-sa/4.0/
 *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.client.util;

import de.sanandrew.mods.claysoldiers.util.Resources;
import de.sanandrew.mods.sanlib.lib.client.ShaderHelper;

public final class Shaders
{
    public static int stencil = 0;

    public static void initShaders() {
        if( !ShaderHelper.areShadersEnabled() ) {
            return;
        }

        stencil = ShaderHelper.createProgram(null, Resources.SHADER_STENCIL.resource);
    }
}
