package io.github.fabriccompatibilitylayers.fabricatedrift.osl;

import net.ornithemc.osl.entrypoints.api.client.ClientModInitializer;
import net.ornithemc.osl.keybinds.api.KeybindEvents;
import net.ornithemc.osl.keybinds.api.KeybindRegistry;
import net.ornithemc.osl.lifecycle.api.client.MinecraftClientEvents;
import org.dimdev.rift.listener.client.ClientTickable;
import org.dimdev.rift.listener.client.KeyBindingAdder;
import org.dimdev.riftloader.RiftLoader;

public class ClientFabricatedRiftOSL implements ClientModInitializer {
    @Override
    public void initClient() {
        MinecraftClientEvents.TICK_END.register(instance -> {
            instance.profiler.startSection("mods");
            for (ClientTickable tickable : RiftLoader.instance.getListeners(ClientTickable.class)) {
                instance.profiler.startSection(() -> tickable.getClass().getCanonicalName().replace('.', '/'));
                tickable.clientTick(instance);
                instance.profiler.endSection();
            }
            instance.profiler.endSection();
        });

        KeybindEvents.REGISTER_KEYBINDS.register(() -> {
            for (KeyBindingAdder keyBindingAdder : RiftLoader.instance.getListeners(KeyBindingAdder.class)) {
                keyBindingAdder.getKeyBindings().forEach(KeybindRegistry::register);
            }
        });
    }
}
