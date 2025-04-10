package io.github.fabriccompatibilitylayers.fabricatedrift;

import io.github.fabriccompatibilitylayers.modremappingapi.api.v2.ModCandidate;
import io.github.fabriccompatibilitylayers.modremappingapi.api.v2.ModDiscovererConfig;

import java.nio.file.Path;

public class RiftCandidate implements ModCandidate {
    private final String id, destinationName;
    private Path path, destinationPath;
    private final ModDiscovererConfig config;

    public RiftCandidate(String id, Path path, ModDiscovererConfig config) {
        this.id = id;
        this.path = path;
        this.destinationName = path.getFileName().toString();
        this.config = config;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Path getPath() {
        return this.path;
    }

    @Override
    public String getType() {
        return "rift";
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getAccessWidenerPath() {
        return null;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public ModCandidate getParent() {
        return null;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getVersion() {
        return null;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public String getParentSubPath() {
        return null;
    }

    @Override
    public String getDestinationName() {
        return this.destinationName;
    }

    @Override
    public ModDiscovererConfig getDiscovererConfig() {
        return this.config;
    }

    @Override
    public void setAccessWidener(byte[] bytes) {

    }

    @Override
    public byte @org.jetbrains.annotations.Nullable [] getAccessWidener() {
        return new byte[0];
    }

    @Override
    public void setDestination(Path path) {
        this.destinationPath = path;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public Path getDestination() {
        return this.destinationPath;
    }

    @Override
    public void setPath(Path path) {
        this.path = path;
    }
}
