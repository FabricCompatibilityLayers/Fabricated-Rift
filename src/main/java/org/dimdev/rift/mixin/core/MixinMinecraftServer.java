package org.dimdev.rift.mixin.core;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {
    /**
     * @author Runemoro
     * @reason change branding
     */
    @Overwrite
    public String getServerModName() {
        return "rift-on-fabric";
    }
}
