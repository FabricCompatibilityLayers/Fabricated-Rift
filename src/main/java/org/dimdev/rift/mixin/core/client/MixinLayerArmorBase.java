package org.dimdev.rift.mixin.core.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LayerArmorBase.class)
public class MixinLayerArmorBase {
    @ModifyExpressionValue(method = "getArmorResource(Lnet/minecraft/item/ItemArmor;ZLjava/lang/String;)Lnet/minecraft/util/ResourceLocation;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/IArmorMaterial;getName()Ljava/lang/String;"))
    private String rift$sanitizePath(String original) {
        ResourceLocation location = new ResourceLocation(original);
        return location.getPath();
    }

    @Inject(method = "getArmorResource(Lnet/minecraft/item/ItemArmor;ZLjava/lang/String;)Lnet/minecraft/util/ResourceLocation;",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Map;computeIfAbsent(Ljava/lang/Object;Ljava/util/function/Function;)Ljava/lang/Object;",
                    remap = false
            ))
    private void getArmorTexture(ItemArmor armor, boolean bl, String string, CallbackInfoReturnable<ResourceLocation> cir,
                                 @Local(ordinal = 1) LocalRef<String> oldRef) {
        String old = oldRef.get();

        ResourceLocation location = new ResourceLocation(armor.getArmorMaterial().getName());

        oldRef.set(location.getNamespace() + ":" + old);
    }
}
