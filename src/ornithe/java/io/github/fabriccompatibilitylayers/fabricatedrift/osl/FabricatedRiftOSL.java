package io.github.fabriccompatibilitylayers.fabricatedrift.osl;

import net.ornithemc.osl.blocks.api.BlockEvents;
import net.ornithemc.osl.entrypoints.api.ModInitializer;
import net.ornithemc.osl.items.api.ItemEvents;
import net.ornithemc.osl.lifecycle.api.LifecycleEvents;
import net.ornithemc.osl.lifecycle.api.server.MinecraftServerEvents;
import org.dimdev.rift.listener.*;
import org.dimdev.riftloader.RiftLoader;

public class FabricatedRiftOSL implements ModInitializer {
    @Override
    public void init() {
        LifecycleEvents.BOOTSTRAP_START.register(() -> {
            for (MinecraftStartListener listener : RiftLoader.instance.getListeners(MinecraftStartListener.class)) {
                listener.onMinecraftStart();
            }
        });

        LifecycleEvents.BOOTSTRAP_END.register(() -> {
            for (BootstrapListener listener : RiftLoader.instance.getListeners(BootstrapListener.class)) {
                listener.afterVanillaBootstrap();
            }
        });

        BlockEvents.REGISTER_BLOCKS.register(() -> {
            for (BlockAdder blockAdder : RiftLoader.instance.getListeners(BlockAdder.class)) {
                blockAdder.registerBlocks();
            }
        });

        ItemEvents.REGISTER_ITEMS.register(() -> {
            for (ItemAdder itemAdder : RiftLoader.instance.getListeners(ItemAdder.class)) {
                itemAdder.registerItems();
            }
        });

        MinecraftServerEvents.TICK_END.register(instance -> {
            instance.profiler.startSection("mods");
            for (ServerTickable tickable : RiftLoader.instance.getListeners(ServerTickable.class)) {
                instance.profiler.startSection(() -> tickable.getClass().getCanonicalName().replace('.', '/'));
                tickable.serverTick(instance);
                instance.profiler.endSection();
            }
            instance.profiler.endSection();
        });
    }
}
