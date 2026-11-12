package org.dimdev.rift.mixin.hook;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityFurnace;
import org.dimdev.rift.listener.BurnTimeProvider;
import org.dimdev.riftloader.RiftLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

@Mixin(TileEntityFurnace.class)
public class MixinFurnace {
    @ModifyReturnValue(method = "getBurnTimes", at = @At(value = "RETURN"))
    private static Map<Item, Integer> getBurnTimes(Map<Item, Integer> original) {
        for (BurnTimeProvider burnTimeProvider : RiftLoader.instance.getListeners(BurnTimeProvider.class)) {
            burnTimeProvider.registerBurnTimes(original);
        }

        return original;
    }
}
