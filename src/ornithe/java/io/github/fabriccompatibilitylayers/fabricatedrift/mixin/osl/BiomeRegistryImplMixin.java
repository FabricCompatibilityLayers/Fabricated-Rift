package io.github.fabriccompatibilitylayers.fabricatedrift.mixin.osl;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.biome.Biome;
import net.ornithemc.conditionalmixin.annotations.Conditional;
import net.ornithemc.conditionalmixin.annotations.Mod;
import net.ornithemc.osl.biomes.api.BiomeRegistry;
import net.ornithemc.osl.biomes.impl.BiomeRegistryImpl;
import net.ornithemc.osl.core.api.util.NamespacedIdentifier;
import org.dimdev.rift.listener.WorldChanger;
import org.dimdev.riftloader.RiftLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Conditional(modLoaded = @Mod("osl-biomes"))
@Mixin(BiomeRegistryImpl.class)
public class BiomeRegistryImplMixin {
    @ModifyReturnValue(method = "register*", at = @At("RETURN"))
    private static Biome rift$modifyBiome(Biome original) {
        NamespacedIdentifier identifier = BiomeRegistry.getIdentifier(original);
        int id = BiomeRegistry.getId(original);

        for (WorldChanger worldChanger : RiftLoader.instance.getListeners(WorldChanger.class)) {
            worldChanger.modifyBiome(id, identifier.toString(), original);
        }

        return original;
    }
}
