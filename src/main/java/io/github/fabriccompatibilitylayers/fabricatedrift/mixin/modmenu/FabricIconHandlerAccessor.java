package io.github.fabriccompatibilitylayers.fabricatedrift.mixin.modmenu;

import com.terraformersmc.modmenu.util.mod.fabric.FabricIconHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.nio.file.Path;
import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(value = FabricIconHandler.class, remap = false)
public interface FabricIconHandlerAccessor {
    @Accessor(value = "modIconCache", remap = false)
    Map<Path, DynamicTexture> getCache();
}
