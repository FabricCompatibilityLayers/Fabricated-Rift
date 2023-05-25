package org.dimdev.rift.mixin.core;

import net.minecraft.resources.FolderPack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.VanillaPack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

@Mixin(VanillaPack.class)
public class MixinVanillaPack {
    /**
     * @reason Give priority to resources in the Minecraft jar to avoid them
     * from being overwritten by mods.
     * @author Runemoro, InsomniaKitten, Cat Core
     */
    @Inject(method = "method_14416", at = @At("HEAD"), cancellable = true)
    private void findInputStream(ResourcePackType resourceType, ResourceLocation identifier, CallbackInfoReturnable<InputStream> callback) {
        if (VanillaPack.field_14196 != null) {
            // Fall through to Vanilla logic, they have a special case here.
            return;
        }

        String path = resourceType.getDirectoryName() + "/" + identifier.getNamespace() + "/" + identifier.getPath();
        URL found = null;

        try {
            Enumeration<URL> candidates = VanillaPack.class.getClassLoader().getResources(path);

            // Get the last element
            while (candidates.hasMoreElements()) {
                found = candidates.nextElement();
            }

            if (found == null || !FolderPack.method_14402(new File(found.getFile()), "/" + path)) {
                // Mimics vanilla behavior

                callback.setReturnValue(null);
                return;
            }
        } catch (IOException var6) {
            // Default path
        }

        try {
            if (found != null) {
                callback.setReturnValue(found.openStream());
            }
        } catch (Exception e) {
            // Default path
        }
    }
}
