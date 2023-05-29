package org.dimdev.rift.mixin.core.client;

import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(LayerArmorBase.class)
public class MixinLayerArmorBase<T extends ModelBase> {
    private static final Pattern TEXTURE_MASK = Pattern.compile("textures\\/models\\/armor\\/(.+)(_layer_\\d\\w*\\.png)");

    @ModifyArg(
            method = "getArmorResource(Lnet/minecraft/item/ItemArmor;ZLjava/lang/String;)Lnet/minecraft/util/ResourceLocation;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;",
                    remap = false
            ),
            index = 0
    )
    private Object getArmorTexture(Object old) {
        Matcher match = TEXTURE_MASK.matcher((String) old);
        if (!match.matches() || match.groupCount() != 2) {
            //Strange texture we can't resolve
            throw new IllegalArgumentException("Unexpected armor texture: " + old);
        }

        String name = match.group(1);
        String extra = match.group(2);
        ResourceLocation location = new ResourceLocation(name);

        return location.getNamespace() + ":textures/models/armor/" + location.getPath() + extra;
    }
}
