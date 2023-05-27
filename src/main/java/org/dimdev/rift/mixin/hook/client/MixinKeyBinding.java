package org.dimdev.rift.mixin.hook.client;

import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(KeyBinding.class)
public class MixinKeyBinding {
    @Final
    @Shadow
    private static Map<String, Integer> CATEGORY_ORDER;
    @Inject(method = "<init>*", at = @At("RETURN"))
    private void insertCategory(String description,
                                int keycode, String category, CallbackInfo ci) {
        CATEGORY_ORDER.computeIfAbsent(category, k -> CATEGORY_ORDER.size());
    }
}
