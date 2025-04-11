package io.github.fabriccompatibilitylayers.fabricatedrift.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import java.util.HashMap;
import java.util.Map;

public class FabricatedRiftModMenuCompat implements ModMenuApi {
    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        Map<String, ConfigScreenFactory<?>> factories = new HashMap<>();

        for (Map.Entry<String, Runnable> entry : RiftModListConfigRegistry.modConfigRunnableMap.entrySet()) {
            factories.put("rift_" + entry.getKey(), parent -> new FakeGuiScreen(parent, entry.getValue()));
        }

        return factories;
    }
}
