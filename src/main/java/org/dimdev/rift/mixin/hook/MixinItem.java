package org.dimdev.rift.mixin.hook;

import net.minecraft.item.Item;
import net.ornithemc.conditionalmixin.annotations.Conditional;
import net.ornithemc.conditionalmixin.annotations.Mod;
import org.dimdev.rift.listener.ItemAdder;
import org.dimdev.riftloader.RiftLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Conditional(modAbsent = @Mod("osl-items"))
@Mixin(Item.class)
public abstract class MixinItem {
    @Inject(method = "registerItems", at = @At("RETURN"))
    private static void onRegisterItems(CallbackInfo ci) {
        for (ItemAdder itemAdder : RiftLoader.instance.getListeners(ItemAdder.class)) {
            itemAdder.registerItems();
        }
    }
}
