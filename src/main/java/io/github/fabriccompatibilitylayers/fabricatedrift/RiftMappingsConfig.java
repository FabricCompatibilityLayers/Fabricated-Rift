package io.github.fabriccompatibilitylayers.fabricatedrift;

import io.github.fabriccompatibilitylayers.modremappingapi.api.v2.MappingsConfig;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

public class RiftMappingsConfig implements MappingsConfig {
    @org.jetbrains.annotations.Nullable
    @Override
    public String getSourceNamespace() {
        return "official";
    }

    @Override
    public @org.jetbrains.annotations.Nullable Supplier<String> getExtraMappings() {
        return null;
    }

    @Override
    public Map<String, String> getRenamingMap() {
        return Collections.emptyMap();
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getDefaultPackage() {
        return "net/minecraft/";
    }
}
