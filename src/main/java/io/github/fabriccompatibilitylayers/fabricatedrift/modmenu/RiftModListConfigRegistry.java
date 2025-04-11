package io.github.fabriccompatibilitylayers.fabricatedrift.modmenu;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

public class RiftModListConfigRegistry {
    protected static Map<String, Runnable> modConfigRunnableMap = Maps.newHashMap();

    public static void registerConfig(String modid, Runnable runnable) {
        modConfigRunnableMap.put(modid, runnable);
    }

    public static void unregisterConfig(String modid) {
        registerConfig(modid, null);
    }

    public static Optional<Runnable> getConfigRunnable(String modid) {
        if (modConfigRunnableMap.containsKey(modid))
            return Optional.ofNullable(modConfigRunnableMap.get(modid));
        return Optional.empty();
    }
}
