package org.dimdev.rift.mixin.core.client;

import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ClientBrandRetriever.class)
public class MixinClientBrandRetriever {
    /**
     * @author Runemoro
     * @reason change branding
     */
    @Overwrite
    public static String getClientModName() {
        return "rift-on-fabric";
    }
}
