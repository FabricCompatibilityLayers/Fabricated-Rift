package org.dimdev.rift.mixin.hook;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import org.dimdev.rift.listener.CommandAdder;
import org.dimdev.riftloader.RiftLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Commands.class)
public class MixinCommands {
    @Shadow
    @Final
    private CommandDispatcher<CommandSource> dispatcher;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/brigadier/CommandDispatcher;findAmbiguities(Lcom/mojang/brigadier/AmbiguityConsumer;)V", remap = false))
    public void findAmbiguities(boolean isDedicatedServer, CallbackInfo ci) {
        for (CommandAdder commandAdder : RiftLoader.instance.getListeners(CommandAdder.class)) {
            commandAdder.registerCommands(this.dispatcher);
        }
    }
}
